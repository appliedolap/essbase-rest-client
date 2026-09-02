package com.appliedolap.essbase;

/**
 * A live, navigable ad hoc grid on a cube - the REST analog of the Java API's {@code IEssCubeView}.
 * Every operation re-executes the view against the server and replaces the grid held by this view
 * with the server's response.
 *
 * <p>{@link #getCell(int, int)}, {@link #getCellType(int, int)}, and the navigation operations
 * ({@link #zoomIn}, {@link #zoomOut}, {@link #keepOnly}, {@link #removeOnly}, {@link #refresh}) are
 * verified against a live server (see {@code EssCubeViewIT}). {@link #pivot} and
 * {@link #pivotToPov(int, int)} are not: their request wire shape appears correct (the server returns
 * engine-level errors specific to the given coordinates, rather than a malformed-request 400), but no
 * verified-valid pair of coordinates has been found yet - see the ignored pivot tests in
 * {@code EssCubeViewIT}. Setting a cell's value is not implemented at all yet.
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
     * Zooms out from the member at the given position.
     *
     * @param row the row of the member to zoom out from
     * @param col the column of the member to zoom out from
     */
    void zoomOut(int row, int col);

    /**
     * Keeps only the member at the given position (and its siblings, if applicable), removing the rest.
     *
     * @param row the row of the member to keep
     * @param col the column of the member to keep
     */
    void keepOnly(int row, int col);

    /**
     * Removes the member at the given position, keeping the rest.
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

}
