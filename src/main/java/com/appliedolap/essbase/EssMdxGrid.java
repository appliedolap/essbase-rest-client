package com.appliedolap.essbase;

import java.util.List;

/**
 * An MDX query's result, with the names of the dimensions on each axis as well as the cells.
 *
 * <p>The grid starts with a header row for each dimension on the columns, holding its members over
 * the data, with an empty cell above each row member. Every row after that is a member of each
 * dimension on the rows, then the values. For {@code SELECT {[Jan], [Feb]} ON COLUMNS, {[100-10],
 * [100-20]} ON ROWS} against Sample.Basic:
 *
 * <pre>
 * ["",       "Jan",    "Feb"   ]   header row: Year, from getColumnDimensions()
 * ["100-10", "1812.0", "1754.0"]   data row: Product, from getRowDimensions(), then values
 * ["100-20", "200.0",  "206.0" ]
 * </pre>
 *
 * <p>Values are the server's text: a missing value is the string {@code #Missing}, and a dataless
 * query's values are empty strings.
 *
 * <p>When no rows survive - a {@code NON EMPTY} that drops them all, or a set that filters to nothing -
 * the grid is just its header rows, and the server leaves out the empty cells that would have sat
 * above the row members. The header cells then line up with the data columns from the first cell.
 *
 * @since 2.1.0
 */
public interface EssMdxGrid extends EssGrid {

    /**
     * The dimensions on the rows, in order. Each data row starts with a member of each.
     *
     * @return the dimension names; empty if nothing is on the rows
     */
    List<String> getRowDimensions();

    /**
     * The dimensions on the columns, in order. The grid starts with one header row for each.
     *
     * @return the dimension names; empty if nothing is on the columns
     */
    List<String> getColumnDimensions();

    /**
     * The dimensions on neither axis, which each contribute one member to every cell: the query's
     * slicer and the cube's defaults for whatever it leaves out.
     *
     * @return the dimension names
     */
    List<String> getPageDimensions();

    /**
     * The member each page dimension contributes, in the same order as {@link #getPageDimensions()}: the
     * slicer's member where the query names one, the dimension's top member otherwise, named the way
     * the query asked for members to be named.
     *
     * <p>Only servers that report the point of view say which members these are - 26.1 does, 21.7
     * reports just the dimensions - so each is null on one that doesn't.
     *
     * @return the members
     */
    List<String> getPageMembers();

    /**
     * How many rows at the top of the grid are headers rather than data: one for each dimension on the
     * columns.
     *
     * @return the number of header rows
     */
    default int getHeaderRows() {
        return Math.min(getColumnDimensions().size(), getRows());
    }

}
