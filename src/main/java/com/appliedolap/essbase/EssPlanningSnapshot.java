package com.appliedolap.essbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * An EPM Cloud artifact snapshot, read as a source for building an Essbase cube.
 *
 * <p>The snapshot is the {@code .zip} a Planning application exports to. This finds the dimensions in
 * it and builds them into a cube, which is as close as an Essbase server can get to the shape of the
 * Planning application that produced it.
 *
 * <pre>
 * EssPlanningSnapshot snapshot = EssPlanningSnapshot.open(Path.of("Artifact Snapshot.zip"));
 * snapshot.getPlanTypes();                       // [Vision, Plan1]
 * Report report = snapshot.importInto(cube, "Vision");
 * </pre>
 *
 * <p><strong>Best effort, and it says what it could not do.</strong> Planning outlines use member ids
 * where Essbase uses names, allow duplicate names where a plain Essbase outline does not, and carry
 * properties Essbase will refuse in combinations that only the server can judge. Every dimension is
 * attempted independently and the {@link Report} says what happened to each, so one awkward dimension
 * costs that dimension rather than the import.
 *
 * <p>Two files in the snapshot describe the outline and neither is sufficient alone. The dimension
 * {@code .csv} files carry the names, parents and properties - the hierarchy. The batch-outline-edit
 * {@code .xml} carries the attribute associations, which the CSVs do not, but addresses members by
 * Planning GUID; the CSVs' {@code UUID} column is what turns those back into names. Both are read.
 */
