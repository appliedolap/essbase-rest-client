package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
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
import static org.junit.Assert.assertTrue;

/**
 * Writes down what Essbase does for Keep Only and Remove Only, as a corpus for diffing a client-side
 * implementation against.
 *
 * <p>Runs against {@code Vision.Plan1} - the Planning application imported into Essbase from an EPM
 * Cloud artifact snapshot - because the point of the corpus is to compare the same outline in two
 * places, and that outline is the one the Dodeca PBCS Connector is pointed at. The test skips rather
 * than fails where the cube is absent: the cube exists on the local container by having been imported
 * there, and no build should demand that of a server it happens to be pointed at.
 *
 * <p>Data is beside the point. Keep Only and Remove Only act on the member tuples on the axes, so an
 * outline with no data loaded answers the same question a loaded one does, only with empty data cells.
 */
public class KeepRemoveOracleIT {

    /** Beside the test sources, so a fixture can be read and corrected by hand like any other. */
    private static final File FIXTURES = new File("src/test/resources/oracle");

    private GridOracle oracle;

    private EssCube cube;

    @Before
    public void setUp() {
        EssServer server = ConnectionUtils.server();
        try {
            cube = server.getApplication("Vision").getCube("Plan1");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Vision.Plan1 is not on this server", absent);
        }
        oracle = new GridOracle(cube);
    }

    /**
     * One dimension on the left, one on top, six in the POV.
     *
     * <pre>
     *                      Year  Period  HSP_Metric  Version  Currency  Account
     *                      Plan  Actual
     * Unspecified Entity
     * Management Rollup
     * No Entity
     * Total Entity
     * </pre>
     */
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

    /**
     * Two dimensions on the left, one on top, five in the POV - the shape where Keep Only stops being
     * obvious, because a selection in the outer column and one in the inner column are different
     * questions and a client-side implementation can easily answer both the same way.
     *
     * <pre>
     *                            Year  Period  HSP_Metric  Version  Currency
     *                                  Plan    Actual
     *
     * Management Rollup  ALL
     * Management Rollup  CF
     * Total Entity       ALL
     * Total Entity       CF
     * </pre>
     */
    private static String[][] twoOnTheLeft() {
        return new String[][] {
            {"",                  "",    "Year",   "Period", "HSP_Metric", "Version", "Currency"},
            {"",                  "",    "Plan",   "Actual", "",           "",        ""},
            {"Management Rollup", "ALL", "",       "",       "",           "",        ""},
            {"Management Rollup", "CF",  "",       "",       "",           "",        ""},
            {"Total Entity",      "ALL", "",       "",       "",           "",        ""},
            {"Total Entity",      "CF",  "",       "",       "",           "",        ""},
        };
    }

    private GridFixture capture(String name, String[][] sheet, int headerRows, int leftColumns,
            GridOracle.Action action, Range... ranges) throws Exception {
        GridFixture fixture = oracle.captureTo(FIXTURES, name, sheet, headerRows, leftColumns,
                action, List.of(ranges));
        System.out.println("=== " + name + " : " + fixture.operation().action()
                + " " + fixture.operation().selected());
        System.out.print(fixture.basis().render());
        System.out.println("    ->");
        System.out.print(fixture.result().render());
        return fixture;
    }

    /**
     * The outline the fixtures were captured against, in the form a client can load.
     *
     * <p>Not really a test - it writes a file - but it belongs in this class because the corpus is only
     * meaningful beside the outline it came from, and the two have to be regenerated together.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void writeTheOutlineTheFixturesWereCapturedAgainst() throws Exception {
        File outline = new File(FIXTURES, "vision-plan1.yaml");
        OutlineYaml.write(cube, outline);

        assertTrue(outline + " was not written", outline.length() > 0);
    }


    /**
     * Two dimensions on the left with three members in the inner one, so that selecting two of them
     * says something - in the two-by-two grid every pair of accounts is all of them.
     */
    private static String[][] twoOnTheLeftWide() {
        return new String[][] {
            {"",                  "",           "Year", "Period", "HSP_Metric", "Version", "Currency"},
            {"",                  "",           "Plan", "Actual", "",           "",        ""},
            {"Management Rollup", "ALL",        "",     "",       "",           "",        ""},
            {"Management Rollup", "CF",         "",     "",       "",           "",        ""},
            {"Management Rollup", "Statistics", "",     "",       "",           "",        ""},
            {"Total Entity",      "ALL",        "",     "",       "",           "",        ""},
            {"Total Entity",      "CF",         "",     "",       "",           "",        ""},
            {"Total Entity",      "Statistics", "",     "",       "",           "",        ""},
        };
    }

