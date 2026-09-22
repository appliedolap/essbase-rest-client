package com.appliedolap.essbase.jaygrid;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import com.appliedolap.jaygrid.CubeView;
import com.appliedolap.jaygrid.GridMember;
import com.appliedolap.jaygrid.impl.CubeViewImpl;
import com.appliedolap.jaygrid.impl.OperationBuilder;
import com.appliedolap.jaygrid.intermediate.IntermediateGrid;
import com.appliedolap.jaygrid.intermediate.IntermediateGridFactory;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Jaygrid driving a real Essbase cube, in one process.
 *
 * <p>Runs against {@code Sample.Basic} rather than {@code Vision.Plan1}, because this is the comparison
 * that finally involves data and Vision has none loaded - suppression against an empty cube suppresses
 * everything and proves nothing.
 *
 * <p>What this is for: the fixture corpus compares Jaygrid's answers to Essbase's, but only their member
 * structure, and only through files copied between two projects by hand. Here both sides are live, so
 * the data comes too, and the options that decide which rows survive by their data can be exercised at
 * last.
 */
public class JaygridAgainstEssbaseIT {

    /**
     * Shared across the class: building one walks the whole outline, and doing that per test was most
     * of the runtime. It holds no per-test state - each test makes its own CubeView over it.
     */
    private static EssbaseGridDataSource dataSource;

