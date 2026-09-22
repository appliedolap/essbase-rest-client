package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssCubeView.Range;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * What Essbase does for Zoom In and Zoom Out, as a corpus for diffing Jaygrid against.
 *
 * <p>Runs against {@code Vision.Plan1}, like {@link KeepRemoveOracleIT}, and skips where that cube is
 * absent. Zoom answers depend far more on the grid preferences than keep only and remove only do -
 * {@code zoomInPreference}, {@code includeSelection} and {@code withinSelectedGroup} each change them -
 * so every fixture records the preferences in force and a corpus worth having has to vary them.
 */
public class ZoomOracleIT {

    private static final File FIXTURES = new File("src/test/resources/oracle");

    private GridOracle oracle;

    @Before
    public void setUp() {
        EssServer server = ConnectionUtils.server();
        EssCube cube = null;
        try {
            cube = server.getApplication("Vision").getCube("Plan1");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Vision.Plan1 is not on this server", absent);
        }
        oracle = new GridOracle(cube);
    }

    /** A blank row ruled between the second and third entity. */
    private static String[][] blankRowInTheLeftAxis() {
        return new String[][] {
            {"",                   "Year", "Period", "HSP_Metric", "Version", "Currency", "Account"},
            {"",                   "Plan", "Actual", "",           "",        "",         ""},
            {"Unspecified Entity", "",     "",       "",           "",        "",         ""},
            {"Management Rollup",  "",     "",       "",           "",        "",         ""},
            {"",                   "",     "",       "",           "",        "",         ""},
            {"No Entity",          "",     "",       "",           "",        "",         ""},
            {"Total Entity",       "",     "",       "",           "",        "",         ""},
        };
    }

    private static String[][] blankColumnInTheTopAxis() {
        return new String[][] {
            {"",                   "Year", "Period", "HSP_Metric", "Version", "Currency", "Account"},
            {"",                   "Plan", "",       "Actual",     "",        "",         ""},
            {"Unspecified Entity", "",     "",       "",           "",        "",         ""},
            {"Management Rollup",  "",     "",       "",           "",        "",         ""},
            {"Total Entity",       "",     "",       "",           "",        "",         ""},
        };
    }

    private GridFixture capture(String name, String[][] sheet, int headerRows, int leftColumns,
            GridOracle.Action action, Range... ranges) throws Exception {
        return capture(name, GridOracle.DEFAULTS, sheet, headerRows, leftColumns, action, ranges);
    }

    private GridFixture capture(String name, EssCubeView.GridPreferences preferences, String[][] sheet,
            int headerRows, int leftColumns, GridOracle.Action action, Range... ranges) throws Exception {
        GridFixture fixture = oracle.capture(name, sheet, headerRows, leftColumns, action,
                List.of(ranges), preferences);
        fixture.writeTo(new File(FIXTURES, name + ".json"));
        System.out.println("=== " + name + " : " + fixture.operation().action()
                + " " + fixture.operation().selected());
        System.out.print(fixture.basis().render());
        System.out.println("    ->");
        System.out.print(fixture.result().render());
        return fixture;
    }

    /** Four entities down the left, two scenarios across the top. */
    private static String[][] oneOnEachAxis() {
        return new String[][] {
            {"",                   "Year", "Period", "HSP_Metric", "Version", "Currency", "Account"},
            {"",                   "Plan", "Actual", "",           "",        "",         ""},
            {"Unspecified Entity", "",     "",       "",           "",        "",         ""},
            {"Management Rollup",  "",     "",       "",           "",        "",         ""},
            {"No Entity",          "",     "",       "",           "",        "",         ""},
            {"Total Entity",       "",     "",       "",           "",        "",         ""},
        };
    }

