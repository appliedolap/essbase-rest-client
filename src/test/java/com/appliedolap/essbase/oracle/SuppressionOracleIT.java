package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Suppressing rows and columns that have no data.
 *
 * <p>Built on Sample.Basic's Caffeine Free Cola, which is sold in East, West and Central but not in
 * South - so a grid of the four regions has exactly one empty row (or column) to remove, and the
 * remaining three prove that suppression did not simply empty the grid.
 *
 * <p>Suppression only applies where the server is choosing what to return. A {@link
 * EssCubeView#setLayout} states the grid literally and is honoured literally, empty rows and all, so
 * these build a layout and then <em>zoom</em> to produce the rows under test.
 */
@Category(DestructiveIntegrationTest.class)
public class SuppressionOracleIT {

    private EssCube cube;

    private EssCubeView.GridPreferences saved;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
        saved = cube.openCubeView().getPreferences();
    }

    @After
    public void tearDown() {
        if (saved != null) {
            cube.openCubeView().setPreferences(saved);
        }
    }

    /**
     * The preference survives a write and a read.
     *
     * <p>Looks like a test of nothing, and is the one that matters: the wire carries row suppression
     * under two names, {@code rowSuppression} and the misspelled {@code rowSupression}, and this
     * library wrote only the misspelled one. The server stored it in a field it never reads, so the
     * setting round-tripped as false and suppression silently did nothing at all. A grid assertion
     * alone would have said "suppression does not work on this server" rather than "we are setting the
     * wrong field".
     */
    @Test
    public void theSuppressionPreferencesRoundTrip() {
        apply(true);

        EssCubeView.GridPreferences readBack = cube.openCubeView().getPreferences();
        assertTrue("suppressMissingRows did not round-trip", readBack.suppressMissingRows());
        assertTrue("suppressMissingColumns did not round-trip", readBack.suppressMissingColumns());
    }

    @Test
    public void suppressingMissingRowsRemovesTheEmptyRow() {
        apply(false);
        assertEquals(List.of("East", "West", "South", "Central"), regions(zoomedRows()));

        apply(true);
        assertEquals(List.of("East", "West", "Central"), regions(zoomedRows()));
    }

    /**
     * Column suppression is accepted, stored, and then ignored.
     *
     * <p>Asserted as it actually behaves rather than as it reads, so that the day a server starts
     * honouring it this test fails and says so. The field is real - it round-trips, see above - and
     * Essbase 21.1 simply does not act on it, the same way it accepts {@code zoomIn.ancestor} and
     * always puts the ancestor last.
     */
    @Test
    public void suppressingMissingColumnsDoesNothingOnThisServer() {
        apply(true);

        assertEquals(List.of("East", "West", "South", "Central"), regions(zoomedColumns()));
    }

    private void apply(boolean suppress) {
        cube.openCubeView().setPreferences(new EssCubeView.GridPreferences(
                EssCubeView.Indentation.NONE, suppress, false, false, false,
                EssCubeView.ZoomInPreference.NEXT_LEVEL, false, false, false, false, false,
                suppress, true));
    }

    /** Sales for Caffeine Free Cola, with Market zoomed down the rows. */
    private EssCubeView zoomedRows() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        view.setLayout(new String[][] {
                { "", "100-30", "Actual" },
                { "", "Sales", "" },
                { "Market", "", "" },
        }, 2, 1);
        view.zoomIn(2, 0);
        return view;
    }

    /** The same, transposed. */
    private EssCubeView zoomedColumns() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        view.setLayout(new String[][] {
                { "", "100-30", "Actual" },
                { "", "Market", "" },
                { "Sales", "", "" },
        }, 2, 1);
        view.zoomIn(1, 1);
        return view;
    }

    /** The region names, wherever the grid put them. */
    private static List<String> regions(EssCubeView view) {
        List<String> regions = new ArrayList<>();
        for (int row = 0; row < view.getRows(); row++) {
            for (int column = 0; column < view.getColumns(); column++) {
                String cell = view.getCell(row, column);
                if (cell == null) {
                    continue;
                }
                String member = cell.trim();
                if (List.of("East", "West", "South", "Central").contains(member)) {
                    regions.add(member);
                }
            }
        }
        return regions;
    }
}