    @Before
    public void setUp() {
        if (dataSource != null) {
            return;
        }
        EssServer server = ConnectionUtils.server();
        EssCube cube;
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
            return;
        }
        dataSource = new EssbaseGridDataSource(cube);
    }

    private OperationBuilder jaygrid() {
        CubeView cubeView = new CubeViewImpl(dataSource);
        return new OperationBuilder(cubeView, new IntermediateGridFactory(dataSource));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void theOutlineComesThrough() {
        List<String> names = new ArrayList<>();
        dataSource.getDimensions().forEach(dimension -> names.add(dimension.getName()));

        assertTrue(names.toString(), names.contains("Year"));
        assertTrue(names.toString(), names.contains("Measures"));
        assertEquals("Qtr1", dataSource.getMember("Qtr1").getName());
        assertEquals("Year", dataSource.getMember("Qtr1").getParent().getName());
        assertFalse(dataSource.getMember("Qtr1").getChildren().isEmpty());
    }

    /**
     * The point of the whole exercise: a retrieve through Jaygrid that comes back with real numbers.
     *
     * <p>Read from the grid view rather than from {@code IntermediateGrid.getData()}, which is null
     * here - {@code OperationBuilder.intermediateGrid()} re-interprets the view's labels into regions
     * and does not carry the data across. The view is where the values are.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void aRetrieveThroughJaygridBringsBackData() {
        OperationBuilder op = jaygrid().grid(sheet()).retrieve();
        IntermediateGrid grid = op.intermediateGrid();

        assertEquals(1, grid.getLeft().getRows());
        assertEquals(1, grid.getTop().getColumns());

        String value = op.grid().getStringValue(grid.povHeight() + grid.getTop().getRows(),
                grid.getLeft().getColumns());
        System.out.println("retrieved: '" + value + "'");
        assertTrue("the grid should have come back with a value in it, not " + value, hasValue(value));
    }

    /** Cell (2,0) is Measures on the left axis, so zooming it gives Sample.Basic's real top accounts. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomingThroughJaygridExpandsAgainstTheRealOutline() {
        IntermediateGrid grid = jaygrid().grid(sheet()).retrieve().zoomIn(2, 0).intermediateGrid();

        List<String> members = new ArrayList<>();
        for (int row = 0; row < grid.getLeft().getRows(); row++) {
            GridMember member = grid.getLeft().getCell(row, 0);
            members.add(member == null ? null : member.getName());
        }
        assertTrue(members.toString(), members.contains("Profit"));
        assertTrue(members.toString(), members.contains("Inventory"));
        assertTrue(members.toString(), members.contains("Ratios"));
    }

    /** And the top axis: cell (1,1) is Year, which zooms to the quarters. */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomingTheTopAxisExpandsAcross() {
        IntermediateGrid grid = jaygrid().grid(sheet()).retrieve().zoomIn(1, 1).intermediateGrid();

        List<String> members = new ArrayList<>();
        for (int column = 0; column < grid.getTop().getColumns(); column++) {
            GridMember member = grid.getTop().getCell(0, column);
            members.add(member == null ? null : member.getName());
        }
        assertTrue(members.toString(), members.contains("Qtr1"));
        assertTrue(members.toString(), members.contains("Qtr4"));
    }

    /**
     * Suppress missing, exercised for the first time - the option this whole data source exists for.
     *
     * <p>Asserted as an invariant rather than as a row count, because whether any row is actually
     * missing depends on the data in the cube: with the option on, no row that comes back may be
     * entirely empty. That holds whether or not this particular grid had gaps, and it is the thing the
     * option promises. The counts are printed so a grid with no gaps is visible rather than silently
     * passing.
     */
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void suppressMissingLeavesNoEmptyRows() {
        int kept = rowsAfterZoom(false, false);
        int suppressed = rowsAfterZoom(true, false);
        int emptyWhenSuppressed = rowsAfterZoom(true, true);

        System.out.println("rows kept: " + kept + ", with suppression: " + suppressed
                + ", of which entirely empty: " + emptyWhenSuppressed);
        assertTrue("suppressing missing rows should never add rows", suppressed <= kept);
        assertEquals("no row should come back empty when missing rows are suppressed",
                0, emptyWhenSuppressed);
    }

    /**
     * @param countEmptyOnly count the rows with no value at all rather than all of them
     */
    private int rowsAfterZoom(boolean suppressMissing, boolean countEmptyOnly) {
        CubeView cubeView = new CubeViewImpl(dataSource);
        cubeView.setSuppressMissing(suppressMissing);
        OperationBuilder op = new OperationBuilder(cubeView, new IntermediateGridFactory(dataSource));
        op.grid(sheet()).retrieve().zoomIn(2, 0);
        IntermediateGrid grid = op.intermediateGrid();
        int firstDataRow = grid.povHeight() + grid.getTop().getRows();
        int firstDataColumn = grid.getLeft().getColumns();

        int rows = 0;
        for (int row = 0; row < grid.getLeft().getRows(); row++) {
            boolean anyValue = false;
            for (int column = 0; column < grid.getTop().getColumns(); column++) {
                anyValue |= hasValue(op.grid().getStringValue(firstDataRow + row,
                        firstDataColumn + column));
            }
            if (!countEmptyOnly || !anyValue) {
                rows++;
            }
        }
        return rows;
    }

    /**
     * Whether a cell says anything. {@code #Missing} is a non-empty string and emphatically not a
     * value - checking only for emptiness is how the suppression test passed twice while proving
     * nothing.
     */
    private static boolean hasValue(String cell) {
        return cell != null && !cell.trim().isEmpty() && !cell.trim().equalsIgnoreCase("#Missing");
    }

    /**
     * A plain Sample.Basic grid: Measures down the side, Year across the top, the rest in the POV.
     *
     * <pre>
     *           Product  Market  Scenario
     *           Year
     * Measures
     * </pre>
     */
    private static com.appliedolap.jaygrid.Grid<String> sheet() {
        com.appliedolap.jaygrid.grids.MutableArrayGrid<String> grid =
                new com.appliedolap.jaygrid.grids.MutableArrayGrid<>(3, 4);
        grid.setCell(0, 1, "Product");
        grid.setCell(0, 2, "Market");
        grid.setCell(0, 3, "Scenario");
        grid.setCell(1, 1, "Year");
        grid.setCell(2, 0, "Measures");
        return grid;
    }

}
