package com.appliedolap.essbase;

import java.util.Collections;
import java.util.List;

/**
 * The detail rows behind a drilled cell.
 *
 * <p>Modelled as columns plus rows of strings because that is what the server sends and no more: the
 * response is a JSON array whose first element is the column datatypes, whose second is the column
 * names, and whose remainder is one array per record. Every value arrives as a string, including the
 * numeric ones - a {@code DOUBLE} column yields {@code "24.0"} - so the datatype is reported
 * alongside rather than used to coerce, which would be this class guessing at a format the caller
 * knows better than it does.
 */
public final class EssDrillthroughResult {

    private final List<Column> columns;

    private final List<List<String>> rows;

    public EssDrillthroughResult(List<Column> columns, List<List<String>> rows) {
        this.columns = Collections.unmodifiableList(columns);
        this.rows = Collections.unmodifiableList(rows);
    }

    /** The columns, in the order the server returned them. */
    public List<Column> getColumns() {
        return columns;
    }

    /** One list of values per record, aligned with {@link #getColumns()}. */
    public List<List<String>> getRows() {
        return rows;
    }

    public int getRowCount() {
        return rows.size();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public String toString() {
        return rows.size() + " row(s) over " + columns.size() + " column(s)";
    }

    /** One column of a drill-through result: what it is called and what the server says it holds. */
    public static final class Column {

        private final String name;

        private final String dataType;

        public Column(String name, String dataType) {
            this.name = name;
            this.dataType = dataType;
        }

        public String getName() {
            return name;
        }

        /** As the server reports it - {@code STRING}, {@code DOUBLE}, {@code DATE} and so on. */
        public String getDataType() {
            return dataType;
        }

        @Override
        public String toString() {
            return name + " (" + dataType + ")";
        }

    }

}
