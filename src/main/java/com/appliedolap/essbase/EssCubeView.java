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
 * <p>{@link #pivotToPov(int, int)} moves a dimension into the POV,
 * and needs a grid with two column dimensions to do anything at all - see the method for what is
 * established and what is still guesswork. Setting a *data* cell's value is
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
     * Moves a dimension to somewhere else in the grid.
     *
     * <p><strong>Both arguments are flat cell indices</strong>, counted across the grid row by row:
     * {@code row * getColumns() + column}, which {@link #cellIndex(int, int)} will work out. They are
     * not row/column pairs and not column numbers. Oracle's own examples are the authority here - the
     * documented {@code pivotToPOV} call sends {@code [8, 2]} for a four-column grid, meaning the cell
     * at row 2 column 0 and the one at row 0 column 2.
     *
     * <p>This is the general move: name any cell holding a dimension, name the cell where it should go,
     * and the grid comes back with it there. The source may be in the POV or on either axis, and the
     * destination decides which region it lands in.
     *
     * <pre>
     * // On Sample.Basic's default grid - Year down the side, Measures across the top, three in the POV:
     * view.pivot(view.cellIndex(0, 1), view.cellIndex(1, 1));   // Product from the POV onto the top axis
     * view.pivot(view.cellIndex(1, 1), view.cellIndex(0, 1));   // Measures off the top axis into the POV
     * view.pivot(view.cellIndex(1, 1), view.cellIndex(1, 0));   // Measures off the top onto the row axis
     * </pre>
     *
     * <p>It refuses rather than corrupting the grid: taking the last dimension off an axis answers
     * "Cannot pivot last row", and naming a data cell answers "Pivot starting point cannot be
     * determined".
     *
     * @param fromCell the flat index of a cell holding the dimension to move
     * @param toCell   the flat index of the cell to move it to
     */
    void pivot(int fromCell, int toCell);

    /**
     * Moves a dimension to the front of the row axis, which is what a pivot with no destination does.
     *
     * <p>The form Oracle's documentation shows, with a single coordinate. Works from the POV or from the
     * column axis.
     *
     * @param fromCell the flat index of a cell holding the dimension to move
     */
    void pivot(int fromCell);

    /**
     * Moves a dimension into the POV.
     *
     * <p>Flat cell indices again, like {@link #pivot(int, int)}. The destination names where in the POV
     * row it should sit.
     *
     * <p>The axis it leaves cannot be emptied: pivoting away the only dimension on the row axis answers
     * "Cannot pivot last row". Where the grid can afford it, Essbase fills the gap by bringing a POV
     * dimension back out - pivoting the second of two row dimensions away leaves the first in place and
     * pulls something from the POV in beside it.
     *
     * @param fromCell the flat index of a cell holding the dimension to move
     * @param toCell   the flat index of the POV cell to move it to
     */
    void pivotToPov(int fromCell, int toCell);

    /**
     * The flat index of a cell, which is how the pivot actions address one.
     *
     * @param row    the cell's row
     * @param column the cell's column
     * @return {@code row * getColumns() + column}
     */
    default int cellIndex(int row, int column) {
        return row * getColumns() + column;
    }

    /**
     * Rewrites the grid as a sheet of labels and asks the server to interpret it.
     *
     * <p><strong>This is how a grid is rearranged.</strong> Not the pivot actions - this. Write the
     * dimension names where you want them and send it; Essbase reads the sheet and answers with the
     * grid that layout describes, data and all. It is what Smart View does, and it is why every
     * operation carries the whole grid in the request rather than a handle to one.
     *
     * <pre>
     * before                            after
     * .      Product Market Scenario     .         Product Market Scenario
     * .      Measures                    .         Year
     * Year   105522                      Measures  105522
     *
     * view.setLayout(List.of(
     *         "", "Product", "Market", "Scenario",
     *         "", "Year",    "",       "",
     *         "Measures", "", "",      ""));
     * </pre>
     *
     * <p>That is the row-for-column rotation the {@link #pivot} action cannot do, and moving a POV
     * dimension to the top works the same way. Reordering within an axis is the same idea: write the
     * names in the order you want them.
     *
     * <p>The sheet is row-major and must have exactly {@link #getRows()} times {@link #getColumns()}
     * entries - the server indexes it by position and answers "Index n out of bounds" if it is short.
     * Beyond that the rules are the engine's: a layout it cannot read comes back as "Your report
     * heading cannot be interpreted", and a sheet whose shape implies more or fewer header rows than
     * the one sent may come back reinterpreted rather than refused.
     *
     * @param cells the grid's labels, row by row, with empty strings for the blanks
     */
    void setLayout(List<String> cells);

    /**
     * Rewrites the grid as a sheet of labels of any shape, saying where the data region begins.
     *
     * <p>The difference from {@link #setLayout(List)} is that this one can change the grid's
     * <em>structure</em> - how many dimensions sit on the left, on top, and in the POV - and not just
     * the order of what is already there. The single-argument form keeps the current grid's
     * member/data partition and only rewrites the labels, which is enough to rotate a dimension from
     * the left to the top but not to move a dimension between regions, because the server reads a
     * label that lands in what it still believes is a data position as a heading it cannot interpret,
     * and quietly answers with everything it could not place pushed into the POV.
     *
     * <p>A grid has three regions, and {@code headerRows} and {@code leftColumns} are exactly the two
     * numbers that divide them:
     *
     * <pre>
     *                 &lt;- leftColumns -&gt;
     *              +----------------+----------------------+
     *  headerRows  |     blank      |  POV row, then one    |
     *              |                |  row per top axis     |
     *              +----------------+----------------------+
     *              |  one column    |                       |
     *              |  per left axis |      data region      |
     *              +----------------+----------------------+
     * </pre>
     *
     * <p>Every cell above {@code headerRows} or left of {@code leftColumns} is a label. Of the rest, a
     * cell carries data where both its column and its row do: a column does where the last header row -
     * the innermost top axis, which names one member per data column - names something there, and a row
     * does where the left axis names something on it. Leave the data cells empty; the server fills them.
     *
     * <p>Deciding it per row and column rather than as a rectangle is what lets a sheet have a blank row
     * or column ruled through it, the way a real template does.
     *
     * <p>The POV does not have to be there: a grid with every dimension on an axis simply has no POV
     * row, and {@code headerRows} is then just the number of top axes.
     *
     * @param sheet       the grid's labels, row by row, with empty strings for blanks and data cells
     * @param headerRows  how many rows precede the data region - the POV row, if there is one, plus
     *                    one row per top/column axis
     * @param leftColumns how many columns the left/row axes occupy
     */
    void setLayout(String[][] sheet, int headerRows, int leftColumns);

    /**
     * Where a dimension sits in the grid: in the POV, on a top/column axis, or on a left/row axis.
     *
     * @param name   the dimension's name
     * @param region which of the grid's three regions it occupies
     * @param index  the grid row it occupies for {@link Region#TOP}, the grid column for
     *               {@link Region#LEFT}, and {@code -1} for {@link Region#POV}
     */
    record DimensionPlacement(String name, Region region, int index) {

        /**
         * A grid's three regions. {@code TOP} and {@code LEFT} are the column and row axes - a top
         * dimension lays its members across a grid <em>row</em>, and a left dimension lays them down a
         * grid <em>column</em>, which is why naming them after the axis they form rather than the
         * direction they run reads backwards as often as not.
         */
        public enum Region {
            POV, TOP, LEFT
        }

    }

    /**
     * Says where every dimension currently sits, as the server reports it rather than as the sheet of
     * labels implies.
     *
     * <p>This is the authoritative read of a grid's structure and the one worth asserting against: a
     * layout the server reinterpreted looks perfectly reasonable in the returned labels and is only
     * obvious here, as a dimension that came back in the POV when it was asked for on an axis.
     *
     * <p>The count is the cube's dimension count, always - every dimension is in exactly one region.
     *
     * @return one placement per dimension, in the server's order
     */
    List<DimensionPlacement> getPlacements();

    /**
     * The alias table the grid's labels are in.
     *
     * <p>Worth asking, because a grid takes member names on the way in and hands back aliases: send a
     * layout saying {@code ALL} and the answer says {@code All Accounts}. Anything comparing what it
     * asked for against what it got needs to know which table did that.
     *
     * @return the alias table's name, such as {@code Default}
     */
    String getAliasTable();

    /**
     * The alias table name that means "label members with their own names".
     *
     * <p>A sentinel rather than a table: the grid API has no flag for turning aliases off, and a name
     * the cube does not have is an error, so this is the only way to ask for member names. Exactly this
     * spelling - {@code None}, {@code NONE} and {@code nOnE} are all rejected like any other unknown
     * table, which is itself the evidence that the lowercase one is deliberate rather than a cube that
     * happens to have a table by that name.
     */
    String NO_ALIASES = "none";

    /**
     * Sets the alias table this grid's members are labelled from.
     *
     * <p>Per request, not a stored preference: the alias table rides along on every grid action, so two
     * grids open on the same connection can be reading different ones. That is unlike the rest of the
     * grid preferences, which Essbase keeps per session and therefore shares between every grid a user
     * has open.
     *
     * <p>Null means the server's default, which is the {@code Default} table. A name the cube does not
     * have is <em>not</em> ignored - the next action fails - so a name should come from
     * {@link EssCube#getAliasTables()} or be {@link #NO_ALIASES}. {@link #getAliasTable()} reports what
     * the last response was labelled from.
     *
     * @param aliasTable the alias table to label members from, or null for the server's default
     */
    void setAliasTable(String aliasTable);

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
     * @param navigateWithoutData    whether zooming and pivoting bring data back, or only rearrange the
     *                               members. The wire has this the other way up, as {@code navigate},
     *                               which is true when data <em>is</em> wanted - established live,
     *                               where setting it false returned a grid of empty cells
     * @param useBothNamesAndAliases whether each row dimension gets two columns, its members' names in
     *                               one and their aliases in the next. The wire calls this
     *                               {@code includeDescriptionLabel}, which says nothing about what it
     *                               does; established live, where turning it on widened a grid from
     *                               four columns to five and put {@code 100} beside {@code Colas}.
     *                               Row dimensions only - the column axis is unaffected
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
            boolean removeUnselectedGroup,
            boolean useBothNamesAndAliases,
            boolean navigateWithoutData) {

        /**
         * The form with {@code useBothNamesAndAliases} but not {@code navigateWithoutData}.
         *
         * @param useBothNamesAndAliases whether each row dimension gets a name column and an alias one
         */
        public GridPreferences(Indentation indentation, boolean suppressMissingRows,
                boolean suppressZeroRows, boolean suppressUnderscoreRows, boolean repeatMemberLabels,
                ZoomInPreference zoomInPreference, boolean includeSelection, boolean withinSelectedGroup,
                boolean removeUnselectedGroup, boolean useBothNamesAndAliases) {
            this(indentation, suppressMissingRows, suppressZeroRows, suppressUnderscoreRows,
                    repeatMemberLabels, zoomInPreference, includeSelection, withinSelectedGroup,
                    removeUnselectedGroup, useBothNamesAndAliases, false);
        }

        /**
         * The form without {@code useBothNamesAndAliases}, which defaults to off.
         *
         * <p>Kept so that every caller written before that existed still compiles, and because off is
         * what they meant: a grid that showed one column per row dimension carries on doing so.
         */
        public GridPreferences(Indentation indentation, boolean suppressMissingRows,
                boolean suppressZeroRows, boolean suppressUnderscoreRows, boolean repeatMemberLabels,
                ZoomInPreference zoomInPreference, boolean includeSelection, boolean withinSelectedGroup,
                boolean removeUnselectedGroup) {
            this(indentation, suppressMissingRows, suppressZeroRows, suppressUnderscoreRows,
                    repeatMemberLabels, zoomInPreference, includeSelection, withinSelectedGroup,
                    removeUnselectedGroup, false, false);
        }
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
