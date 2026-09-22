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
 * Reordering dimensions <em>within</em> an axis, which the REST API has no separate action for.
 *
 * <p>It falls out of the ordinary pivot: name a dimension's cell as the source and a neighbouring
 * position on the same axis as the destination, and the two change places. Nothing about the request
 * says "stay on this axis" - the destination cell being on the axis the dimension is already on is
 * what makes it a reorder rather than a move, which is why this is an emergent behaviour rather than
 * an eighth grid action.
 *
 * <p>The destination is a cell on the <em>axis's own line</em>, not on the header row the dimension
 * is drawn in: for the row axis that is the first data row, and for the column axis the first data
 * column. Aiming at the drawn cell instead is the mistake worth having a test for.
 */
@Category(DestructiveIntegrationTest.class)
public class WithinAxisOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    /** Two dimensions on the row axis; moving the inner one outward puts it first. */
    @Test
    public void movingARowDimensionOutwardReordersTheRowAxis() {
        EssCubeView view = laidOut(new String[][] {
                { "", "", "Market", "Scenario" },
                { "", "", "Measures", "" },
                { "Year", "Product", "", "" },
        }, 2, 2);
        assertEquals(List.of("LEFT0=Year", "LEFT1=Product", "TOP1=Measures"), axes(view));

        // Product's cell, to the row axis's first position on the first data row.
        view.pivot(view.cellIndex(2, 1), view.cellIndex(2, 0));

        assertEquals(List.of("LEFT0=Product", "LEFT1=Year", "TOP1=Measures"), axes(view));
    }

    /** The same on the column axis, where the axis's line runs down the first data column. */
    @Test
    public void movingAColumnDimensionOutwardReordersTheColumnAxis() {
        EssCubeView view = laidOut(new String[][] {
                { "", "Market", "Scenario" },
                { "", "Measures", "" },
                { "", "Product", "" },
                { "Year", "", "" },
        }, 1, 3);
        assertEquals(List.of("LEFT0=Year", "TOP1=Measures", "TOP2=Product"), axes(view));

        // Product's cell, to the position Measures occupies.
        view.pivot(view.cellIndex(2, 1), view.cellIndex(1, 1));

        assertEquals(List.of("LEFT0=Year", "TOP1=Product", "TOP2=Measures"), axes(view));
    }

    /**
     * An axis dimension moved into the POV.
     *
     * <p>The plain pivot cannot do this: aimed at a POV-row cell it reads the destination *column* and
     * puts the dimension on the column axis instead. Only {@code pivotToPov} reaches the POV.
     */
    @Test
    public void pivotToPovIsTheOnlyWayIntoThePov() {
        EssCubeView view = laidOut(new String[][] {
                { "", "", "Market", "Scenario" },
                { "", "", "Measures", "" },
                { "Year", "Product", "", "" },
        }, 2, 2);

        view.pivotToPov(view.cellIndex(2, 1), view.cellIndex(0, 2));

        assertEquals(List.of("LEFT0=Year", "TOP1=Measures"), axes(view));
    }

    private EssCubeView laidOut(String[][] layout, int leftColumns, int headerRows) {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        view.setLayout(layout, headerRows, leftColumns);
        return view;
    }

    private static List<String> axes(EssCubeView view) {
        List<String> placements = new ArrayList<>();
        for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
            if (placement.region() != EssCubeView.DimensionPlacement.Region.POV) {
                placements.add(placement.region() + "" + placement.index() + "=" + placement.name());
            }
        }
        placements.sort(String::compareTo);
        return placements;
    }
}
