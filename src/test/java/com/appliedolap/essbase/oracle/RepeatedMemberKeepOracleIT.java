package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Keep only, where the same member appears on an axis more than once.
 *
 * <p>It keeps <em>members</em>, not positions. Asking for one occurrence keeps every occurrence, and a
 * range covering every member on the axis - however few of its columns it touches - therefore changes
 * nothing at all. That reads as the operation having been ignored, which is what prompted these: a
 * selection of two columns out of four left all four standing, because the two named the only two
 * members there were.
 *
 * <p>A grid can hold a member twice through an ordinary pivot or a hand-built layout, so this is not a
 * curiosity - and there is no way to ask for one of them, because nothing in the request distinguishes
 * them.
 */
@Category(DestructiveIntegrationTest.class)
public class RepeatedMemberKeepOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    /** Two measures laid out twice over, so each member has two columns. */
    private EssCubeView repeatedColumns() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        view.setLayout(new String[][] {
                { "", "Product", "Market", "", "Scenario" },
                { "", "Inventory", "Ratios", "Inventory", "Ratios" },
                { "Year", "", "", "", "" },
        }, 2, 1);
        assertEquals(List.of("Inventory", "Ratios", "Inventory", "Ratios"), topRow(view));
        return view;
    }

    @Test
    public void keepingOneOccurrenceKeepsThemAll() {
        EssCubeView view = repeatedColumns();

        view.keepOnly(List.of(new EssCubeView.Range(1, 1, 1, 1)));

        assertEquals("both Inventory columns stay, and only Ratios goes",
                List.of("Inventory", "Inventory"), topRow(view));
    }

    /**
     * And so a selection naming every member on the axis leaves the grid alone, however small it is.
     * Nothing has gone wrong; there is simply nothing that is not being kept.
     */
    @Test
    public void keepingARangeThatNamesEveryMemberChangesNothing() {
        EssCubeView view = repeatedColumns();

        view.keepOnly(List.of(new EssCubeView.Range(1, 3, 1, 2)));

        assertEquals(List.of("Inventory", "Ratios", "Inventory", "Ratios"), topRow(view));
    }

    private static List<String> topRow(EssCubeView view) {
        List<String> members = new ArrayList<>();
        for (int column = 0; column < view.getColumns(); column++) {
            String cell = view.getCell(1, column);
            if (cell != null && !cell.isBlank()) {
                members.add(cell.trim());
            }
        }
        return members;
    }
}