    // ----- more than one cell ---------------------------------------------------------------------
    //
    // Dodeca sends every cell the user selected, and the connector passes them all on. What zooming
    // two members at once is supposed to mean - both expanded in place, or only one of them - is not
    // something to guess at.

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnTwoRowMembers() throws Exception {
        capture("zoom-in-two-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, Range.cell(3, 0), Range.cell(5, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAContiguousRangeOfRowMembers() throws Exception {
        capture("zoom-in-contiguous-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, new Range(3, 0, 4, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnOneRowMemberForComparison() throws Exception {
        capture("zoom-in-one-row-member", oneOnEachAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, Range.cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomOutOnTwoRowMembers() throws Exception {
        capture("zoom-out-two-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.ZOOM_OUT, Range.cell(3, 0), Range.cell(5, 0));
    }

    /** One cell on each axis - the ranged operations refuse a selection spanning two regions. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInAcrossTwoRegions() throws Exception {
        try {
            capture("zoom-in-across-two-regions", oneOnEachAxis(), 2, 1,
                    GridOracle.Action.ZOOM_IN, Range.cell(3, 0), Range.cell(1, 1));
        } catch (RuntimeException refused) {
            System.out.println("=== zoom-in-across-two-regions : the server refused it: "
                    + refused.getClass().getSimpleName() + ": " + refused.getMessage());
        }
    }

    /** Two dimensions on the left, so that a member spans a group of rows rather than one. */
    private static String[][] twoOnTheLeft() {
        return new String[][] {
            {"",                  "",    "Year", "Period", "HSP_Metric", "Version", "Currency"},
            {"",                  "",    "Plan", "Actual", "",           "",        ""},
            {"Total Entity",      "ALL", "",     "",       "",           "",        ""},
            {"Total Entity",      "CF",  "",     "",       "",           "",        ""},
            {"Management Rollup", "ALL", "",     "",       "",           "",        ""},
            {"Management Rollup", "CF",  "",     "",       "",           "",        ""},
        };
    }

    // ----- the ad hoc options -----------------------------------------------------------------------
    //
    // Everything above was captured under one set of preferences. These are the ones that change a
    // zoom's answer, each varied on its own against that same baseline so a fixture's difference from
    // the default one is attributable to the option and nothing else. They are stored per session
    // rather than per grid, so every capture states them rather than inheriting them.

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInToAllLevels() throws Exception {
        capture("zoom-in-all-levels", GridOracle.zoomingBy(EssCubeView.ZoomInPreference.ALL_LEVELS),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_IN, Range.cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInToTheBottomLevel() throws Exception {
        capture("zoom-in-bottom-level", GridOracle.zoomingBy(EssCubeView.ZoomInPreference.BOTTOM_LEVEL),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_IN, Range.cell(3, 0));
    }

    /** Without it, the member that was expanded is replaced by its children rather than kept. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInWithoutIncludeSelection() throws Exception {
        capture("zoom-in-without-include-selection", GridOracle.with(false, false, false),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_IN, Range.cell(3, 0));
    }

    /** With it, the rows that were not selected go away entirely. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInRemovingTheUnselectedGroup() throws Exception {
        capture("zoom-in-remove-unselected-group", GridOracle.with(true, false, true),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_IN, Range.cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomOutWithoutIncludeSelection() throws Exception {
        capture("zoom-out-without-include-selection", GridOracle.with(false, false, false),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_OUT, Range.cell(3, 0));
    }

    // ----- within the selected group ----------------------------------------------------------------
    //
    // Only says anything where a member spans a group of rows, which needs two dimensions on the left.

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAnOuterMemberAcrossItsGroup() throws Exception {
        capture("zoom-in-outer-across-the-group", GridOracle.with(true, false, false),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAnOuterMemberWithinItsGroup() throws Exception {
        capture("zoom-in-outer-within-the-group", GridOracle.with(true, true, false),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAnInnerMemberAcrossItsGroup() throws Exception {
        capture("zoom-in-inner-across-the-group", GridOracle.with(true, false, false),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 1));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnAnInnerMemberWithinItsGroup() throws Exception {
        capture("zoom-in-inner-within-the-group", GridOracle.with(true, true, false),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 1));
    }

    /** Without repeated labels the outer column names each member once, over a group of blank cells. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInWithoutRepeatingMemberLabels() throws Exception {
        capture("zoom-in-without-repeating-member-labels", GridOracle.repeatingMemberLabels(false),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 1));
    }

    // ----- removing the unselected group ------------------------------------------------------------
    //
    // One fixture with a single dimension on the left is not enough to implement from: with two, an
    // inner member expands under several groups, and what "unselected" means then is a real question.

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeUnselectedGroupZoomingAnInnerMember() throws Exception {
        capture("zoom-in-remove-unselected-inner", GridOracle.with(true, false, true),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 1));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeUnselectedGroupZoomingAnOuterMember() throws Exception {
        capture("zoom-in-remove-unselected-outer", GridOracle.with(true, false, true),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 0));
    }

    /** Both group options at once - does "within" narrow what "remove unselected" then keeps? */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeUnselectedGroupWithinTheSelectedGroup() throws Exception {
        capture("zoom-in-remove-unselected-within-group", GridOracle.with(true, true, true),
                twoOnTheLeft(), 2, 2, GridOracle.Action.ZOOM_IN, Range.cell(2, 1));
    }

    /** And whether the option means anything to a zoom out at all. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeUnselectedGroupZoomingOut() throws Exception {
        capture("zoom-out-remove-unselected-group", GridOracle.with(true, false, true),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_OUT, Range.cell(3, 0));
    }

    /** Two cells selected, so "unselected" has more than one thing to spare. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeUnselectedGroupWithTwoCellsSelected() throws Exception {
        capture("zoom-in-remove-unselected-two-cells", GridOracle.with(true, false, true),
                oneOnEachAxis(), 2, 1, GridOracle.Action.ZOOM_IN, Range.cell(3, 0), Range.cell(5, 0));
    }

    // ----- selections that name no member ---------------------------------------------------------
    //
    // Keep Only and Remove Only do nothing with one. Whether Zoom does too is a separate question, and
    // Jaygrid reaches the grid by a different path for zoom - a single source cell rather than a list
    // of ranges - so it is worth asking rather than assuming.

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnABlankRowDoesNothing() throws Exception {
        GridFixture fixture = capture("zoom-in-on-a-blank-row", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, Range.cell(4, 0));

        assertEquals(fixture.basis().render(), fixture.result().render());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomOutOnABlankRowDoesNothing() throws Exception {
        GridFixture fixture = capture("zoom-out-on-a-blank-row", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.ZOOM_OUT, Range.cell(4, 0));

        assertEquals(fixture.basis().render(), fixture.result().render());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnABlankColumnDoesNothing() throws Exception {
        GridFixture fixture = capture("zoom-in-on-a-blank-column", blankColumnInTheTopAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, Range.cell(1, 2));

        assertEquals(fixture.basis().render(), fixture.result().render());
    }

    /** A data cell names no member either, and Keep Only ignores one. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInOnADataCellDoesNothing() throws Exception {
        GridFixture fixture = capture("zoom-in-on-a-data-cell", blankColumnInTheTopAxis(), 2, 1,
                GridOracle.Action.ZOOM_IN, Range.cell(2, 1));

        assertEquals(fixture.basis().render(), fixture.result().render());
    }

}
