package com.appliedolap.essbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * One dimension as EPM Cloud exports it, read from the {@code .csv} in an artifact snapshot.
 *
 * <p>This, rather than the batch-outline-edit XML in the same snapshot, is what an Essbase outline can
 * be built from. The XML looks like the obvious candidate and is not: every member in it is addressed
 * by a Planning GUID with {@code isMbrId="true"}, and it carries no member names, no parents and no
 * hierarchy at all. It is a property refresh for members Planning has already created - useful for
 * replaying properties, useless for building a dimension. These files carry the names, the parents,
 * the aliases and the properties, which is everything an outline needs.
 *
 * <p>Snapshot layout, for finding them: shared dimensions are under
 * {@code <app>/resource/Global Artifacts/Common Dimensions/Standard Dimensions} and
 * {@code .../Attribute Dimensions}, and a cube's own dimensions under
 * {@code <app>/resource/Cube/<cube>/Standard Dimensions}.
 *
 * <p>The file is a CSV with an XML header block bolted on the front - a {@code <DIMENSIONS>} document
 * describing the dimension, then {@code #--!}, then an ordinary header row and the members. Both
 * halves are read: the XML says whether the dimension is dense or sparse and what type it is, the CSV
 * says what is in it.
 */
public class EssPlanningDimensionFile {

    /** One member row, with the columns that mean something to an Essbase outline pulled out. */
    public static class Member {

        private final Map<String, String> columns;

        Member(Map<String, String> columns) {
            this.columns = columns;
        }

        /** The member name, which is the first column and is named after the dimension. */
        public String getName() {
            return first();
        }

        public String getParent() {
            return value("Parent");
        }

        /** Planning's own id for this member - the GUID the batch-outline-edit XML addresses it by. */
        public String getId() {
            return value("UUID");
        }

        /** Alias table name to alias, for every {@code Alias: X} column that had a value. */
        public Map<String, String> getAliases() {
            Map<String, String> aliases = new LinkedHashMap<>();
            columns.forEach((column, value) -> {
                if (column.startsWith("Alias: ") && !value.isEmpty()) {
                    aliases.put(column.substring("Alias: ".length()), value);
                }
            });
            return aliases;
        }

        /**
         * The consolidation operator for a plan type, falling back to the dimension-wide one.
         *
         * <p>Planning writes a column per plan type - {@code Aggregation (Vision)} - because a member
         * can consolidate differently in each. Which is why {@link EssPlanningDimensionFile#toBatchOutlineEdit}
         * takes a plan type rather than guessing.
         */
        public String getConsolidation(String planType) {
            String specific = value("Aggregation (" + planType + ")");
            return specific.isEmpty() ? value("Aggregation") : specific;
        }

        public String getDataStorage(String planType) {
            String specific = value("Data Storage (" + planType + ")");
            return specific.isEmpty() ? value("Data Storage") : specific;
        }

        public String getFormula(String planType) {
            String specific = clean(value("Formula (" + planType + ")"));
            return specific.isEmpty() ? clean(value("Formula")) : specific;
        }

        public String getSolveOrder(String planType) {
            String specific = value("Solve Order (" + planType + ")");
            return specific.isEmpty() ? value("Solve Order") : specific;
        }

        /**
         * Whether this member belongs to the given plan type.
         *
         * <p>A Planning application's dimensions are shared across its cubes, and a member can be
         * switched off for one of them - so a dimension file holds more members than any one cube's
         * outline should. Members with no {@code Plan Type (X)} column at all are kept: that is an
         * attribute dimension, which has no per-plan columns and belongs to whichever cube uses it.
         */
        public boolean isInPlanType(String planType) {
            String column = "Plan Type (" + planType + ")";
            return !columns.containsKey(column) || "true".equalsIgnoreCase(value(column));
        }

        /** Every UDA on the member. Planning writes them comma-separated in one column. */
        public List<String> getUdas() {
            String uda = clean(value("UDA"));
            if (uda.isEmpty()) {
                return Collections.emptyList();
            }
            List<String> udas = new ArrayList<>();
            for (String one : uda.split(",")) {
                if (!one.trim().isEmpty()) {
                    udas.add(one.trim());
                }
            }
            return udas;
        }

        public String getTimeBalance() {
            return value("Time Balance");
        }

        public String getSkipValue() {
            return value("Skip Value");
        }

        public String getVarianceReporting() {
            return value("Variance Reporting");
        }

        public boolean isTwoPass() {
            return "true".equalsIgnoreCase(value("Two Pass Calculation"));
        }

        public String getDescription() {
            return clean(value("Description"));
        }

        /** Any column, by its header, for something this class does not model. */
        public String value(String column) {
            String value = columns.get(column);
            return value == null ? "" : value.trim();
        }

        public Map<String, String> getColumns() {
            return Collections.unmodifiableMap(columns);
        }

        private String first() {
            return columns.isEmpty() ? "" : columns.values().iterator().next().trim();
        }

        /** Planning writes an absent value as the literal {@code <none>} rather than as nothing. */
        private static String clean(String value) {
            return value == null || value.equals("<none>") ? "" : value.trim();
        }

        @Override
        public String toString() {
            return getName() + " (parent " + getParent() + ")";
        }

    }

    private final String dimensionName;

    private final Map<String, String> dimensionAttributes;

    private final List<Member> members;

    private EssPlanningDimensionFile(String dimensionName, Map<String, String> dimensionAttributes,
            List<Member> members) {
        this.dimensionName = dimensionName;
        this.dimensionAttributes = dimensionAttributes;
        this.members = members;
    }

    /**
     * Reads one dimension file.
     *
     * @param file the {@code .csv} from an artifact snapshot
     * @return the dimension and its members, in the order the file lists them
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if it is not one of these files
     */
    public static EssPlanningDimensionFile read(Path file) throws IOException {
        return parse(Files.readString(file, StandardCharsets.UTF_8));
    }

    static EssPlanningDimensionFile parse(String text) {
        // The byte order mark Planning writes, which would otherwise end up inside the first header name.
        if (!text.isEmpty() && text.charAt(0) == '﻿') {
            text = text.substring(1);
        }
        int headerEnd = text.indexOf("</DIMENSIONS>");
        if (headerEnd < 0) {
            throw new IllegalArgumentException(
                    "Not an EPM Cloud dimension file: no <DIMENSIONS> header block");
        }
        Map<String, String> attributes = dimensionAttributes(text.substring(0, headerEnd));
        String body = text.substring(headerEnd + "</DIMENSIONS>".length());

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(body))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank() && !line.startsWith("#--!") && !line.startsWith("#!")) {
                    lines.add(line);
                }
            }
        } catch (IOException impossible) {
            throw new IllegalStateException(impossible);
        }
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Not an EPM Cloud dimension file: no member rows");
        }

        List<String> headers = splitRow(lines.get(0));
        for (int i = 0; i < headers.size(); i++) {
            headers.set(i, headers.get(i).trim());
        }
        List<Member> members = new ArrayList<>();
        for (String line : lines.subList(1, lines.size())) {
            List<String> values = splitRow(line);
            Map<String, String> columns = new LinkedHashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                columns.put(headers.get(i), i < values.size() ? values.get(i) : "");
            }
            // The first column is the member name, and a row without one is not a member.
            if (!columns.values().iterator().next().trim().isEmpty()) {
                members.add(new Member(columns));
            }
        }
        // The first column's header is the dimension's name - "Account", "Entity" - which is how a
        // renamed file still reports the dimension it actually describes.
        return new EssPlanningDimensionFile(headers.get(0), attributes, members);
    }

    /**
     * The attributes on the {@code <Dimension>} element, for the few that matter to an outline.
     *
     * <p>Read with a small scan rather than an XML parser: the header block is a fragment whose body
     * is followed by CSV, so it is not a document, and the alternative is extracting the substring to
     * parse it - which is the same scan with a parser on the end.
     */
    private static Map<String, String> dimensionAttributes(String header) {
        Map<String, String> attributes = new LinkedHashMap<>();
        int start = header.indexOf("<Dimension ");
        if (start < 0) {
            return attributes;
        }
        int end = header.indexOf('>', start);
        String element = end < 0 ? header.substring(start) : header.substring(start, end);
        int i = 0;
        while ((i = element.indexOf('=', i)) > 0) {
            int nameEnd = i;
            int nameStart = nameEnd;
            while (nameStart > 0 && !Character.isWhitespace(element.charAt(nameStart - 1))) {
                nameStart--;
            }
            int quote = element.indexOf('"', i);
            if (quote < 0) {
                break;
            }
            int close = element.indexOf('"', quote + 1);
            if (close < 0) {
                break;
            }
            attributes.put(element.substring(nameStart, nameEnd), element.substring(quote + 1, close));
            i = close + 1;
        }
        return attributes;
    }

    /** Minimal CSV splitting: commas separate, double quotes group, doubled quotes escape. */
    private static List<String> splitRow(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quoted) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        value.append('"');
                        i++;
                    } else {
                        quoted = false;
                    }
                } else {
                    value.append(c);
                }
            } else if (c == '"') {
                quoted = true;
            } else if (c == ',') {
                values.add(value.toString());
                value.setLength(0);
            } else {
                value.append(c);
            }
        }
        values.add(value.toString());
        return values;
    }

    /** The dimension's name, taken from the first column's header. */
    public String getDimensionName() {
        return dimensionName;
    }

    /** @return "Sparse" or "Dense" as the file declares it, or empty if it did not */
    public String getDensity() {
        return dimensionAttributes.getOrDefault("density", "");
    }

    /** @return Planning's dimension type, e.g. "None", "Account", "Time" */
    public String getDimensionType() {
        return dimensionAttributes.getOrDefault("dimensionType", "");
    }

    public Map<String, String> getDimensionAttributes() {
        return Collections.unmodifiableMap(dimensionAttributes);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Planning member ids to member names, for every member in the file.
     *
     * <p>The map that makes the batch-outline-edit XML in the same snapshot readable: it addresses
     * every member by the id in the {@code UUID} column here, and carries the name nowhere at all.
     *
     * @return id to name, skipping members the file gave no id
     */
    public Map<String, String> memberNamesById() {
        Map<String, String> names = new LinkedHashMap<>();
        for (Member member : members) {
            if (!member.getId().isEmpty()) {
                names.put(member.getId(), member.getName());
            }
        }
        return names;
    }

    /**
     * The members of this dimension as a batch outline edit, ready to apply to a cube.
     *
     * <p>Every member is a {@code mbrAddOrUpdate}, so applying the same file twice is how you refresh a
     * dimension rather than how you get errors. Parents come before their children, because the server
     * applies actions in order and a member cannot be added under a parent that does not exist yet -
     * the file is usually in that order already, and this does not assume it.
     *
     * <p>The dimension member itself is not added: an outline's dimension root comes from the cube's
     * definition, and the rows whose parent is the dimension attach to it. A dimension the cube does
     * not have will fail loudly on its first member, which is the right way round.
     *
     * @param planType which cube's columns to read, for the properties Planning records per plan type
     * @return the edit, which the caller can add to before applying
     */
    public EssBatchOutlineEdit toBatchOutlineEdit(String planType) {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        for (Member member : parentsFirst(planType)) {
            EssBatchOutlineEdit.MemberAction action =
                    edit.addOrUpdateMember(member.getName(), member.getParent());
            java.util.Optional<EssBatchOutlineEdit.DataStorage> storage =
                    dataStorage(member.getDataStorage(planType));
            if (storage.orElse(null) == EssBatchOutlineEdit.DataStorage.SHARED) {
                // A shared member points at another member's data and has no properties of its own -
                // it takes the prototype's. Giving it an alias, a UDA or a consolidation is an error,
                // and one that fails the whole document, so it gets its name, its parent and nothing.
                action.dataStorage(EssBatchOutlineEdit.DataStorage.SHARED);
                continue;
            }
            apply(action, member, planType);
            // Only the storage that is legal on a member with no children, because that is what every
            // member is at the instant it is added. The rest is {@link #toStorageRefinement}.
            storage.filter(EssPlanningDimensionFile::isLegalOnALeaf).ifPresent(action::dataStorage);
        }
        return edit;
    }

    /**
     * The storage types that could not be set while the dimension was being built, as a second edit.
     *
     * <p>Label-only and dynamic-calc describe a member that aggregates something, so neither is valid on
     * a member with no children - and every member is childless at the instant it is added. They can
     * only be set once the hierarchy exists, which means a second document and a second call: a single
     * document mixing {@code mbrAddOrUpdate} and {@code mbrUpdate} for the members it is creating is
     * refused outright, the server resolving {@code mbrUpdate} against the outline as it was before the
     * document ran.
     *
     * <p><strong>Best effort.</strong> Whether Essbase accepts a given storage on a given member depends
     * on rules this cannot fully know from the file - two are handled here, that a member which lost all
     * its children to plan-type filtering is now a leaf, and that an ancestor of a shared member cannot
     * be dynamic calc - and there are others, each of which fails the whole document rather than the one
     * action. Apply this separately from {@link #toBatchOutlineEdit}, and treat a failure as "the
     * dimension is built, some storage types are not set" rather than as the build having failed.
     *
     * @param planType which cube's columns to read
     * @return an edit setting the remaining storage types, which may be empty
     */
    public EssBatchOutlineEdit toStorageRefinement(String planType) {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        List<Member> ordered = parentsFirst(planType);

        Map<String, Integer> occurrences = new LinkedHashMap<>();
        Set<String> parents = new LinkedHashSet<>();
        Map<String, String> parentOf = new LinkedHashMap<>();
        for (Member member : ordered) {
            occurrences.merge(member.getName(), 1, Integer::sum);
            parents.add(member.getParent());
            parentOf.putIfAbsent(member.getName(), member.getParent());
        }

        Set<String> aboveAShare = new LinkedHashSet<>();
        for (Member member : ordered) {
            if (dataStorage(member.getDataStorage(planType))
                    .orElse(null) != EssBatchOutlineEdit.DataStorage.SHARED) {
                continue;
            }
            String ancestor = parentOf.get(member.getName());
            while (ancestor != null && aboveAShare.add(ancestor)) {
                ancestor = parentOf.get(ancestor);
            }
        }

        for (Member member : ordered) {
            // A repeated name is a shared member's, and cannot be the target of an mbrUpdate at all -
            // the server cannot tell which one is meant. Which is exactly why EPM Cloud's own exported
            // document addresses every member by GUID instead of by name.
            if (occurrences.getOrDefault(member.getName(), 0) > 1 || !parents.contains(member.getName())) {
                continue;
            }
            dataStorage(member.getDataStorage(planType))
                    .filter(storage -> !isLegalOnALeaf(storage))
                    .filter(storage -> !isDynamic(storage) || !aboveAShare.contains(member.getName()))
                    .ifPresent(storage -> edit.updateMember(member.getName()).dataStorage(storage));
        }
        return edit;
    }

    /**
     * The names this file cannot set every property on, because they appear more than once.
     *
     * <p>A shared member repeats its prototype's name, and a repeated name cannot be the target of an
     * {@code mbrUpdate}. Both members are still created; what cannot be set afterwards is anything
     * {@link #toStorageRefinement} would have set on them.
     *
     * @param planType the plan type whose members to consider
     * @return the duplicated names, in file order
     */
    public List<String> ambiguousMemberNames(String planType) {
        Map<String, Integer> occurrences = new LinkedHashMap<>();
        for (Member member : parentsFirst(planType)) {
            occurrences.merge(member.getName(), 1, Integer::sum);
        }
        List<String> ambiguous = new ArrayList<>();
        occurrences.forEach((name, count) -> {
            if (count > 1) {
                ambiguous.add(name);
            }
        });
        return ambiguous;
    }

    private static boolean isDynamic(EssBatchOutlineEdit.DataStorage storage) {
        return storage == EssBatchOutlineEdit.DataStorage.DYNAMIC_CALC
                || storage == EssBatchOutlineEdit.DataStorage.DYNAMIC_CALC_AND_STORE;
    }

    private static boolean isLegalOnALeaf(EssBatchOutlineEdit.DataStorage storage) {
        switch (storage) {
            case LABEL_ONLY:
            case DYNAMIC_CALC:
            case DYNAMIC_CALC_AND_STORE:
                return false;
            default:
                return true;
        }
    }


    private void apply(EssBatchOutlineEdit.MemberAction action, Member member, String planType) {
        consolidation(member.getConsolidation(planType)).ifPresent(action::consolidation);
        timeBalance(member.getTimeBalance()).ifPresent(action::timeBalance);
        skip(member.getSkipValue()).ifPresent(action::skip);
        member.getAliases().forEach(action::alias);
        member.getUdas().forEach(action::uda);
        String formula = member.getFormula(planType);
        if (!formula.isEmpty()) {
            action.formula(formula);
        }
        String description = member.getDescription();
        if (!description.isEmpty()) {
            action.comment(description);
        }
        if (member.isTwoPass()) {
            action.twoPassCalc(true);
        }
        if (!member.getVarianceReporting().isEmpty()) {
            action.expense("expense".equalsIgnoreCase(member.getVarianceReporting()));
        }
        String solveOrder = member.getSolveOrder(planType);
        if (!solveOrder.isEmpty()) {
            try {
                action.solveOrder(Integer.parseInt(solveOrder));
            } catch (NumberFormatException ignored) {
                // Planning has written something that is not a number. Leaving the solve order alone is
                // better than failing the whole dimension over one column.
            }
        }
    }

    /**
     * The members this plan type has, with every parent ahead of its children.
     *
     * <p>A stable sort by depth rather than a rebuild of the hierarchy: it keeps the file's own order
     * within each level, which is the order the dimension is meant to be in, while guaranteeing the
     * only ordering the server actually requires.
     */
    private List<Member> parentsFirst(String planType) {
        List<Member> included = new ArrayList<>();
        Set<String> names = new LinkedHashSet<>();
        for (Member member : members) {
            if (member.isInPlanType(planType)) {
                included.add(member);
                names.add(member.getName());
            }
        }
        Map<String, Member> byName = new LinkedHashMap<>();
        included.forEach(member -> byName.putIfAbsent(member.getName(), member));

        List<Member> ordered = new ArrayList<>(included.size());
        Set<String> placed = new LinkedHashSet<>();
        List<Member> pending = new ArrayList<>(included);
        boolean progress = true;
        while (!pending.isEmpty() && progress) {
            progress = false;
            List<Member> stillPending = new ArrayList<>();
            for (Member member : pending) {
                String parent = member.getParent();
                // A parent outside this file is the dimension root, or a member of another dimension in
                // a shared hierarchy - either way it is not this file's job to create, so the member is
                // ready to go.
                if (!names.contains(parent) || placed.contains(parent)) {
                    ordered.add(member);
                    placed.add(member.getName());
                    progress = true;
                } else {
                    stillPending.add(member);
                }
            }
            pending = stillPending;
        }
        // A cycle, or a parent that is its own ancestor. Appending rather than dropping: the server will
        // say what is wrong with them far more precisely than this could.
        ordered.addAll(pending);
        return ordered;
    }

    private static java.util.Optional<EssBatchOutlineEdit.Consolidation> consolidation(String operator) {
        for (EssBatchOutlineEdit.Consolidation consolidation : EssBatchOutlineEdit.Consolidation.values()) {
            if (consolidation.getOperator().equals(operator.trim())) {
                return java.util.Optional.of(consolidation);
            }
        }
        return java.util.Optional.empty();
    }

    /** Planning's words for data storage are not Essbase's - "store" against "storeData", and so on. */
    private static java.util.Optional<EssBatchOutlineEdit.DataStorage> dataStorage(String planning) {
        switch (planning.trim().toLowerCase(Locale.ROOT)) {
            case "store": return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.STORE_DATA);
            case "never share": return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.NEVER_SHARE);
            case "label only": return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.LABEL_ONLY);
            case "dynamic calc": return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.DYNAMIC_CALC);
            case "dynamic calc and store":
                return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.DYNAMIC_CALC_AND_STORE);
            case "shared": return java.util.Optional.of(EssBatchOutlineEdit.DataStorage.SHARED);
            default: return java.util.Optional.empty();
        }
    }

    /**
     * Planning says "balance" and "average" where Essbase says "last" and "avg", and "flow" is Planning's
     * word for no time balance at all.
     */
    private static java.util.Optional<EssBatchOutlineEdit.TimeBalance> timeBalance(String planning) {
        switch (planning.trim().toLowerCase(Locale.ROOT)) {
            case "flow":
            case "none": return java.util.Optional.of(EssBatchOutlineEdit.TimeBalance.NONE);
            case "first": return java.util.Optional.of(EssBatchOutlineEdit.TimeBalance.FIRST);
            case "balance":
            case "last": return java.util.Optional.of(EssBatchOutlineEdit.TimeBalance.LAST);
            case "average": return java.util.Optional.of(EssBatchOutlineEdit.TimeBalance.AVERAGE);
            default: return java.util.Optional.empty();
        }
    }

    private static java.util.Optional<EssBatchOutlineEdit.Skip> skip(String planning) {
        switch (planning.trim().toLowerCase(Locale.ROOT)) {
            case "none": return java.util.Optional.of(EssBatchOutlineEdit.Skip.NONE);
            case "missing": return java.util.Optional.of(EssBatchOutlineEdit.Skip.MISSING);
            case "zero": return java.util.Optional.of(EssBatchOutlineEdit.Skip.ZERO);
            case "missing and zero":
            case "both": return java.util.Optional.of(EssBatchOutlineEdit.Skip.BOTH);
            default: return java.util.Optional.empty();
        }
    }

    @Override
    public String toString() {
        return dimensionName + " (" + members.size() + " members)";
    }

}
