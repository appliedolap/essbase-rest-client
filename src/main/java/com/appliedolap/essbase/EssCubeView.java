package com.appliedolap.essbase;

import java.util.List;

/**
 * A live, navigable ad hoc grid on a cube - the REST analog of the Java API's {@code IEssCubeView}.
 * Every operation re-executes the view against the server and replaces the grid held by this view
 * with the server's response.
 *
 * <p>This class describes actions and cells to the Essbase REST API and renders back whatever grid
 * the engine returns - it does not model what a given action "should" do to the grid, or compute a
 * request shaped to produce some intended result. The one exception is mechanical, not semantic:
 * {@link #zoomOut} and {@link #keepOnly} are sent as a "ranges" request (a single-cell range at the
 * given position) rather than "coordinates" like the other operations, because the server silently
 * no-ops "coordinates" for those two actions (200 OK, grid unchanged) - "ranges" is simply the only
 * field that reaches their implementation at all, not a different way of describing the same click.
 * Beyond that field substitution, no attempt is made to reverse, expand, or otherwise infer "the
 * range that will produce a particular outcome" - whatever the server does with the literal
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
 * <p>{@link #removeOnly}, {@link #pivot}, and {@link #pivotToPov(int, int)} are not verified: their
 * request wire shape appears correct (the server returns engine-level errors specific to the given
 * coordinates/ranges, rather than a malformed-request 400), but no verified-valid pair of
 * coordinates has been found yet - see the ignored tests in {@code EssCubeViewIT}. For removeOnly,
 * every coordinate/range tried (including removing only the aggregate/total row, in isolation)
 * fails with "This operation would generate a nonsensical report." Setting a *data* cell's value is
 * not implemented at all yet - the same dirty-cell/submit mechanism {@link #setMembers} uses was
 * tried against data positions too, but unlike member positions, the write never stuck even against
 * a confirmed leaf-level intersection.
 */
public interface EssCubeView extends EssGrid {

    /**
     * Zooms in on the member at the given position, using the server's default zoom-in preference.
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
     * Keeps only the member at the given position. See the class-level javadoc: this describes the
     * click as-is and returns whatever the server does with it, rather than computing a request
     * shaped to force a particular outcome.
     *
     * @param row the row of the member to keep
     * @param col the column of the member to keep
     */
    void keepOnly(int row, int col);

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
     * Swaps the positions of the members at the two given coordinates.
     *
     * @param fromRow the row of the first member
     * @param fromCol the column of the first member
     * @param toRow   the row of the second member
     * @param toCol   the column of the second member
     */
    void pivot(int fromRow, int fromCol, int toRow, int toCol);

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

}
