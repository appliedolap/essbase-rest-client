package com.appliedolap.essbase;

import java.util.List;

/**
 * A live, navigable ad hoc grid on a cube - the REST analog of the Java API's {@code IEssCubeView}.
 * Every operation re-executes the view against the server and replaces the grid held by this view
 * with the server's response.
 *
 * <p>This class describes actions and cells to the Essbase REST API and renders back whatever grid
 * the engine returns - it does not model what a given action "should" do to the grid, or compute a
 * request shaped to produce some intended result. The exceptions are all mechanical, not semantic -
 * cases where the wire format itself has more than one field capable of naming a cell, and only one
 * of them actually reaches the engine's implementation for a given kind of cell or action:
 *
 * <ul>
 * <li>{@link #zoomOut}, {@link #keepOnly} and {@link #removeOnly} are sent as a "ranges" request
 * rather than "coordinates" like the other operations, because the server silently no-ops
 * "coordinates" for those actions (200 OK, grid unchanged) - "ranges" is simply the only field that
 * reaches their implementation at all.
 * <li>A range is <strong>{@code [startRow, startColumn, rowCount, columnCount]}</strong>, not two
 * corners. Established live: on a grid of Qtr1..Qtr4, Year, a {@code keepOnly} at Qtr2 (row 3) sent as
 * {@code [3, 0, 3, 0]} keeps Qtr2, Qtr3 and Qtr4 - three rows starting at row 3 - while
 * {@code [3, 0, 1, 1]} keeps Qtr2 alone.
 * <li>{@link #zoomIn} tries "ranges" first and falls back to "coordinates" only if the server
 * rejects that shape outright (an engine-level error, not a 200). This isn't a guess based on
 * inspecting the cell: "coordinates" turns out not to do a literal (row, col) grid lookup at all for
 * a cell already on a real axis (as opposed to a POV placeholder dimension shown as a header but not
 * yet on any axis) - it addresses POV placeholder dimensions by column, order-insensitively
 * (coordinates {@code [0,1]}, {@code [1,0]}, {@code [0,2]}, and {@code [2,0]} all landed on whichever
 * POV dimension's column matched the nonzero value, never on the literal on-axis cell). Confirmed
 * live: zooming in on "Year" (on the row axis at column 0) via "coordinates" silently expanded
 * "Market" (an unrelated POV dimension) instead - not an error, just the wrong dimension, which is
 * exactly why trying "ranges" first and reacting to an actual rejection is the only sound way to
 * pick between the two: "coordinates" can't be trusted to fail loudly when it's wrong.
 * </ul>
 *
 * Beyond those field-selection adjustments, no attempt is made to reverse, expand, or otherwise infer
 * "the range that will produce a particular outcome" - whatever the server does with the literal
 * single-cell description is authoritative. In practice this means, for instance, that
 * {@link #zoomOut} reliably collapses a cleanly-targeted dimension back to its total, but on a member
 * row that the engine doesn't accept for that request shape (or once more than one dimension is
 * zoomed in at once), it may do less than you'd expect, more than you'd expect, or return an
 * engine-level error - that's the engine's answer to the given description, not a bug in this layer.
 *
 * <p>{@link #getCell(int, int)}, {@link #getCellType(int, int)}, {@link #setMembers}, {@link #zoomIn},
 * {@link #zoomOut}, {@link #keepOnly}, and {@link #refresh} are verified against a live server (see
 * {@code EssCubeViewIT}) in at least their most direct case - the grid content is asserted to actually
 * change, not just that the call doesn't throw.
 *
 * <p>{@link #removeOnly} is verified live, single cell and rectangle. It previously was not, and was
 * documented here as unusable because every attempt answered "This operation would generate a
 * nonsensical report" - that was the range format, not the action: a single cell sent as
 * {@code [row, col, row, col]} describes a block of {@code row} by {@code col} cells, which for most
 * positions really is nonsensical. It is ordinary once the range says what it means.
 *
 * <p>{@link #pivot} is verified live and documented on the method: it takes a source column and a
 * destination column, not a pair of cells as the old four-argument form assumed.
 *
 * <p>{@link #pivotToPov(int, int)} is still not understood. What is known: one coordinate answers
 * "Pivot ending point cannot be determined", so it wants two; two coordinates naming a row-axis column
 * answer "Cannot pivot last column"; two naming a column-side dimension answer "Your pivot operation
 * cannot be performed on this report". No pair has yet moved a row-axis dimension out to the POV,
 * which is what the name suggests it is for. Setting a *data* cell's value is
 * not implemented at all yet - the same dirty-cell/submit mechanism {@link #setMembers} uses was
 * tried against data positions too, but unlike member positions, the write never stuck even against
 * a confirmed leaf-level intersection.
 */
