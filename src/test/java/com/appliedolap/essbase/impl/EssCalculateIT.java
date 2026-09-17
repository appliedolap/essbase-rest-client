package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssGrid;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Calculating a cube, which is the step that makes a freshly loaded one stop looking empty.
 *
 * <p>Asserts against an upper-level number rather than a level-0 one, because level 0 is what the
 * load already wrote - a test that read it would pass without the calculation having done anything.
 */
@Category(DestructiveIntegrationTest.class)
public class EssCalculateIT {

    private final EssServer server = ConnectionUtils.server();

    private EssCube cube;

    @Before
    public void findSampleBasic() {
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server has no Sample.Basic", e);
        }
    }

    @Test
    public void theDefaultCalculationAggregatesTheCube() {
        cube.calculate();
        String total = upperLevelSales();
        assertNotEquals("the top of the cube should not be empty after a calculation",
                "#Missing", total);
        assertTrue("and it should be a number, not a message: " + total,
                total.matches("-?[0-9.]+"));
    }

    /** Script text rather than a stored script - the path EssScript.execute cannot take. */
    @Test
    public void runsCalculationTextThatIsNotAStoredScript() {
        cube.calculate("CALC ALL;");
        assertNotEquals("#Missing", upperLevelSales());
    }

    @Test
    public void aBadCalculationFailsLoudly() {
        try {
            cube.calculate("THIS IS NOT ESSBASE CALC SCRIPT SYNTAX;");
            // Some servers accept nonsense and fail the job; either way it must not look successful.
        } catch (RuntimeException expected) {
            return;
        }
        // If it did not throw, the job must still have reported failure rather than silence - which
        // the calculate() contract turns into an exception, so reaching here means it claimed success.
        throw new AssertionError("a calculation of nonsense should not report success");
    }

    /** Sales at the top of every dimension - #Missing unless something aggregated it. */
    private String upperLevelSales() {
        EssGrid grid = cube.executeMdx("SELECT {[Sales]} ON COLUMNS,"
                + " {([Product],[Market],[Year],[Actual])} ON ROWS FROM Sample.Basic");
        // The value is the last cell of the last row; the rest is member labels.
        return grid.getCell(grid.getRows() - 1, grid.getColumns() - 1);
    }

}
