package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * What the REST API's pivot actions do.
 *
 * <p>They are a general "move this dimension there": name the cell holding a dimension, name the cell
 * it should go to, and the grid comes back rearranged. The source can be in the POV or on either axis,
 * and the destination decides where it lands - so a row dimension becoming a column dimension, which is
 * the pivot people mean, is one call.
 *
 * <p><strong>The coordinates are flat cell indices</strong>, counted across the grid row by row, and
 * that is the whole difficulty. They look like row/column pairs and are not. Probed as columns or as
 * pairs the actions appear far narrower than they are - refusing most requests, moving the wrong
 * dimension, or reporting that a pivot cannot be performed at all - and an earlier pass of this file
 * recorded exactly those conclusions before Oracle's own documented examples settled it: their
 * {@code pivotToPOV} sample sends {@code [8, 2]} for a four-column grid, which is only sensible read
 * flat.
 *
 * <p>Runs against {@code Sample.Basic}, whose default grid is small enough to name every cell.
 */
public class PivotOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        EssServer server = ConnectionUtils.server();
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    /**
     * The default grid, settled back to a known state.
     *
     * <pre>
     *   [ 0]         [ 1]Product   [ 2]Market   [ 3]Scenario     &lt;- POV
     *   [ 4]         [ 5]Measures  [ 6]         [ 7]             &lt;- top axis
     *   [ 8]Year     [ 9]105522.0  [10]         [11]             &lt;- row axis, then data
     * </pre>
     */
    private EssCubeView settled() {
        for (int attempt = 0; attempt < 10; attempt++) {
            cube.resetDefaultView();
            EssCubeView view = cube.openCubeView();
            if (axes(view).equals(List.of("Year=LEFT0", "Measures=TOP1"))) {
                return view;
            }
        }
        return null;
    }

    private static List<String> axes(EssCubeView view) {
        List<String> placements = new ArrayList<>();
        for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
            if (placement.region() != EssCubeView.DimensionPlacement.Region.POV) {
                placements.add(placement.name() + "=" + placement.region() + placement.index());
            }
        }
        return placements;
    }

    private static String regionOf(EssCubeView view, String dimension) {
        for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
            if (placement.name().equals(dimension)) {
                return placement.region() + (placement.index() >= 0 ? String.valueOf(placement.index()) : "");
            }
        }
        return "absent";
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void theBasisIsOneDimensionOnEachAxisAndThreeInThePov() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        assertEquals(List.of("Year=LEFT0", "Measures=TOP1"), axes(view));
        assertEquals("Product", view.getCell(0, 1));
        assertEquals("Measures", view.getCell(1, 1));
        assertEquals("Year", view.getCell(2, 0));
    }

    /** Flat, not a pair: the cell at row 2 column 0 of a four-column grid is index 8. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aCellIndexIsFlat() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        assertEquals(8, view.cellIndex(2, 0));
        assertEquals(2, view.cellIndex(0, 2));
    }

    // ----- one coordinate: to the front of the row axis --------------------------------------------

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aSingleCoordinateMovesAPovDimensionToTheRowAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(0, 1));

        assertEquals("LEFT0", regionOf(view, "Product"));
        assertEquals("LEFT1", regionOf(view, "Year"));
    }

    /** And from the column axis, which is a pivot the action was thought not to do at all. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aSingleCoordinateMovesAColumnDimensionToTheRowAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(1, 1));

        assertEquals("LEFT0", regionOf(view, "Measures"));
    }

    // ----- two coordinates: to wherever the destination names --------------------------------------

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aPovDimensionMovesToTheColumnAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(0, 1), view.cellIndex(1, 1));

        assertEquals("TOP2", regionOf(view, "Product"));
        assertEquals("LEFT0", regionOf(view, "Year"));
    }

    /** The one that matters: a column dimension becoming a row dimension, in a single call. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aColumnDimensionMovesToTheRowAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(1, 1), view.cellIndex(2, 0));

        assertEquals("LEFT0", regionOf(view, "Measures"));
        assertEquals("LEFT1", regionOf(view, "Year"));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aColumnDimensionMovesToThePov() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(1, 1), view.cellIndex(0, 1));

        assertEquals("POV", regionOf(view, "Measures"));
    }

    // ----- pivotToPov ------------------------------------------------------------------------------

    /**
     * Moves a row dimension into the POV, and fills the gap it leaves.
     *
     * <p>Staged with two dimensions on the row axis, because the axis cannot be emptied. Essbase brings
     * a POV dimension back out to keep it occupied, which is worth knowing: the answer has one more
     * change in it than was asked for.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToPovTakesARowDimensionAndBackfillsTheAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);
        view.pivot(view.cellIndex(0, 2));                    // Market joins the row axis
        assertEquals(List.of("Year=LEFT1", "Measures=TOP1", "Market=LEFT0"), axes(view));

        view.pivotToPov(view.cellIndex(2, 1), view.cellIndex(0, 2));

        assertEquals("POV", regionOf(view, "Year"));
        assertTrue("the row axis should not have been emptied",
                axes(view).stream().anyMatch(placement -> placement.endsWith("LEFT0")));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void theLastDimensionOnAnAxisCannotBePivotedAway() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        try {
            view.pivot(view.cellIndex(2, 0));
            fail("pivoting the only row dimension was expected to be refused");
        } catch (EssApiException refused) {
            assertTrue(refused.getMessage(), refused.getMessage().contains("Cannot pivot last row"));
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aDataCellIsNotSomethingToPivot() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        try {
            view.pivot(view.cellIndex(2, 1));
            fail("pivoting a data cell was expected to be refused");
        } catch (EssApiException refused) {
            assertTrue(refused.getMessage(),
                    refused.getMessage().contains("starting point cannot be determined"));
        }
    }

    // ----- one coordinate on the row axis, with more than one dimension there ----------------------

    /**
     * A row dimension with only a source named goes to the front of the column axis.
     *
     * <p>Completes the one-coordinate rule, which is a toggle rather than a cycle: a dimension in the
     * POV or on the column axis goes to the front of the row axis, and one already on the row axis goes
     * to the front of the column axis. The axis it leaves closes up behind it - Year moves from
     * {@code LEFT1} to {@code LEFT0} here without being asked to.
     *
     * <p>Staged with two dimensions on the row axis, because the last one cannot be pivoted away.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aSingleCoordinateMovesARowDimensionToTheColumnAxis() {
        EssCubeView view = settled();
        Assume.assumeNotNull(view);

        view.pivot(view.cellIndex(0, 2));   // Market joins the row axis, so there are two
        assertEquals(List.of("Year=LEFT1", "Measures=TOP1", "Market=LEFT0"), axes(view));

        view.pivot(view.cellIndex(2, 0));   // Market, the outer row dimension

        assertEquals("TOP1", regionOf(view, "Market"));
        assertEquals("the column axis should have made room", "TOP2", regionOf(view, "Measures"));
        assertEquals("the row axis should have closed up", "LEFT0", regionOf(view, "Year"));
    }

}