public interface EssCubeView extends EssGrid {

    /**
     * Zooms in on the member at the given position, using the server's default zoom-in preference.
     * See the class-level javadoc: this tries "ranges" first and only falls back to "coordinates" if
     * the server itself rejects that shape - not a guess based on inspecting the cell, but a reaction
     * to what the server says about the same literal click either way.
     *
     * @param row the row of the member to zoom in on
     * @param col the column of the member to zoom in on
     */
    void zoomIn(int row, int col);

    /**
     * Zooms out from the member at the given position. See the class-level javadoc: this describes
     * the click as-is and returns whatever the server does with it, rather than computing a request
     * shaped to force a particular collapse.
     *
     * @param row the row of the member to zoom out from
     * @param col the column of the member to zoom out from
     */
    void zoomOut(int row, int col);

    /**
     * Zooms in on the members in several rectangles at once.
     *
     * <p>One request, so every member expands against the same grid. Zooming them one at a time would
     * work from a grid that the previous zoom had already reshaped, and the positions would no longer
     * mean what they meant when they were picked.
     *
     * @param ranges the rectangles to zoom in on, in any order
     */
    void zoomIn(List<Range> ranges);

    /**
     * Zooms out of the members in several rectangles at once.
     *
     * @param ranges the rectangles to zoom out of, in any order
     */
    void zoomOut(List<Range> ranges);

    /**
     * Keeps only the member at the given position. See the class-level javadoc: this describes the
     * click as-is and returns whatever the server does with it, rather than computing a request
     * shaped to force a particular outcome.
     *
     * @param row the row of the member to keep
     * @param col the column of the member to keep
     */
    void keepOnly(int row, int col);

    /**
     * Keeps only the members in a rectangle of cells, dropping their siblings.
     *
     * <p>The corners may be given in either order. What reaches the server is the rectangle they
     * describe - a range there is a start plus a row and column count, not a pair of corners, which is
     * a distinction worth knowing about only if you are reading the wire format.
     *
     * @param fromRow one corner's row
     * @param fromCol one corner's column
     * @param toRow the other corner's row
     * @param toCol the other corner's column
     */
    void keepOnly(int fromRow, int fromCol, int toRow, int toCol);

    /**
     * Keeps only the members in several rectangles at once, dropping their siblings.
     *
     * <p>The rectangles need not touch, and this is the point of the method: keeping the first and third
     * of four quarters is not a rectangle, and sending two operations would drop everything outside the
     * first before the second was asked. Verified live - {@code [[2,0,1,1],[4,0,1,1]]} against
     * Qtr1..Qtr4, Year keeps Qtr1 and Qtr3.
     *
     * @param ranges the rectangles to keep, in any order
     */
    void keepOnly(List<Range> ranges);

    /**
     * Removes the member at the given position, keeping the rest.
     *
     * <p><b>Not currently verified to work</b> - every coordinate/range tried against a live server
     * returns "This operation would generate a nonsensical report." See the class-level javadoc.
     *
     * @param row the row of the member to remove
     * @param col the column of the member to remove
     */
    void removeOnly(int row, int col);

    /**
     * Removes the members in a rectangle of cells, keeping their siblings.
     *
     * @param fromRow one corner's row
     * @param fromCol one corner's column
     * @param toRow the other corner's row
     * @param toCol the other corner's column
     */
    void removeOnly(int fromRow, int fromCol, int toRow, int toCol);

    /**
     * Removes the members in several rectangles at once, keeping their siblings.
     *
     * @param ranges the rectangles to remove, in any order
     */
    void removeOnly(List<Range> ranges);

    /**
     * A rectangle of cells, given as two corners in either order.
     *
     * <p>What reaches the server is a start plus a row and column count, which is how a range is spelled
     * on the wire; corners are how anyone holding a selection thinks about one.
     */
    final class Range {

        private final int fromRow;

        private final int fromColumn;

        private final int toRow;

        private final int toColumn;

        public Range(int fromRow, int fromColumn, int toRow, int toColumn) {
            this.fromRow = fromRow;
            this.fromColumn = fromColumn;
            this.toRow = toRow;
            this.toColumn = toColumn;
        }

        /** A single cell. */
        public static Range cell(int row, int column) {
            return new Range(row, column, row, column);
        }

