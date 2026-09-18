package scratch;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EssCubeViewIT extends AbstractEssbaseServerTest {

    private static final Logger logger = LoggerFactory.getLogger(EssCubeViewIT.class);

    @Before
    public void resetDefaultView() {
        sampleBasic().resetDefaultView();
    }

    private EssCube sampleBasic() {
        return server.getApplication("Sample").getCube("Basic");
    }

    private void logGrid(EssCubeView view) {
        logger.info("Grid is {} rows x {} columns", view.getRows(), view.getColumns());
        for (int row = 0; row < view.getRows(); row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < view.getColumns(); col++) {
                line.append(String.format("%20s", view.getCell(row, col)));
            }
            logger.info(line.toString());
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void openDefaultView() {
        EssCubeView view = sampleBasic().openCubeView();
        assertNotNull(view);
        logGrid(view);
    }

    // openCubeView() is NOT idempotent: it returns whatever ad hoc view state Essbase already has
    // active server-side for this user+cube, which persists across brand-new sessions (confirmed:
    // separate test runs, each a fresh WebLogic session, still saw a prior run's zoom/pivot state).
    // It is NOT a named/saved layout though - "Default" is not a real layout name, so this is most
    // likely genuine Essbase-kernel-level cube view state, not anything reachable via the layout API.
    // Don't assume openCubeView() gives a pristine starting point - every test/consumer needs to
    // drive the view to its own known starting shape explicitly.
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void openingNonexistentLayoutThrows() {
        try {
            sampleBasic().openCubeView("Default");
            fail("Expected an EssApiException - there is no real saved layout named \"Default\"");
        } catch (EssApiException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("No layout exists with this name"));
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void cellTypeDistinguishesDataFromMembers() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(0, 1);
        logGrid(view);

        // rows 2+ are the zoomed-in Product members: col0=member, col1="Year" member, col2=data, col3=blank
        for (int row = 2; row < view.getRows(); row++) {
            for (int col = 0; col < view.getColumns(); col++) {
                EssCubeView.CellType expected = col == 2 ? EssCubeView.CellType.DATA : EssCubeView.CellType.MEMBER;
                assertEquals("row " + row + ", col " + col, expected, view.getCellType(row, col));
            }
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomIn() {
        EssCubeView view = sampleBasic().openCubeView();
        logger.info("Before zoom in:");
        logGrid(view);

        view.zoomIn(0, 1);
        logger.info("After zoom in on (0, 1):");
        logGrid(view);
    }

    // Reproduces a real bug: zooming in on "Year" (already on the row axis at (2, 0) in the pristine
    // grid) via "coordinates" silently expanded "Market" (a POV dimension) instead - not an error,
    // just the wrong dimension. "coordinates" turns out not to do a literal (row, col) grid lookup at
    // all for members already on an axis; it's addressing POV placeholder dimensions by column,
    // order-insensitively (confirmed: coordinates [0,1], [1,0], [0,2], and [2,0] all landed on a POV
    // dimension keyed off whichever value wasn't 0, never on "Year"). zoomIn now tries "ranges" first
    // (which does address the literal cell) and only falls back to "coordinates" if the server itself
    // rejects that shape - see EssCubeViewImpl.zoomIn.
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAnAlreadyOnAxisDimension() {
        EssCubeView view = sampleBasic().openCubeView();
        logger.info("Before zoom in:");
        logGrid(view);
        assertEquals("Year", view.getCell(2, 0));

        view.zoomIn(2, 0);
        logger.info("After zoom in on Year:");
        logGrid(view);
        assertEquals("     Qtr1", view.getCell(2, 0));
        assertEquals("     Qtr2", view.getCell(3, 0));
        assertEquals("     Qtr3", view.getCell(4, 0));
        assertEquals("     Qtr4", view.getCell(5, 0));
        assertEquals("Year", view.getCell(6, 0));

        // The same bug recurs one level deeper: zooming into "Qtr1" (now itself on-axis, at the
        // same column Year occupied) also silently mistargeted a POV dimension via "coordinates".
        view.zoomIn(2, 0);
        logger.info("After zoom in on Qtr1:");
        logGrid(view);
        assertEquals("          Jan", view.getCell(2, 0));
        assertEquals("          Feb", view.getCell(3, 0));
        assertEquals("          Mar", view.getCell(4, 0));
        assertEquals("     Qtr1", view.getCell(5, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInThenZoomOut() {
        EssCubeView view = sampleBasic().openCubeView();
        int rowsBeforeZoom = view.getRows();

        view.zoomIn(0, 1);
        logger.info("After zoom in:");
        logGrid(view);
        assertTrue("zoom in should add rows for the Product members", view.getRows() > rowsBeforeZoom);

        // Zoom out targets the Product aggregate/total row (the last row after zooming in), not the
        // original pre-zoom coordinate - that position no longer holds the "Product" member once its
        // children have been zoomed into the grid.
        view.zoomOut(view.getRows() - 1, 0);
        logger.info("After zoom out:");
        logGrid(view);
        assertEquals("zoom out should collapse back to the pre-zoom row count", rowsBeforeZoom, view.getRows());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnly() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(0, 1);
        logger.info("After zoom in:");
        logGrid(view);

        // Row 2 is the first Product member (Colas) once zoomed in. keepOnly describes the click as
        // a literal single-cell range and shows back whatever the server does with it - here, that's
        // keeping this row and the next (Colas and Root Beer), not just the one row clicked.
        view.keepOnly(2, 0);
        logger.info("After keep only on (2, 0):");
        logGrid(view);
        assertEquals("Colas", view.getCell(2, 0));
        assertEquals("Root Beer", view.getCell(3, 0));
        assertEquals(4, view.getRows());
    }

    // Not currently verified to work: every coordinate/range tried against a live server - including
    // removing only the aggregate/total row, in isolation - fails with "This operation would generate
    // a nonsensical report." See the class-level javadoc on EssCubeView.
    @Ignore
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnly() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(0, 1);
        logger.info("After zoom in:");
        logGrid(view);

        view.removeOnly(2, 0);
        logger.info("After remove only on (2, 0):");
        logGrid(view);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void setMembersReplacesProductRows() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(0, 1);
        logger.info("Before setMembers:");
        logGrid(view);

        view.setMembers(List.of(
                new EssCubeView.MemberPlacement(2, 0, "Root Beer"),
                new EssCubeView.MemberPlacement(3, 0, "Diet Drinks")));
        logger.info("After setMembers:");
        logGrid(view);

        assertEquals("Root Beer", view.getCell(2, 0).trim());
        assertEquals("Diet Drinks", view.getCell(3, 0).trim());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void refresh() {
        EssCubeView view = sampleBasic().openCubeView();
        view.refresh();
        logGrid(view);
    }

    // Preferences are stored server-side per login session (wire resource "/preferences/grid", no
    // app/db/cube in its path) - not reset by resetDefaultView(), and they'd otherwise leak into
    // whichever test runs next. Restore a known-default set after every test in this class.
    @org.junit.After
    public void restoreDefaultPreferences() {
        sampleBasic().openCubeView().setPreferences(new EssCubeView.GridPreferences(
                EssCubeView.Indentation.SUBITEMS, false, false, false, true,
                EssCubeView.ZoomInPreference.NEXT_LEVEL, true, false, false));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void preferencesIndentationAffectsRetrieve() {
        EssCubeView view = sampleBasic().openCubeView();
        view.setPreferences(new EssCubeView.GridPreferences(
                EssCubeView.Indentation.NONE, false, false, false, true,
                EssCubeView.ZoomInPreference.NEXT_LEVEL, true, false, false));
        assertEquals(EssCubeView.Indentation.NONE, view.getPreferences().indentation());

        view.zoomIn(0, 1);
        logger.info("After zoom in with indentation=NONE:");
        logGrid(view);
        assertEquals("Colas", view.getCell(2, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void preferencesZoomInPreferenceAffectsRetrieve() {
        EssCubeView view = sampleBasic().openCubeView();
        view.setPreferences(new EssCubeView.GridPreferences(
                EssCubeView.Indentation.SUBITEMS, false, false, false, true,
                EssCubeView.ZoomInPreference.ALL_LEVELS, true, false, false));
        assertEquals(EssCubeView.ZoomInPreference.ALL_LEVELS, view.getPreferences().zoomInPreference());

        // With a plain zoomIn (no explicit member set) and ALL_LEVELS in effect, Product should pull
        // in every descendant level, not just its direct children - so Colas's own children (e.g.
        // "Cola") should already be present without a second zoom.
        view.zoomIn(0, 1);
        logger.info("After zoom in with zoomInPreference=ALL_LEVELS:");
        logGrid(view);
        assertTrue("expected more than just Product's direct children", view.getRows() > 8);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void preferencesRepeatMemberLabelsAffectsRetrieve() {
        EssCubeView view = sampleBasic().openCubeView();
        view.setPreferences(new EssCubeView.GridPreferences(
                EssCubeView.Indentation.SUBITEMS, false, false, false, false,
                EssCubeView.ZoomInPreference.NEXT_LEVEL, true, false, false));
        assertEquals(false, view.getPreferences().repeatMemberLabels());

        view.zoomIn(0, 1);
        view.zoomIn(0, 2);
        logger.info("After nested zoom with repeatMemberLabels=false:");
        logGrid(view);
        assertEquals("     East", view.getCell(2, 0));
        assertEquals("", view.getCell(3, 0));
    }

    /**
     * Pivot moves a dimension from one column of the grid to another.
     *
     * <p>The pristine Sample.Basic grid is
     * <pre>
     *   col      0        1         2       3
     *   row 0            Product   Market  Scenario    &lt;- POV
     *   row 1            Measures                      &lt;- column axis
     *   row 2   Year     105522                        &lt;- row axis
     * </pre>
     * so column 2 is Market, and pivoting it to column 0 puts it at the front of the row axis.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotMovesAColumnSideDimensionOntoTheRowAxis() {
        EssCubeView view = sampleBasic().openCubeView();
        assertEquals("Market", view.getCell(0, 2).trim());
        assertEquals("Year", view.getCell(2, 0).trim());

        view.pivot(2, 0);
        logGrid(view);

        assertEquals("Market is now the first row dimension", "Market", view.getCell(2, 0).trim());
        assertEquals("Year has moved along to make room", "Year", view.getCell(2, 1).trim());
    }

    /** One coordinate means the front of the row axis, which is what the two-argument form does with 0. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotWithNoDestinationGoesToTheFrontOfTheRowAxis() {
        EssCubeView view = sampleBasic().openCubeView();
        view.pivot(2);

        assertEquals("Market", view.getCell(2, 0).trim());
        assertEquals("Year", view.getCell(2, 1).trim());
    }

    /** A destination further right keeps the dimension on the column side, just reordered. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotCanReorderTheColumnSide() {
        EssCubeView view = sampleBasic().openCubeView();
        assertEquals("Market", view.getCell(0, 2).trim());
        assertEquals("Scenario", view.getCell(0, 3).trim());

        view.pivot(3, 2);
        logGrid(view);

        assertEquals("Scenario has moved ahead of Market", "Scenario", view.getCell(0, 2).trim());
        assertEquals("Market", view.getCell(0, 3).trim());
    }

    /**
     * A grid has to keep a dimension on each side, and the server says so rather than emptying one.
     * The message names the column rather than the axis, which is worth knowing when reading it.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotWillNotEmptyTheColumnSide() {
        EssCubeView view = sampleBasic().openCubeView();
        // Pull dimensions onto the rows until the server stops us, rather than assuming how many there
        // are - Sample.Basic has four on the column side, three in the POV plus Measures on the column
        // axis, and counting them here would be encoding a fact about the cube into a test about a rule.
        EssApiException refused = null;
        for (int attempt = 0; attempt < 10 && refused == null; attempt++) {
            try {
                view.pivot(2, 0);
            } catch (EssApiException e) {
                refused = e;
            }
        }
        logGrid(view);
        assertNotNull("the server should eventually refuse to empty the column side", refused);
        assertTrue(refused.getMessage(), refused.getMessage().contains("Cannot pivot last column"));
    }

    /**
     * The POV row exists only while something is in the POV.
     *
     * <p>Emptying it does not leave a blank row - the grid loses the row, the column axis moves up to
     * row 0, and every row index below it shifts. A reader that treats row 0 as the POV will take the
     * column axis for it.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void thePovRowDisappearsWhenThePovIsEmptied() {
        EssCubeView view = sampleBasic().openCubeView();
        assertEquals(3, view.getRows());
        assertEquals("Product", view.getCell(0, 1).trim());   // POV
        assertEquals("Measures", view.getCell(1, 1).trim());  // column axis, below it

        // Pull all three POV dimensions onto the rows.
        view.pivot(2, 0);
        view.pivot(2, 0);
        view.pivot(2, 0);
        logGrid(view);

        assertEquals("the POV row is gone, not blank", 2, view.getRows());
        assertEquals("the column axis has moved up into row 0",
                "Measures", view.getCell(0, view.getColumns() - 1).trim());
    }

    /**
     * pivotToPov exchanges the POV dimension with the first one on the column axis.
     *
     * <p>It needs two dimensions on the column axis - with one, taking it into the POV would empty the
     * axis and every coordinate is refused. {@code pivot(2, 1)} is how the second one gets there.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToPovExchangesThePovWithTheColumnAxis() {
        EssCubeView view = twoOnEachAxis();
        assertEquals("Scenario", view.getCell(0, 2).trim());   // POV
        assertEquals("Market", view.getCell(1, 2).trim());     // first column-axis dimension

        view.pivotToPov(3, 0);
        logGrid(view);

        assertEquals("Market has taken the POV", "Market", view.getCell(0, 2).trim());
        assertEquals("Scenario has gone to the column axis", "Scenario", view.getCell(1, 2).trim());
        assertEquals("Measures is undisturbed below it", "Measures", view.getCell(2, 2).trim());
    }

    /** With one dimension on the column axis there is nothing to exchange, and the server says so. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToPovNeedsASecondColumnDimension() {
        EssCubeView view = sampleBasic().openCubeView();
        // Pristine Sample.Basic: Measures alone on the column axis.
        try {
            view.pivotToPov(3, 0);
            fail("expected a refusal with only one column dimension");
        } catch (EssApiException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("cannot be performed"));
        }
    }

    /**
     * Market onto the column axis, then Product onto the rows: two dimensions on each axis, one in the
     * POV. Retried because resetDefaultView is not always settled by the time the next call lands.
     */
    private EssCubeView twoOnEachAxis() {
        for (int attempt = 0; ; attempt++) {
            try {
                sampleBasic().resetDefaultView();
                EssCubeView view = sampleBasic().openCubeView();
                view.pivot(2, 1);
                view.pivot(1, 0);
                assertEquals("Scenario", view.getCell(0, 2).trim());
                return view;
            } catch (Exception e) {
                if (attempt >= 6) {
                    throw new AssertionError("could not stage a two-on-each-axis grid", e);
                }
            }
        }
    }

    /**
     * The row axis is a sink: nothing pivots a dimension off it.
     *
     * <p>Staged with an empty POV so that pivot has no POV dimension to reach for instead, and with
     * three dimensions on the rows so that losing one would be legal. Asking to move one of them to the
     * column side still does not.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void nothingPivotsADimensionOffTheRowAxis() {
        EssCubeView view = emptyPov();
        assertEquals("Scenario", view.getCell(2, 0).trim());
        assertEquals("Product", view.getCell(2, 1).trim());
        assertEquals("Year", view.getCell(2, 2).trim());

        // Year is at column 2; column 3 is the column side. Asking to send it there does nothing.
        try {
            view.pivot(2, 3);
            fail("expected a refusal - the row axis cannot be pivoted out of");
        } catch (EssApiException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("no effect"));
        }

        // And a from that names a row column moves a column-side dimension instead of that one.
        EssCubeView other = emptyPov();
        other.pivot(1, 0);
        logGrid(other);
        assertEquals("Market, off the column side - not Product, which column 1 names",
                "Market", other.getCell(1, 0).trim());
    }

    /** Three dimensions on the rows, two on the column side, nothing in the POV. */
    private EssCubeView emptyPov() {
        for (int attempt = 0; ; attempt++) {
            try {
                sampleBasic().resetDefaultView();
                EssCubeView view = sampleBasic().openCubeView();
                view.pivot(2, 1);
                view.pivot(1, 0);
                view.pivot(0, 0);
                assertEquals(3, view.getRows());
                return view;
            } catch (Exception e) {
                if (attempt >= 6) {
                    throw new AssertionError("could not stage a grid with an empty POV", e);
                }
            }
        }
    }

    /**
     * Rewriting the sheet rotates a row dimension onto the columns - the pivot the actions cannot do.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void setLayoutRotatesRowsOntoColumns() {
        EssCubeView view = sampleBasic().openCubeView();
        assertEquals("Measures", view.getCell(1, 1).trim());   // on the columns
        assertEquals("Year", view.getCell(2, 0).trim());       // on the rows

        view.setLayout(List.of(
                "", "Product",  "Market", "Scenario",
                "", "Year",     "",       "",
                "Measures", "", "",       ""));
        logGrid(view);

        assertEquals("Year has taken the column axis", "Year", view.getCell(1, 1).trim());
        assertEquals("Measures has taken the row axis", "Measures", view.getCell(2, 0).trim());
        assertEquals("and the data came with it", "105522.0", view.getCell(2, 1).trim());
    }

    /** A POV dimension can be written straight onto the columns, which no pivot action does either. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void setLayoutMovesAPovDimensionToTheColumns() {
        EssCubeView view = sampleBasic().openCubeView();

        view.setLayout(List.of(
                "", "Product", "Year", "Scenario",
                "", "Market",  "",     "",
                "Measures", "", "",    ""));
        logGrid(view);

        assertEquals("Market", view.getCell(1, 1).trim());
        assertEquals("Measures", view.getCell(2, 0).trim());
        assertEquals("Year is in the POV now", "Year", view.getCell(0, 2).trim());
    }

    /** The server indexes the sheet by position, so a short one is a bounds error rather than a hint. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void setLayoutNeedsExactlyOneCellPerPosition() {
        EssCubeView view = sampleBasic().openCubeView();
        try {
            view.setLayout(List.of("", "Product", "Market"));
            fail("expected a complaint about the cell count");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("exactly 12 cells"));
        }
    }

    /** Moving a dimension to where it already is is refused rather than quietly doing nothing. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToItsOwnColumnIsRefused() {
        EssCubeView view = sampleBasic().openCubeView();
        try {
            view.pivot(2, 2);
            fail("expected the server to refuse a pivot with no effect");
        } catch (EssApiException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("no effect"));
        }
    }

    // Still unresolved: "Your pivot operation cannot be performed on this report." Pinning one
    // member (Colas) of a still-multi-member Product axis to the POV may not be a legal pivotToPov
    // target as-is - needs real ad hoc grid domain input, same as the earlier pivot() investigation.
    @Ignore
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToPov() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(0, 1);
        logger.info("Before pivot to POV:");
        logGrid(view);

        // pin "Colas" (2,0) to the POV, dropping Product from the row axis
        view.pivotToPov(2, 0);
        logger.info("After pivot to POV:");
        logGrid(view);
    }

}
