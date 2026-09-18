package com.appliedolap.essbase;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A batch outline edit document, built action by action.
 *
 * <p>Batch outline editing changes many things about an outline in one transaction, applied in the
 * order they are listed - so this builds a document rather than making calls, and
 * {@link EssCube#batchOutlineEdit(EssBatchOutlineEdit)} sends the whole thing once.
 *
 * <pre>
 * EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
 * edit.addMember("Widget", "Product")
 *         .consolidation(Consolidation.ADD)
 *         .alias("Our widget")
 *         .uda("New");
 * edit.updateMember("Cola").formula("100-10;");
 * edit.deleteMember("Discontinued");
 * EssBatchOutlineEditResult result = cube.batchOutlineEdit(edit);
 * </pre>
 *
 * <p>Written out by hand rather than through the generated {@code OtlEditMain}, which cannot express
 * an action at all, and whose {@code restructOption} is spelled in a way the server answers HTTP 500
 * to. The vocabulary here is Essbase's own {@code mbredit.xsd}, which ships inside the install at
 * {@code products/Essbase/EssbaseServer/bin/mbredit.xsd} and is the only complete description of this
 * format - the published REST spec erases it.
 *
 * <p>Covers the member and dimension actions. The smart-list and dependency-list families the schema
 * also defines are not here: they are Planning-oriented, and adding them without a way to exercise
 * them would be guessing in public. {@link EssCube#batchOutlineEdit(String)} takes a document as text
 * for anything this does not model.
 */
public class EssBatchOutlineEdit {

    /** How the server consolidates a member into its parent. */
    public enum Consolidation {

        ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"), PERCENT("%"), NEVER("~"), EXPONENT("^");

        private final String operator;

        Consolidation(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

    }

    /** Where a member's data lives. */
    public enum DataStorage {

        STORE_DATA("storeData"),
        DYNAMIC_CALC_AND_STORE("dynamicStore"),
        DYNAMIC_CALC("dynamic"),
        NEVER_SHARE("neverShare"),
        LABEL_ONLY("labelOnly"),
        /** Only meaningful in a unique-name outline; elsewhere use {@code prototypeMbr}. */
        SHARED("shared");

        private final String value;

        DataStorage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** Time balance, for an accounts dimension. */
    public enum TimeBalance {

        NONE("none"), FIRST("first"), LAST("last"), AVERAGE("avg");

        private final String value;

        TimeBalance(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** What a time-balance member skips. */
    public enum Skip {

        NONE("none"), MISSING("missing"), ZERO("zero"), BOTH("both");

        private final String value;

        Skip(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** Whether a dimension's blocks are dense or sparse. */
    public enum DimensionStorage {

        DENSE("dense"), SPARSE("sparse");

        private final String value;

        DimensionStorage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** A dimension's type, in the schema's vocabulary. */
    public enum DimensionCategory {

        ACCOUNT("account"), ATTRIBUTE("attribute"), COUNTRY("country"), CURRENCY_PARTITION("curpartition"),
        NONE("none"), TIME("time"), CURRENCY_TYPE("currencytype");

        private final String value;

        DimensionCategory(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** How a hierarchy under a dimension is aggregated. */
    public enum HierarchyType {

        STORED("stored"), DYNAMIC("dynamic"), MULTIPLE("multiHierarchy");

        private final String value;

        HierarchyType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * What to restructure after the edit.
     *
     * <p>Spelled the way the server accepts, not the way the REST spec documents: the spec's
     * {@code ALL_DATA} is answered with HTTP 500, and {@code allData} is what works.
     */
    public enum Restructure {

        ALL_DATA("allData"),
        /** Indexes only - drops the data. */
        NO_DATA("noData"),
        LOW_DATA("lowData"),
        /** Input data; block storage only. */
        IN_DATA("inData");

        private final String value;

        Restructure(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /** Which members a {@code dataStorageSet} applies to. */
    public enum ApplyTo {

        LEVEL("level"), GENERATION("generation"), CHILDREN("children"),
        INCLUSIVE_CHILDREN("ichildren"), DESCENDANTS("descendants"),
        INCLUSIVE_DESCENDANTS("idescendants");

        private final String value;

        ApplyTo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum SortOrder {

        ASCENDING("ascending"), DESCENDING("descending");

        private final String value;

        SortOrder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * -1 asks the server to skip the outline version check and reset the version to 0, which is what
     * EPM Cloud's own exported documents use and what you want unless you are deliberately guarding
     * against a concurrent edit.
     */
    private int outlineVersion = -1;

    private Boolean validate;

    private Boolean validateFormulas;

    private Boolean keepTransaction;

    private Restructure restructure;

    private final List<Action> actions = new ArrayList<>();

    public static EssBatchOutlineEdit create() {
        return new EssBatchOutlineEdit();
    }

    /**
     * An edit that rebuilds a dimension from an EPM Cloud artifact snapshot.
     *
     * <p>Reads the dimension's {@code .csv} - not the batch-outline-edit XML that sits in the same
     * snapshot, which cannot do this. That file addresses every member by a Planning GUID and carries
     * no names, no parents and no hierarchy: it refreshes properties on members that already exist.
     * The {@code .csv} is the one with the dimension in it. See {@link EssPlanningDimensionFile} for
     * where they live in a snapshot.
     *
     * <pre>
     * EssBatchOutlineEdit edit = EssBatchOutlineEdit.fromPlanningDimension(
     *         Path.of("Artifact Snapshot/HP-Vision/resource/Global Artifacts/"
     *                 + "Common Dimensions/Standard Dimensions/Account.csv"), "Vision");
     * EssBatchOutlineEditResult result = cube.batchOutlineEdit(edit);
     * </pre>
     *
     * @param dimensionFile the dimension's {@code .csv}
     * @param planType which cube's columns to read - Planning records consolidation, storage, formula
     *                 and solve order per plan type, and they differ
     * @return an edit adding or updating every member of that dimension, parents first. Label-only and
     *         dynamic-calc storage is not in it - see {@link EssPlanningDimensionFile#toStorageRefinement},
     *         which is a second call for reasons explained there
     * @throws IOException if the file cannot be read
     */
    public static EssBatchOutlineEdit fromPlanningDimension(Path dimensionFile, String planType)
            throws IOException {
        return EssPlanningDimensionFile.read(dimensionFile).toBatchOutlineEdit(planType);
    }

    /**
     * The outline version this edit expects, for guarding against a concurrent change.
     *
     * @param outlineVersion the version, or -1 to skip the check (the default)
     */
    public EssBatchOutlineEdit outlineVersion(int outlineVersion) {
        this.outlineVersion = outlineVersion;
        return this;
    }

    /** Whether the server validates the outline after applying the edit. Defaults to true, server-side. */
    public EssBatchOutlineEdit validate(boolean validate) {
        this.validate = validate;
        return this;
    }

    public EssBatchOutlineEdit validateFormulas(boolean validateFormulas) {
        this.validateFormulas = validateFormulas;
        return this;
    }

    /**
     * Whether to hold the edit open as a transaction. Required by a few actions - setting a
     * dimension's data member among them - and off by default.
     */
    public EssBatchOutlineEdit keepTransaction(boolean keepTransaction) {
        this.keepTransaction = keepTransaction;
        return this;
    }

    public EssBatchOutlineEdit restructure(Restructure restructure) {
        this.restructure = restructure;
        return this;
    }

    /**
     * Adds a member, failing with a warning if it already exists.
     *
     * @param member the new member's name
     * @param parent the parent to add it under
     * @return the action, for setting the new member's properties
     */
    public MemberAction addMember(String member, String parent) {
        return add(new MemberAction("mbrAdd").attribute("mbrName", member).attribute("parent", parent));
    }

    /** Adds the member, or updates it where it already exists. */
    public MemberAction addOrUpdateMember(String member, String parent) {
        return add(new MemberAction("mbrAddOrUpdate")
                .attribute("mbrName", member).attribute("parent", parent));
    }

    /** Changes an existing member's properties. */
    public MemberAction updateMember(String member) {
        return add(new MemberAction("mbrUpdate").attribute("thisMbr", member));
    }

    public Action deleteMember(String member) {
        return add(new Action("mbrDelete").attribute("thisMbr", member));
    }

    /** @param newName what the member should be called afterwards */
    public Action renameMember(String member, String newName) {
        return add(new Action("mbrRename").attribute("thisMbr", member).attribute("mbrName", newName));
    }

    public Action moveMember(String member, String newParent) {
        return add(new Action("mbrMove").attribute("thisMbr", member).attribute("parent", newParent));
    }

    /** Associates one attribute member with a base member. */
    public Action associateAttribute(String member, String attributeDimension, String attributeMember) {
        return add(new Action("mbrAssoc").attribute("thisMbr", member)
                .attribute("attrDim", attributeDimension).attribute("attrMbr", attributeMember));
    }

    /** Removes every attribute association from a base member - all of them, not a named one. */
    public Action dissociateAttributes(String member) {
        return add(new Action("mbrDissociateAttr").attribute("thisMbr", member));
    }

    /** Changes a dimension's own properties. */
    public DimensionAction updateDimension(String dimension) {
        return add(new DimensionAction("dimUpdate").attribute("dimName", dimension));
    }

    /** Attaches an attribute dimension to a base dimension. */
    public Action associateAttributeDimension(String dimension, String attributeDimension) {
        return add(new Action("dimAssoc").attribute("dimName", dimension)
                .attribute("attrDim", attributeDimension));
    }

    public Action sortChildren(String member, SortOrder order, boolean recursive) {
        return add(new Action("sortChildren").attribute("mbrName", member)
                .attribute("sortOption", order.getValue()).attribute("sortRecursive", recursive));
    }

    /**
     * Sets data storage across a swathe of members at once. Level-0 members are skipped by the server.
     *
     * @param number the level or generation number, required when {@code applyTo} is one of those
     */
    public Action setDataStorage(String member, DataStorage storage, ApplyTo applyTo, Integer number) {
        Action action = new Action("dataStorageSet").attribute("thisMbr", member)
                .attribute("dataStorage", storage.getValue()).attribute("applyTo", applyTo.getValue());
        if (number != null) {
            action.attribute("number", number);
        }
        return add(action);
    }

    /**
     * Marks every member of a dimension for deletion, so a following build can spare the ones it
     * mentions and {@link #deleteMarked(String)} removes the rest. The two halves of how Essbase
     * deletes members that a source system has stopped sending.
     */
    public Action markForDelete(String dimension) {
        return add(new Action("markForDelete").attribute("dimName", dimension));
    }

    public Action deleteMarked(String dimension) {
        return add(new Action("deleteMarked").attribute("dimName", dimension));
    }

    /**
     * An action this does not model, by its element name and attributes.
     *
     * <p>The escape hatch that keeps the rest of the builder honest: rather than pretend to cover the
     * whole schema, anything missing - a smart list, a dependency list - can still be expressed here
     * without dropping to raw XML for the entire document.
     *
     * @param element the action's element name, as {@code mbredit.xsd} spells it
     */
    public Action action(String element) {
        return add(new Action(element));
    }

    private <T extends Action> T add(T action) {
        actions.add(action);
        return action;
    }

    /** @return how many actions this document carries */
    public int size() {
        return actions.size();
    }

    /**
     * The document, as the XML the endpoint takes.
     *
     * @return a complete {@code otlEditMain} document
     */
    public String toXml() {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<otlEditMain xmlns=\"mbredit\" otlVersion=\"").append(outlineVersion).append('"');
        appendAttribute(xml, "validate", validate);
        appendAttribute(xml, "validateFormulas", validateFormulas);
        appendAttribute(xml, "keepTransaction", keepTransaction);
        if (restructure != null) {
            xml.append(" restructOption=\"").append(restructure.getValue()).append('"');
        }
        xml.append(">\n");
        for (Action action : actions) {
            action.appendTo(xml);
        }
        return xml.append("</otlEditMain>\n").toString();
    }

    private static void appendAttribute(StringBuilder xml, String name, Boolean value) {
        if (value != null) {
            xml.append(' ').append(name).append("=\"").append(value).append('"');
        }
    }

    @Override
    public String toString() {
        return "Batch outline edit with " + actions.size() + " action(s)";
    }

    /**
     * One action in the document.
     *
     * <p>Attributes are kept in insertion order and written as given. Names are the schema's, so a
     * caller reading {@code mbredit.xsd} can follow what this produces line for line.
     */
    public static class Action {

        private final String element;

        private final Map<String, String> attributes = new LinkedHashMap<>();

        Action(String element) {
            this.element = element;
        }

        /**
         * Sets an attribute the builder does not otherwise expose.
         *
         * @param name the attribute name, as the schema spells it
         * @param value the value; null removes the attribute
         */
        public Action attribute(String name, Object value) {
            if (value == null) {
                attributes.remove(name);
            } else {
                attributes.put(name, String.valueOf(value));
            }
            return this;
        }

        /**
         * Says that the member names in this action are actually member ids.
         *
         * <p>What EPM Cloud's exported documents use throughout: every {@code thisMbr} is a Planning
         * GUID rather than a name. Rarely what you want against Essbase directly.
         */
        public Action byMemberId() {
            return attribute("isMbrId", true);
        }

        /** Where the member should sit among its siblings; omitted means last. */
        public Action after(String sibling) {
            return attribute("preSibling", sibling);
        }

        void appendTo(StringBuilder xml) {
            xml.append("  <").append(element);
            appendAttributes(xml);
            if (hasChildren()) {
                xml.append(">\n");
                appendChildren(xml);
                xml.append("  </").append(element).append(">\n");
            } else {
                xml.append("/>\n");
            }
        }

        void appendAttributes(StringBuilder xml) {
            for (Map.Entry<String, String> attribute : attributes.entrySet()) {
                xml.append(' ').append(attribute.getKey()).append("=\"")
                        .append(escape(attribute.getValue())).append('"');
            }
        }

        boolean hasChildren() {
            return false;
        }

        void appendChildren(StringBuilder xml) {
        }

    }

    /** An action carrying a {@code mbrInfo} block - add, addOrUpdate, update. */
    public static class MemberAction extends Action {

        private final List<String> info = new ArrayList<>();

        private final List<String> aliases = new ArrayList<>();

        private final List<String> udas = new ArrayList<>();

        private Boolean twoPass;

        private Boolean expense;

        MemberAction(String element) {
            super(element);
        }

        // Covariant overrides so that setting a raw attribute in the middle of a chain does not
        // narrow the builder back to Action and lose the mbrInfo setters below.

        @Override
        public MemberAction attribute(String name, Object value) {
            super.attribute(name, value);
            return this;
        }

        @Override
        public MemberAction byMemberId() {
            super.byMemberId();
            return this;
        }

        @Override
        public MemberAction after(String sibling) {
            super.after(sibling);
            return this;
        }

        public MemberAction consolidation(Consolidation consolidation) {
            return element("consolidation", consolidation.getOperator());
        }

        public MemberAction dataStorage(DataStorage storage) {
            return element("dataStorage", storage.getValue());
        }

        public MemberAction hierarchyType(HierarchyType type) {
            return element("hierarchyType", type.getValue());
        }

        public MemberAction timeBalance(TimeBalance timeBalance) {
            return element("timeBalance", timeBalance.getValue());
        }

        public MemberAction skip(Skip skip) {
            return element("skip", skip.getValue());
        }

        public MemberAction solveOrder(int solveOrder) {
            return element("solveOrder", String.valueOf(solveOrder));
        }

        public MemberAction formula(String formula) {
            return element("formula", formula);
        }

        public MemberAction comment(String comment) {
            return element("comment", comment);
        }

        public MemberAction formatString(String formatString) {
            return element("formatStr", formatString);
        }

        /**
         * The member this one shares data with. How a shared member is expressed in a non-unique-name
         * outline, where {@link DataStorage#SHARED} is ignored.
         */
        public MemberAction prototype(String member) {
            return element("prototypeMbr", member);
        }

        /** An alias in the Default table. */
        public MemberAction alias(String alias) {
            return alias("Default", alias);
        }

        public MemberAction alias(String aliasTable, String alias) {
            aliases.add("        <alias aliasTable=\"" + escape(aliasTable) + "\" alias=\""
                    + escape(alias) + "\"/>\n");
            return this;
        }

        /** Adds a UDA, replacing whatever the member had. */
        public MemberAction uda(String uda) {
            return uda(uda, true, false);
        }

        /**
         * @param replace whether this replaces the member's existing UDAs
         * @param remove whether to remove this UDA instead; wins over {@code replace} if both are set
         */
        public MemberAction uda(String uda, boolean replace, boolean remove) {
            udas.add("        <udas name=\"" + escape(uda) + "\" replace=\"" + replace
                    + "\" remove=\"" + remove + "\"/>\n");
            return this;
        }

        /** Two-pass calculation, which is an attribute of {@code mbrInfo} rather than an element. */
        public MemberAction twoPassCalc(boolean twoPass) {
            this.twoPass = twoPass;
            return this;
        }

        /** Expense reporting, for an accounts member. */
        public MemberAction expense(boolean expense) {
            this.expense = expense;
            return this;
        }

        private MemberAction element(String name, String value) {
            info.add("        <" + name + ">" + escape(value) + "</" + name + ">\n");
            return this;
        }

        @Override
        boolean hasChildren() {
            return !info.isEmpty() || !aliases.isEmpty() || !udas.isEmpty()
                    || twoPass != null || expense != null;
        }

        @Override
        void appendChildren(StringBuilder xml) {
            xml.append("    <mbrInfo");
            if (twoPass != null) {
                xml.append(" twoPassCalc=\"").append(twoPass).append('"');
            }
            if (expense != null) {
                xml.append(" expense=\"").append(expense).append('"');
            }
            xml.append(">\n");
            info.forEach(xml::append);
            aliases.forEach(xml::append);
            udas.forEach(xml::append);
            xml.append("    </mbrInfo>\n");
        }

    }

    /** A {@code dimUpdate}, which carries a {@code properties} block rather than a {@code mbrInfo}. */
    public static class DimensionAction extends Action {

        private final List<String> properties = new ArrayList<>();

        private Boolean nameUnique;

        DimensionAction(String element) {
            super(element);
        }

        @Override
        public DimensionAction attribute(String name, Object value) {
            super.attribute(name, value);
            return this;
        }

        @Override
        public DimensionAction byMemberId() {
            super.byMemberId();
            return this;
        }

        public DimensionAction category(DimensionCategory category) {
            return property("category", category.getValue());
        }

        public DimensionAction storage(DimensionStorage storage) {
            return property("storage", storage.getValue());
        }

        public DimensionAction solveOrder(int solveOrder) {
            return property("dimSolveorder", String.valueOf(solveOrder));
        }

        /** Whether member names in this dimension have to be unique across the outline. */
        public DimensionAction nameUnique(boolean nameUnique) {
            this.nameUnique = nameUnique;
            return this;
        }

        private DimensionAction property(String name, String value) {
            properties.add("      <" + name + ">" + escape(value) + "</" + name + ">\n");
            return this;
        }

        @Override
        boolean hasChildren() {
            return true;
        }

        @Override
        void appendChildren(StringBuilder xml) {
            xml.append("    <properties");
            if (nameUnique != null) {
                xml.append(" nameUnique=\"").append(nameUnique).append('"');
            }
            xml.append(">\n");
            properties.forEach(xml::append);
            xml.append("    </properties>\n");
        }

    }

    /**
     * Member names and formulas are outline content, not markup.
     *
     * <p>Not optional politeness: a formula is full of {@code <} and {@code >}, and an unescaped one
     * produces a document the server reads as a different set of actions - or, given that it skips
     * what it does not recognise, as no actions at all and no complaint.
     */
    static String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }

}