        public int getStartRow() {
            return Math.min(fromRow, toRow);
        }

        public int getStartColumn() {
            return Math.min(fromColumn, toColumn);
        }

        public int getRowCount() {
            return Math.abs(toRow - fromRow) + 1;
        }

        public int getColumnCount() {
            return Math.abs(toColumn - fromColumn) + 1;
        }

        /** How many cells the rectangle covers. */
        public int size() {
            return getRowCount() * getColumnCount();
        }

        @Override
        public String toString() {
            return "[" + getStartRow() + "," + getStartColumn()
                    + " " + getRowCount() + "x" + getColumnCount() + "]";
        }

    }

    /**
     * Moves a dimension to a different place in the grid - onto the row axis, or elsewhere in the
     * header.
     *
     * <p>Worked out against a live server, because nothing documents it. A grid's dimensions all live
     * at some column: the row-axis dimensions occupy the leftmost columns, and the ones on the column
     * side appear as labels further right. Pivoting is moving a dimension from one of those columns to
     * another.
     *
     * <pre>
     *   col      0        1         2       3
     *   row 0            Product   Market  Scenario    &lt;- POV, if there is one
     *   row 1            Measures                      &lt;- column axis, one row per dimension
     *   row 2   Year     105522                        &lt;- row axis
     *
     *   pivot(2, 0)  Market to the front of the row axis
     *   pivot(3, 2)  Scenario ahead of Market, still on the column side
     * </pre>
     *
     * <p><strong>Do not count rows from the top.</strong> The POV occupies row 0 only while there is a
     * POV: empty it and the row disappears, the column-axis rows shift up, and the grid loses a row.
     * Pivoting the third of three POV dimensions onto the rows in the grid above turns it from 3x4 into
     * 2x5 with Measures - the column axis - now at row 0. Anything reading "row 0" as "the POV" will
     * quietly read the column axis instead. The header is as many rows as there are column dimensions,
     * plus one if any dimension is in the POV, and as many columns as there are row dimensions.
     *
     * <p>The dimension is inserted <em>before</em> whatever occupies {@code toColumn}, so moving a
     * dimension to the column just after itself does nothing.
     *
     * <p>{@code fromColumn} names a dimension on the column side, and matters when there is more than
     * one of them; with a single column-side dimension left the server moves it whatever column is
     * named. Moving that last one onto the row axis is refused with "Cannot pivot last column" - a grid
     * keeps something on each axis - and so is naming a row-axis column. A move that would change
     * nothing is refused with "Your pivot operation has no effect on this report".
     *
     * <p>Going the other way - a row-axis dimension out to the POV - is not this call, and is not yet
     * understood; see {@link #pivotToPov}.
     *
     * @param fromColumn the column of the dimension to move
     * @param toColumn where to put it; the dimension lands before whatever is there
     */
    void pivot(int fromColumn, int toColumn);

    /**
     * Moves a dimension to the front of the row axis, which is what a pivot with no destination does.
     *
     * @param fromColumn the column of the dimension to move
     */
    void pivot(int fromColumn);

    /**
     * Pins the member at the given position into the POV, removing its dimension from whichever axis
     * it currently occupies.
     *
     * @param row the row of the member to pin to the POV
     * @param col the column of the member to pin to the POV
     */
    void pivotToPov(int row, int col);

    /**
     * Re-executes the view as-is, picking up any data changes made since it was last retrieved.
     */
    void refresh();

    /**
     * A coarse classification of a cell as either a data position or a member/label. This does not
     * attempt full fidelity with the classic Java API's {@code IEssGridView} cell-type bitmask (NULL,
     * MEMBER, DATA, TEXT, NO_ACCESS, BLANK, MISSING, ZERO, DOUBLE, ERROR, SMARTLIST, DATE) - full
     * fidelity isn't needed. {@link #getCellType(int, int)} only distinguishes whether a cell is a
     * data position, whether or not it currently holds a value (missing/blank data cells are still
     * {@code DATA}, not {@code MEMBER}).
     */
    enum CellType {
        MEMBER, DATA
    }

    /**
     * Classifies the cell at the given position as {@link CellType#DATA} or {@link CellType#MEMBER}.
     * Based on whether the cell's (undocumented by Oracle) {@code types} code is exactly {@code "2"} -
     * verified, against a live server, to mark the data-cell position consistently whether or not the
     * cell currently holds a real value (confirmed both with real data present, and later with every
     * data cell blank/missing after a database reload). Deliberately compares by equality rather than
     * treating {@code types} as a bitmask: code {@code "7"} (blank filler cells) also has bit 2 set,
     * but is not a data position, so a bitwise check misclassifies it.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the cell's type
     */
    CellType getCellType(int row, int col);

