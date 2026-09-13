package com.appliedolap.essbase;

import java.util.List;

/**
 * The rows a data source returned, with the column names it described them by.
 */
public interface EssDataSourceQuery {

    /**
     * The column names, in order, as the data source defines them - not as they appear in the file.
     *
     * @return the column names
     */
    List<String> getColumns();

    /**
     * The rows, each a list of values matching {@link #getColumns()} in length and order.
     *
     * @return the rows
     */
    List<List<String>> getRows();

}
