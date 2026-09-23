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
 * Members appearing on an axis more than once: how a grid gets that way, and what keep only does then.
 *
 * <p>It keeps <em>members</em>, not positions. Asking for one occurrence keeps every occurrence, and a
 * range covering every member on the axis - however few of its columns it touches - therefore changes
 * nothing at all. That reads as the operation having been ignored, which is what prompted these: a
 * selection of two columns out of four left all four standing, because the two named the only two
 * members there were.
 *
 * <p>Nor is it a curiosity that has to be contrived. Include-selection leaves a zoomed member in the
 * grid, so zooming it a second time asks for children that are already there and the server duly adds
 * them again - two clicks from a default grid. And there is then no way to ask for one of them, because
 * nothing in the request distinguishes them.
 */
@Category(DestructiveIntegrationTest.class)
public class RepeatedMemberOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    /**
     * Zooming a member twice, which include-selection makes easy: the first zoom leaves the member in
     * the grid beside its children, and zooming it again adds those children a second time.
     */
    @Test
    public void zoomingAMemberTwiceRepeatsItsChildren() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        EssCubeView.GridPreferences current = view.getPreferences();
        view.setPreferences(new EssCubeView.GridPreferences(current.indentation(),
                current.suppressMissingRows(), current.suppressZeroRows(),
                current.suppressUnderscoreRows(), current.repeatMemberLabels(),
                current.zoomInPreference(), true, current.withinSelectedGroup(),
                current.removeUnselectedGroup(), current.useBothNamesAndAliases()));
        view.refresh();

        view.zoomIn(1, 1);
        assertEquals(List.of("Profit", "Inventory", "Ratios", "Measures"), topRow(view));

        // Measures again, which include-selection left at the end of the row.
        view.zoomIn(1, topRow(view).indexOf("Measures") + 1);

        assertEquals("its children arrive a second time rather than being recognised as present",
                List.of("Profit", "Inventory", "Ratios", "Profit", "Inventory", "Ratios", "Measures"),
                topRow(view));
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