    /**
     * A member to place at a specific grid position, replacing whichever member currently occupies
     * that spot on its row or column axis tier.
     *
     * @param row        the row to place the member at
     * @param col        the column to place the member at
     * @param memberName the member to place there
     */
    record MemberPlacement(int row, int col, String memberName) {
    }

    /**
     * Replaces the members at the given positions, in one request. Verified live against a real
     * server: this retargets an existing member-position on an existing axis tier to a different
     * member (e.g. changing which Product shows in a given row) - it does not grow the grid to add
     * rows or columns beyond what's already there, and the given member name must be valid for that
     * position's dimension.
     *
     * @param placements the members to place, and where
     */
    void setMembers(List<MemberPlacement> placements);

    /**
     * How indented member rows/columns are, mirroring the classic ad hoc "Indentation" setting.
     * Verified live: changing this changes the leading-space indentation on subsequent retrieves.
     */
    enum Indentation {
        NONE, SUBITEMS, TOTALS
    }

    /**
     * The server's zoom-in behavior when a plain {@link #zoomIn} is used (no explicit member set).
     * Mirrors a subset of the classic ad hoc "Zoom In" preference - the REST wire format
     * ({@code ancestor}/{@code mode} on the preferences resource) only expresses these three; it has
     * no equivalent of the classic API's Sibling Level, Same Level, Same Generation, or Formulas
     * options. Verified live: {@code ALL_LEVELS} pulls in every descendant level (not just direct
     * children) on a subsequent zoom, and the effect is visible immediately on the next zoom-in.
     */
    enum ZoomInPreference {
        NEXT_LEVEL, ALL_LEVELS, BOTTOM_LEVEL
    }

    /**
     * Ad hoc display/navigation preferences, mirroring the classic "Essbase Options" dialog to the
     * extent the REST wire format supports. These are stored server-side per login session (the wire
     * resource is {@code /preferences/grid}, with no application/database/cube in its path) - they
     * apply to every subsequent grid operation for this connection, not just this one view, and they
     * persist until changed again or the session ends.
     *
     * <p>Verified live against a real server: {@link #indentation}, {@link #zoomInPreference}, and
     * {@link #repeatMemberLabels} each visibly change the next retrieve/zoom's output. {@link
     * #suppressMissingRows}, {@link #suppressZeroRows}, {@link #suppressUnderscoreRows}, {@link
     * #includeSelection}, {@link #withinSelectedGroup}, and {@link #removeUnselectedGroup} are wired
     * through (the wire fields exist and accept the value) but not yet confirmed to have a visible
     * effect - suppression in particular needs a grid with genuine missing/zero data cells to test
     * against, not just member structure, and wasn't confirmed either way.
     *
     * @param indentation            member row/column indentation style
     * @param suppressMissingRows    whether to suppress rows whose data cells are all #Missing
     * @param suppressZeroRows       whether to suppress rows whose data cells are all zero
     * @param suppressUnderscoreRows whether to suppress rows whose member name starts with "_"
     * @param repeatMemberLabels     whether a member label repeats down every row of its group, or
     *                               only appears once at the top of the group
     * @param zoomInPreference       the server's default zoom-in depth for a plain {@link #zoomIn}
     * @param includeSelection       whether zooming in keeps the zoomed-on member alongside its new
     *                               children, rather than replacing it with just the children
     * @param withinSelectedGroup    whether a zoom stays scoped to the selected group rather than
     *                               applying to every member at that level
     * @param removeUnselectedGroup  whether zooming in removes sibling groups that weren't selected
     */
    record GridPreferences(
            Indentation indentation,
            boolean suppressMissingRows,
            boolean suppressZeroRows,
            boolean suppressUnderscoreRows,
            boolean repeatMemberLabels,
            ZoomInPreference zoomInPreference,
            boolean includeSelection,
            boolean withinSelectedGroup,
            boolean removeUnselectedGroup) {
    }

    /**
     * Reads the current session's ad hoc display/navigation preferences.
     */
    GridPreferences getPreferences();

    /**
     * Replaces the current session's ad hoc display/navigation preferences. See {@link
     * GridPreferences}: this takes effect for every subsequent grid operation on this connection, not
     * just this view, and persists until changed again.
     *
     * @param preferences the preferences to apply
     */
    void setPreferences(GridPreferences preferences);

}
