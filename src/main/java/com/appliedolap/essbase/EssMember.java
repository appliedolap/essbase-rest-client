package com.appliedolap.essbase;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface EssMember extends EssObject {

    /**
     * Returns the name of the member.
     *
     * @return the member name
     */
    @Override
    String getName();

    /**
     * The dimension this member belongs to.
     *
     * <p>Needed wherever a member has to be expressed as part of a cell rather than on its own - a
     * drill-through POV is keyed by dimension, and a grid knows only the member names it is painting.
     *
     * @return the dimension name
     */
    String getDimensionName();

    @Override
    Type getType();

    /**
     * Gets the parent member (not implemented yet).
     *
     * @return the parent member, null if none (it's a dimension)
     */
    EssMember getParent();

    /**
     * Gets the level of the member as indicated by the REST API
     *
     * @return the member level
     */
    int getLevel();

    /**
     * Check if the member is a normal member or a dimension member.
     *
     * @return true if it's a dimension, false if normal member
     */
    boolean isDimension();

    /**
     * Checks if the member is a leaf node or level 0 (has no children).
     *
     * @return true if level 0, false otherwise
     */
    boolean isLeaf();

    /**
     * Gets the children of this member.
     *
     * @return the children of this member or an empty collection if none.
     */
    List<EssMember> getChildren();

    /**
     * Returns the count of children of this member.
     *
     * @return the number of children
     */
    int getChildCount();

    /**
     * How many members sit below this one, at every level.
     *
     * <p>Reported by the outline with the member itself, so it costs nothing - which makes it the way
     * to find out how big a walk would be before starting one. Zero for a leaf, and for a member read
     * from a source that didn't report it.
     *
     * @return the descendant count
     */
    long getDescendantsCount();

    /**
     * Gets the level-0 descendants of this member. There is currently no REST API to do this in one call, so this
     * method works recursively to fetch children of children and so on until the member has been explored.
     *
     * @return the level-0 descendants of this member, or a collection containing just this member if it has no
     * children.
     */
    List<EssMember> getLeafDescendants();


    /**
     * How this member's data is held, or {@link DataStorage#STORED} where the server said nothing -
     * it omits the field entirely for the default rather than naming it, so an absent value is
     * information and not a gap.
     *
     * @return the member's storage, never null
     */
    DataStorage getDataStorage();

    /**
     * This member's alias in every alias table the cube has, by table name.
     *
     * <p>Free: the server sends all of them with the member itself, so reading one table's alias for a
     * whole outline costs no requests beyond the ones that fetched the members. Established live -
     * Sample.Basic's {@code 100} comes back with six entries, one per table on that cube.
     *
     * <p>A table the member has no alias in is present with a null value rather than absent, so the
     * keys are the cube's alias tables whether or not this member uses them. {@link #getAlias(String)}
     * is the reading that does not care about the difference.
     *
     * @return table name to alias, empty if the server sent none
     */
    Map<String, String> getAliases();

    /**
     * This member's alias in one table, or null if it has none there.
     *
     * @param aliasTable the alias table to read; null or blank means {@code Default}
     * @return the alias, or null where the member has none in that table
     */
    default String getAlias(String aliasTable) {
        String table = aliasTable == null || aliasTable.isBlank() ? "Default" : aliasTable;
        String alias = getAliases().get(table);
        return alias == null || alias.isBlank() ? null : alias;
    }

    /**
     * Whether this dimension is dense or sparse.
     *
     * <p>Only a standard dimension has one. A member below a dimension, and an attribute dimension,
     * both answer {@link DimensionStorage#UNSPECIFIED}.
     *
     * @return the dimension's storage, never null
     */
    DimensionStorage getDimensionStorage();

    /**
     * The role this dimension plays, or {@link DimensionType#NONE} for an ordinary one and for any
     * member below a dimension.
     *
     * @return the dimension's type, never null
     */
    DimensionType getDimensionType();

    /**
     * Everything the server said about this member, as it said it.
     * <p>
     * The outline viewer returns a different set of fields per member - it omits anything at its
     * default, so a stored member with no formula and no UDAs is described in eight fields and an
     * accounts member with a formula and a time balance in sixteen - which makes an exhaustive model
     * of it a losing game. The typed accessors cover what is worth modelling; this is how a caller
     * reaches {@code consolidation}, {@code formula}, {@code twoPassCalc}, {@code uda} and the rest.
     *
     * @return the member's fields, or empty for a member not read from the outline viewer
     */
    Map<String, Object> getProperties();

    /**
     * How a member's data is held. The server writes these without separators - {@code DYNAMICCALC},
     * {@code SHAREDMEMBER} - and omits the field altogether for a plain stored member.
     */
    enum DataStorage {

        /** The default, and what an absent value means. */
        STORED("Stored"),

        /** Calculated on retrieval and not written to the cube. */
        DYNAMIC_CALC("Dynamic Calc"),

        /** Calculated on retrieval and then written. */
        DYNAMIC_CALC_AND_STORE("Dynamic Calc and Store"),

        /** A pointer to another member's data rather than data of its own. */
        SHARED("Shared Member"),

        /** Carries no data at all; it exists to group the members under it. */
        LABEL_ONLY("Label Only"),

        /** Stored, and never implicitly shared with a sole child. */
        NEVER_SHARE("Never Share"),

        /** A storage this version of the client doesn't name; the raw value is in {@link #getProperties()}. */
        UNKNOWN("Unknown");

        private final String label;

        DataStorage(String label) {
            this.label = label;
        }

        /**
         * The storage as Essbase's own tools write it, for a caller putting it on screen.
         *
         * @return the display name
         */
        public String getLabel() {
            return label;
        }

        /**
         * Reads the server's spelling, tolerating separators and case.
         *
         * @param text the value from the outline, or null for an absent one
         * @return the storage it names, {@link #STORED} for null, {@link #UNKNOWN} for anything unrecognized
         */
        public static DataStorage parse(String text) {
            if (text == null || text.isBlank()) {
                return STORED;
            }
            switch (text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "")) {
                case "STORED":
                case "STOREDATA":
                case "STOREDMEMBER":
                    return STORED;
                case "DYNAMICCALC":
                    return DYNAMIC_CALC;
                case "DYNAMICCALCANDSTORE":
                    return DYNAMIC_CALC_AND_STORE;
                case "SHARED":
                case "SHAREDMEMBER":
                    return SHARED;
                case "LABELONLY":
                    return LABEL_ONLY;
                case "NEVERSHARE":
                    return NEVER_SHARE;
                default:
                    return UNKNOWN;
            }
        }

    }

    /**
     * The role a dimension plays in the outline. The server names it only on the dimension's own row,
     * and only when it is something other than ordinary.
     */
    enum DimensionType {

        /** An ordinary dimension, and what an absent value means. */
        NONE(null),

        TIME("Time"),

        ACCOUNTS("Accounts"),

        ATTRIBUTE("Attribute"),

        ATTRIBUTE_CALC("Attribute Calc"),

        COUNTRY("Country"),

        CURRENCY_PARTITION("Currency Partition"),

        /** A type this version of the client doesn't name; the raw value is in {@link #getProperties()}. */
        UNKNOWN("Unknown");

        private final String label;

        DimensionType(String label) {
            this.label = label;
        }

        /**
         * The type as Essbase's own tools write it, or null for {@link #NONE}, which has nothing to say.
         *
         * @return the display name, or null
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param text the value from the outline, or null for an absent one
         * @return the type it names, {@link #NONE} for null, {@link #UNKNOWN} for anything unrecognized
         */
        public static DimensionType parse(String text) {
            if (text == null || text.isBlank()) {
                return NONE;
            }
            switch (text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "")) {
                case "NONE":
                    return NONE;
                case "TIME":
                    return TIME;
                case "ACCOUNTS":
                    return ACCOUNTS;
                case "ATTRIBUTE":
                    return ATTRIBUTE;
                case "ATTRIBUTECALC":
                case "ATTRIBUTECALCULATIONS":
                    return ATTRIBUTE_CALC;
                case "COUNTRY":
                    return COUNTRY;
                case "CURRENCYPARTITION":
                    return CURRENCY_PARTITION;
                default:
                    return UNKNOWN;
            }
        }

    }

    /**
     * Whether a dimension's blocks are dense or sparse.
     */
    enum DimensionStorage {

        DENSE,

        SPARSE,

        /** Not a standard dimension, or not a dimension at all. */
        UNSPECIFIED;

        public static DimensionStorage parse(String text) {
            if (text == null) {
                return UNSPECIFIED;
            }
            switch (text.toUpperCase(Locale.ROOT)) {
                case "DENSE":
                    return DENSE;
                case "SPARSE":
                    return SPARSE;
                default:
                    return UNSPECIFIED;
            }
        }

    }

}