public class EssPlanningSnapshot implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(EssPlanningSnapshot.class);

    /** What an import did, dimension by dimension. */
    public static class Report {

        private final List<DimensionOutcome> dimensions = new ArrayList<>();

        private final List<String> notes = new ArrayList<>();

        public List<DimensionOutcome> getDimensions() {
            return Collections.unmodifiableList(dimensions);
        }

        /** Anything worth saying that is not about one dimension. */
        public List<String> getNotes() {
            return Collections.unmodifiableList(notes);
        }

        public boolean isSuccessful() {
            return dimensions.stream().allMatch(DimensionOutcome::isSuccessful);
        }

        public int getTotalMembers() {
            return dimensions.stream().mapToInt(DimensionOutcome::getMembersBuilt).sum();
        }

        void add(DimensionOutcome outcome) {
            dimensions.add(outcome);
        }

        void note(String note) {
            notes.add(note);
        }

        @Override
        public String toString() {
            long failed = dimensions.stream().filter(d -> !d.isSuccessful()).count();
            return dimensions.size() + " dimension(s), " + getTotalMembers() + " member(s) built"
                    + (failed == 0 ? "" : ", " + failed + " dimension(s) with problems");
        }

    }

    /** One dimension's outcome. */
    public static class DimensionOutcome {

        private final String name;

        private final boolean attribute;

        private int membersBuilt;

        private String failure;

        private final List<String> notes = new ArrayList<>();

        DimensionOutcome(String name, boolean attribute) {
            this.name = name;
            this.attribute = attribute;
        }

        public String getName() {
            return name;
        }

        public boolean isAttributeDimension() {
            return attribute;
        }

        public int getMembersBuilt() {
            return membersBuilt;
        }

        /** Null when the dimension built; otherwise why it did not. */
        public String getFailure() {
            return failure;
        }

        /** Things that were skipped or could not be set, which do not amount to a failure. */
        public List<String> getNotes() {
            return Collections.unmodifiableList(notes);
        }

        public boolean isSuccessful() {
            return failure == null;
        }

        void built(int members) {
            this.membersBuilt = members;
        }

        void failed(String failure) {
            this.failure = failure;
        }

        void note(String note) {
            notes.add(note);
        }

        @Override
        public String toString() {
            return name + (attribute ? " (attribute)" : "") + ": "
                    + (failure == null ? membersBuilt + " members" : "failed - " + failure);
        }

    }

    private final ZipFile zip;

    private final Map<String, ZipEntry> entries = new LinkedHashMap<>();

    private EssPlanningSnapshot(ZipFile zip) {
        this.zip = zip;
        zip.stream().filter(entry -> !entry.isDirectory())
                .forEach(entry -> entries.put(entry.getName(), entry));
    }

    /**
     * Opens a snapshot.
     *
     * @param snapshot the artifact snapshot {@code .zip}
     * @throws IOException if it cannot be read
     */
    public static EssPlanningSnapshot open(Path snapshot) throws IOException {
        return new EssPlanningSnapshot(new ZipFile(snapshot.toFile()));
    }

    @Override
    public void close() throws IOException {
        zip.close();
    }

    /**
     * The Planning applications in the snapshot.
     *
     * <p>A snapshot holds more than the application - Shared Services, FDMEE, the document repository -
     * and the application's own folder is the one named {@code HP-<name>}.
     */
    public List<String> getApplicationNames() {
        Set<String> applications = new LinkedHashSet<>();
        for (String name : entries.keySet()) {
            if (name.startsWith("HP-")) {
                int slash = name.indexOf('/');
                if (slash > 3) {
                    applications.add(name.substring(3, slash));
                }
            }
        }
        return new ArrayList<>(applications);
    }

    /**
     * The plan types - the cubes - the snapshot describes.
     *
     * <p>One of these is what {@link #importInto} needs, because a Planning application's dimensions
     * are shared across its cubes and each records its own consolidation, storage and formula per
     * member. Building for the wrong one silently produces a different outline.
     */
    public List<String> getPlanTypes() {
        Set<String> planTypes = new LinkedHashSet<>();
        for (String name : entries.keySet()) {
            String marker = "/resource/Cube/";
            int at = name.indexOf(marker);
            if (at < 0) {
                continue;
            }
            String rest = name.substring(at + marker.length());
            int slash = rest.indexOf('/');
            if (slash > 0) {
                planTypes.add(rest.substring(0, slash));
            }
        }
        return new ArrayList<>(planTypes);
    }

    /**
     * Every dimension file in the snapshot, by dimension name.
     *
     * <p>Three places hold them: the application's shared standard dimensions, its shared attribute
     * dimensions, and a cube's own standard dimensions. A cube's own wins where a name is in both.
     */
    public Map<String, String> getDimensionEntries(String planType) {
        Map<String, String> found = new LinkedHashMap<>();
        for (String name : entries.keySet()) {
            if (!name.endsWith(".csv")) {
                continue;
            }
            boolean shared = name.contains("/Common Dimensions/Standard Dimensions/")
                    || name.contains("/Common Dimensions/Attribute Dimensions/");
            boolean own = name.contains("/Cube/" + planType + "/Standard Dimensions/");
            if (!shared && !own) {
                continue;
            }
            String file = name.substring(name.lastIndexOf('/') + 1);
            String dimension = file.substring(0, file.length() - ".csv".length());
            if (own || !found.containsKey(dimension)) {
                found.put(dimension, name);
            }
        }
        return found;
    }

    /** Whether a dimension file sits in the attribute-dimension folder. */
    private static boolean isAttributeEntry(String entry) {
        return entry.contains("/Attribute Dimensions/");
    }

    /** Reads one dimension out of the snapshot. */
    public EssPlanningDimensionFile readDimension(String entry) throws IOException {
        try (InputStream in = zip.getInputStream(entries.get(entry))) {
            return EssPlanningDimensionFile.parse(new String(in.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    /**
     * Builds every dimension in the snapshot into a cube.
     *
     * <p>The cube must exist and should be empty; this adds to whatever is there rather than replacing
     * it. Each dimension is created, then its members, then the storage types that could only be set
     * once the hierarchy existed, then the attribute associations - each step independent, so a step
     * that Essbase refuses costs that step rather than the import.
     *
     * @param cube where to build
     * @param planType which cube of the Planning application to take the properties from
     * @return what happened, dimension by dimension
     */
    public Report importInto(EssCube cube, String planType) {
        Report report = new Report();
        Map<String, String> dimensionEntries = getDimensionEntries(planType);
        if (dimensionEntries.isEmpty()) {
            report.note("No dimension files found for plan type " + planType + ".");
            return report;
        }

        Map<String, EssPlanningDimensionFile> files = new LinkedHashMap<>();
        Map<String, String> namesById = new LinkedHashMap<>();
        List<String> standard = new ArrayList<>();
        List<String> attributes = new ArrayList<>();
        for (Map.Entry<String, String> entry : dimensionEntries.entrySet()) {
            try {
                EssPlanningDimensionFile file = readDimension(entry.getValue());
                files.put(entry.getKey(), file);
                namesById.putAll(file.memberNamesById());
                (isAttributeEntry(entry.getValue()) ? attributes : standard).add(entry.getKey());
            } catch (Exception e) {
                DimensionOutcome outcome =
                        new DimensionOutcome(entry.getKey(), isAttributeEntry(entry.getValue()));
                outcome.failed("could not be read: " + e.getMessage());
                report.add(outcome);
            }
        }

        // Standard dimensions first: an attribute dimension can only be associated with a base
        // dimension that is already there.
        createDimensions(cube, standard, attributes, files, report);
        for (String dimension : standard) {
            build(cube, dimension, files.get(dimension), planType, false, report);
        }
        for (String dimension : attributes) {
            build(cube, dimension, files.get(dimension), planType, true, report);
        }
        associateAttributes(cube, planType, namesById, report);
        return report;
    }

    /**
     * Creates the dimensions, and tells Essbase what kind each is.
     *
     * <p>One document: dimensions have to exist before members can hang off them, and creating them one
     * at a time would be a call each for no benefit. The type and density come from the file's own
     * header block - a dimension Planning calls Accounts has to be an accounts dimension here too, or
     * every time balance and variance-reporting flag on its members is rejected.
     */
    private void createDimensions(EssCube cube, List<String> standard, List<String> attributes,
            Map<String, EssPlanningDimensionFile> files, Report report) {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        for (String dimension : standard) {
            edit.action("dimAdd").attribute("dimName", dimension);
        }
        for (String dimension : attributes) {
            edit.action("dimAdd").attribute("dimName", dimension);
        }
        try {
            cube.batchOutlineEdit(edit);
        } catch (Exception e) {
            report.note("Creating the dimensions failed: " + e.getMessage()
                    + " They may already exist, in which case the members will still build.");
        }

        // One dimension at a time. Essbase refuses some of these - an attribute dimension that is
        // already one, a density it will not accept on this outline - and a refused action fails the
        // whole document, so batching them would mean one awkward dimension costing every other
        // dimension its type. Which is what it did: sent together, none of them applied.
        List<String> untyped = new ArrayList<>();
        for (String dimension : standard) {
            EssPlanningDimensionFile file = files.get(dimension);
            EssBatchOutlineEdit typed = EssBatchOutlineEdit.create();
            EssBatchOutlineEdit.DimensionAction action = typed.updateDimension(dimension);
            category(file.getDimensionType()).ifPresent(action::category);
            if ("Dense".equalsIgnoreCase(file.getDensity())) {
                action.storage(EssBatchOutlineEdit.DimensionStorage.DENSE);
            } else if ("Sparse".equalsIgnoreCase(file.getDensity())) {
                action.storage(EssBatchOutlineEdit.DimensionStorage.SPARSE);
            }
            if (!apply(cube, typed)) {
                untyped.add(dimension);
            }
        }
        for (String dimension : attributes) {
            EssBatchOutlineEdit typed = EssBatchOutlineEdit.create();
            typed.updateDimension(dimension).category(EssBatchOutlineEdit.DimensionCategory.ATTRIBUTE);
            if (!apply(cube, typed)) {
                untyped.add(dimension);
            }
        }
        if (!untyped.isEmpty()) {
            report.note("Dimension type or density not set for " + untyped
                    + " - their members still build, but time balance and variance reporting may not"
                    + " stick on a dimension Essbase does not consider an accounts dimension.");
        }
    }

    /** @return whether the server accepted the document */
    private static boolean apply(EssCube cube, EssBatchOutlineEdit edit) {
        try {
            return cube.batchOutlineEdit(edit).isSuccessful();
        } catch (Exception e) {
            logger.debug("Batch outline edit refused", e);
            return false;
        }
    }

    private static java.util.Optional<EssBatchOutlineEdit.DimensionCategory> category(String planningType) {
        switch (planningType == null ? "" : planningType.toLowerCase(java.util.Locale.ROOT)) {
            case "accounts": return java.util.Optional.of(EssBatchOutlineEdit.DimensionCategory.ACCOUNT);
            case "time": return java.util.Optional.of(EssBatchOutlineEdit.DimensionCategory.TIME);
            case "country": return java.util.Optional.of(EssBatchOutlineEdit.DimensionCategory.COUNTRY);
            case "currency": return java.util.Optional.of(EssBatchOutlineEdit.DimensionCategory.CURRENCY_TYPE);
            default: return java.util.Optional.empty();
        }
    }

    /** One dimension: the members, then the storage that could not be set while adding them. */
    private void build(EssCube cube, String dimension, EssPlanningDimensionFile file, String planType,
            boolean attribute, Report report) {
        DimensionOutcome outcome = new DimensionOutcome(dimension, attribute);
        try {
            EssBatchOutlineEdit members = file.toBatchOutlineEdit(planType);
            if (members.size() == 0) {
                outcome.note("no members for this plan type");
                report.add(outcome);
                return;
            }
            EssBatchOutlineEditResult result = cube.batchOutlineEdit(members);
            outcome.built(Math.max(result.getAdded(), 0) + Math.max(result.getUpdated(), 0));
            result.getErrors().forEach(message -> outcome.note(message.getText()));
        } catch (Exception e) {
            outcome.failed(e.getMessage());
            report.add(outcome);
            return;
        }

        EssBatchOutlineEdit refinement = file.toStorageRefinement(planType);
        if (refinement.size() > 0) {
            try {
                cube.batchOutlineEdit(refinement);
            } catch (Exception e) {
                // Documented as best effort on toStorageRefinement: the dimension is built, some
                // label-only and dynamic-calc storage is not set. Not a failure of the dimension.
                outcome.note("storage types not applied for " + refinement.size()
                        + " member(s) - Essbase refused them on this outline");
            }
        }
        List<String> ambiguous = file.ambiguousMemberNames(planType);
        if (!ambiguous.isEmpty()) {
            outcome.note("shared member name(s) that cannot be updated by name: " + ambiguous);
        }
        report.add(outcome);
    }

    /**
     * The attribute associations, which only the batch-outline-edit XML has.
     *
     * <p>The dimension-level ones name their dimensions and go straight across. The member-level ones
     * are Planning GUIDs, and are translated through the {@code UUID} column of the dimension files -
     * which is the one thing that makes that XML usable against an Essbase outline at all.
     */
    private void associateAttributes(EssCube cube, String planType, Map<String, String> namesById,
            Report report) {
        String entry = findBatchOutlineEditXml(planType);
        if (entry == null) {
            report.note("No batch outline edit XML in the snapshot, so no attribute associations.");
            return;
        }
        String xml;
        try (InputStream in = zip.getInputStream(entries.get(entry))) {
            xml = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            report.note("Could not read " + entry + ": " + e.getMessage());
            return;
        }

        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        int dimensionLevel = 0;
        int memberLevel = 0;
        int unresolved = 0;
        for (String element : elements(xml, "dimAssoc")) {
            String dimension = attribute(element, "dimName");
            String attributeDimension = attribute(element, "attrDim");
            if (!dimension.isEmpty() && !attributeDimension.isEmpty()) {
                edit.associateAttributeDimension(dimension, attributeDimension);
                dimensionLevel++;
            }
        }
        for (String element : elements(xml, "mbrAssoc")) {
            String member = namesById.get(attribute(element, "thisMbr"));
            String attributeMember = namesById.get(attribute(element, "attrMbr"));
            String attributeDimension = attribute(element, "attrDim");
            if (member == null || attributeMember == null || attributeDimension.isEmpty()) {
                unresolved++;
                continue;
            }
            edit.associateAttribute(member, attributeDimension, attributeMember);
            memberLevel++;
        }
        if (edit.size() == 0) {
            report.note("No attribute associations could be resolved from " + entry + ".");
            return;
        }
        try {
            cube.batchOutlineEdit(edit);
            report.note("Attribute associations: " + dimensionLevel + " dimension-level, "
                    + memberLevel + " member-level"
                    + (unresolved == 0 ? "" : ", " + unresolved + " unresolved"));
        } catch (Exception e) {
            report.note("Attribute associations were not applied: " + e.getMessage());
        }
    }

    /**
     * The batch outline edit document for a plan type.
     *
     * <p>Named for the Essbase application rather than the plan type - {@code AVision_Vision.xml} in an
     * application whose cube is Vision - so this looks for the plan type anywhere in the name under the
     * Essbase data folder rather than assuming the prefix.
     */
    private String findBatchOutlineEditXml(String planType) {
        for (String name : entries.keySet()) {
            if (name.contains("/Essbase Data/") && name.endsWith(".xml")
                    && name.contains(planType) && !name.contains("Outline Snapshots")) {
                return name;
            }
        }
        return null;
    }

    /** The elements with this name, whole, without parsing a megabyte of XML into a tree. */
    private static List<String> elements(String xml, String name) {
        List<String> found = new ArrayList<>();
        String open = "<" + name;
        int at = 0;
        while ((at = xml.indexOf(open, at)) >= 0) {
            int end = xml.indexOf('>', at);
            if (end < 0) {
                break;
            }
            found.add(xml.substring(at, end));
            at = end;
        }
        return found;
    }

    private static String attribute(String element, String name) {
        int at = element.indexOf(name + "=\"");
        if (at < 0) {
            return "";
        }
        int start = at + name.length() + 2;
        int end = element.indexOf('"', start);
        return end < 0 ? "" : element.substring(start, end);
    }

}
