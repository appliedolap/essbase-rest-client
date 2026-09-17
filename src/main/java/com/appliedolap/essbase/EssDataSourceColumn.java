package com.appliedolap.essbase;

/**
 * One column of a data source, as the server describes it.
 *
 * <p>The index is the column's position in the underlying source and is <em>zero</em> based, which the
 * specification does not say and the server does not check: a one-based index is accepted when the
 * data source is created and fails much later, at query time, with "Index 10 out of bounds for
 * length 10".
 */
public final class EssDataSourceColumn {

    private final String name;

    private final String dataType;

    private final Integer index;

    public EssDataSourceColumn(String name, String dataType, Integer index) {
        this.name = name;
        this.dataType = dataType;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    /** {@code STRING}, {@code DOUBLE}, {@code DATE}, {@code TIMESTAMP} or {@code LONG}. */
    public String getDataType() {
        return dataType;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return name + " (" + dataType + ")";
    }

}