    /**
     * Two dimensions on top, one on the left - the mirror of {@link #twoOnTheLeft()}, to check that the
     * rules are the same rotated ninety degrees rather than merely similar.
     *
     * <pre>
     *                     Year  Period  HSP_Metric  Version  Currency
     *                     Plan  Plan    Actual      Actual
     *                     ALL   CF      ALL         CF
     * Management Rollup
     * Total Entity
     * </pre>
     */
    private static String[][] twoOnTop() {
        return new String[][] {
            {"",                  "Year", "Period", "HSP_Metric", "Version", "Currency"},
            {"",                  "Plan", "Plan",   "Actual",     "Actual",  ""},
            {"",                  "ALL",  "CF",     "ALL",        "CF",      ""},
            {"Management Rollup", "",     "",       "",           "",        ""},
            {"Total Entity",      "",     "",       "",           "",        ""},
        };
    }

    // ----- blank rows and columns -----------------------------------------------------------------
    //
    // Every basis above is a solid rectangle. A real template is not: people rule a blank row between
    // groups and a blank column between scenarios, and the question is whether the engine keeps them,
    // ignores them, or refuses the sheet - and whether a selection below one still means what it says.

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

    /** A blank column ruled between the two scenarios. */
    private static String[][] blankColumnInTheTopAxis() {
        return new String[][] {
            {"",                   "Year", "Period", "HSP_Metric", "Version", "Currency", "Account"},
            {"",                   "Plan", "",       "Actual",     "",        "",         ""},
            {"Unspecified Entity", "",     "",       "",           "",        "",         ""},
            {"Management Rollup",  "",     "",       "",           "",        "",         ""},
            {"Total Entity",       "",     "",       "",           "",        "",         ""},
        };
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyBelowABlankRow() throws Exception {
        capture("keep-only-below-a-blank-row", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(5, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAboveABlankRow() throws Exception {
        capture("keep-only-above-a-blank-row", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyBelowABlankRow() throws Exception {
        capture("remove-only-below-a-blank-row", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.REMOVE_ONLY, Range.cell(5, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyTheBlankRowItself() throws Exception {
        capture("keep-only-the-blank-row-itself", blankRowInTheLeftAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(4, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyBeyondABlankColumn() throws Exception {
        capture("keep-only-beyond-a-blank-column", blankColumnInTheTopAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(1, 3));
    }

    // ----- how selections combine -----------------------------------------------------------------

    /** Two members picked in the same left column: a union within that column, or something else? */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyTwoMembersInOneRowColumn() throws Exception {
        capture("keep-only-two-members-in-one-row-column", twoOnTheLeftWide(), 2, 2,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 1), Range.cell(3, 1));
    }

    /** The mirror of the intersecting keep only - do two removals in two columns also intersect? */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyAcrossBothRowColumns() throws Exception {
        capture("remove-only-across-both-row-columns", twoOnTheLeft(), 2, 2,
                GridOracle.Action.REMOVE_ONLY, Range.cell(2, 0), Range.cell(3, 1));
    }

    /**
     * Remove Only that would empty the left axis. Jaygrid refuses this outright; worth knowing whether
     * the engine does, or whether it answers with something.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyEveryRowMember() throws Exception {
        try {
            capture("remove-only-every-row-member", twoOnTheLeft(), 2, 2,
                    GridOracle.Action.REMOVE_ONLY, Range.cell(2, 0), Range.cell(4, 0));
        } catch (RuntimeException refused) {
            System.out.println("=== remove-only-every-row-member : the server refused it: "
                    + refused.getClass().getSimpleName() + ": " + refused.getMessage());
        }
    }

    // ----- two dimensions on top ------------------------------------------------------------------

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAnOuterColumnMember() throws Exception {
        capture("keep-only-outer-column-member", twoOnTop(), 3, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(1, 1));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAnInnerColumnMember() throws Exception {
        capture("keep-only-inner-column-member", twoOnTop(), 3, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 1));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAcrossBothColumnRows() throws Exception {
        capture("keep-only-across-both-column-rows", twoOnTop(), 3, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(1, 1), Range.cell(2, 2));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyAnInnerColumnMember() throws Exception {
        capture("remove-only-inner-column-member", twoOnTop(), 3, 1,
                GridOracle.Action.REMOVE_ONLY, Range.cell(2, 1));
    }

    // ----- one dimension on each axis -------------------------------------------------------------

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyOneRowMember() throws Exception {
        GridFixture fixture = capture("keep-only-one-row-member", oneOnEachAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 0));

        assertEquals(List.of("Unspecified Entity"), fixture.operation().selected());
        // One row survives, so the grid loses three.
        assertEquals(3, fixture.result().rows());
        assertEquals("Unspecified Entity", fixture.result().cell(2, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyContiguousRowMembers() throws Exception {
        GridFixture fixture = capture("keep-only-contiguous-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, new Range(3, 0, 4, 0));

        assertEquals(4, fixture.result().rows());
        assertEquals("Management Rollup", fixture.result().cell(2, 0));
        assertEquals("No Entity", fixture.result().cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyDisjointRowMembers() throws Exception {
        GridFixture fixture = capture("keep-only-disjoint-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 0), Range.cell(5, 0));

        assertEquals(4, fixture.result().rows());
        assertEquals("Unspecified Entity", fixture.result().cell(2, 0));
        assertEquals("Total Entity", fixture.result().cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyOneRowMember() throws Exception {
        GridFixture fixture = capture("remove-only-one-row-member", oneOnEachAxis(), 2, 1,
                GridOracle.Action.REMOVE_ONLY, Range.cell(3, 0));

        assertEquals(5, fixture.result().rows());
        assertTrue(fixture.result().render(), !fixture.result().render().contains("Management Rollup"));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyDisjointRowMembers() throws Exception {
        GridFixture fixture = capture("remove-only-disjoint-row-members", oneOnEachAxis(), 2, 1,
                GridOracle.Action.REMOVE_ONLY, Range.cell(2, 0), Range.cell(4, 0));

        assertEquals(4, fixture.result().rows());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyOneColumnMember() throws Exception {
        GridFixture fixture = capture("keep-only-one-column-member", oneOnEachAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(1, 1));

        assertEquals(List.of("Plan"), fixture.operation().selected());
        assertEquals("Plan", fixture.result().cell(1, 1));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyOneColumnMember() throws Exception {
        GridFixture fixture = capture("remove-only-one-column-member", oneOnEachAxis(), 2, 1,
                GridOracle.Action.REMOVE_ONLY, Range.cell(1, 1));

        assertEquals("Actual", fixture.result().cell(1, 1));
    }

    /**
     * A selection on a data cell does nothing at all - the grid comes back untouched, rather than
     * keeping the row, the column, or their intersection. Worth knowing: a client-side implementation
     * has to decide what to do here, and "nothing" is a defensible answer it is unlikely to reach on
     * its own.
     *
     * <p>Caveat worth keeping with the fixture: this cube has no data loaded, so the cell is both a
     * data position and empty, and nothing here distinguishes "Essbase ignores data cells" from
     * "Essbase ignores empty cells". Re-run it against a loaded cube to tell them apart.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyADataCellDoesNothing() throws Exception {
        GridFixture fixture = capture("keep-only-a-data-cell", oneOnEachAxis(), 2, 1,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 1));

        assertEquals(fixture.basis().render(), fixture.result().render());
    }

    // ----- two dimensions on the left -------------------------------------------------------------

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAnOuterRowMember() throws Exception {
        GridFixture fixture = capture("keep-only-outer-row-member", twoOnTheLeft(), 2, 2,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 0));

        assertEquals(List.of("Management Rollup"), fixture.operation().selected());
        // Both of that entity's account rows survive: the account column had nothing selected in it,
        // so it constrained nothing.
        assertEquals(4, fixture.result().rows());
        assertEquals("All Accounts", fixture.result().cell(2, 1));
        assertEquals("Cash Flow", fixture.result().cell(3, 1));
    }

    /**
     * The one worth having an oracle for. Selecting {@code ALL} in the inner column can reasonably mean
     * "keep the ALL rows under every entity" or "keep this one tuple", and nothing about the request
     * says which.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyAnInnerRowMember() throws Exception {
        GridFixture fixture = capture("keep-only-inner-row-member", twoOnTheLeft(), 2, 2,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 1));

        // The selection is read off the basis, which is in aliases - "ALL" went in, "All Accounts"
        // came back. The answer to the ambiguous case: it kept the member under every entity, not the
        // one tuple that was clicked.
        assertEquals(List.of("All Accounts"), fixture.operation().selected());
        assertEquals(4, fixture.result().rows());
        assertEquals("Management Rollup", fixture.result().cell(2, 0));
        assertEquals("Total Entity", fixture.result().cell(3, 0));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnlyAnInnerRowMember() throws Exception {
        GridFixture fixture = capture("remove-only-inner-row-member", twoOnTheLeft(), 2, 2,
                GridOracle.Action.REMOVE_ONLY, Range.cell(2, 1));

        // The mirror of keep only: the account column loses that member under every entity.
        assertEquals(4, fixture.result().rows());
        assertEquals("Cash Flow", fixture.result().cell(2, 1));
        assertEquals("Cash Flow", fixture.result().cell(3, 1));
    }

    /**
     * Two selections in two different left columns intersect - they do not union.
     *
     * <p>Keeping {@code Management Rollup} in the entity column and {@code Cash Flow} in the account
     * column leaves the single row that is both, not the two rows that were clicked. This is the rule
     * a client-side implementation is most likely to get wrong, because the selection arrives as a
     * list of cells and keeping the rows those cells are on is the obvious reading of it.
     *
     * <p>It falls out of the rule the single-column cases show: each selected column constrains its
     * own dimension to the members picked in it, and the grid keeps the tuples that satisfy every
     * constraint. A column with nothing selected in it constrains nothing.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnlyIntersectsAcrossRowColumnsRatherThanUnioning() throws Exception {
        GridFixture fixture = capture("keep-only-across-both-row-columns", twoOnTheLeft(), 2, 2,
                GridOracle.Action.KEEP_ONLY, Range.cell(2, 0), Range.cell(3, 1));

        assertEquals(fixture.result().render(), 3, fixture.result().rows());
        assertEquals("Management Rollup", fixture.result().cell(2, 0));
        assertEquals("Cash Flow", fixture.result().cell(2, 1));
    }

}
