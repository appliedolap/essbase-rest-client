package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Alias tables: listing a cube's, and labelling a grid from one.
 *
 * <p>The alias table is one of the few things the grid API takes per request rather than reading from
 * the session's preferences, so it belongs to a view rather than to a connection - two grids open at
 * once can be reading different ones.
 */
@Category(DestructiveIntegrationTest.class)
public class AliasTableOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    /** Every cube has Default, whether or not anyone added others. */
    @Test
    public void listsTheCubesAliasTables() {
        List<String> tables = cube.getAliasTables();
        System.out.println("alias tables: " + tables);

        assertTrue("expected Default among " + tables, tables.contains("Default"));
    }

    /** A grid reports the table it was labelled from, and takes one for the next action. */
    @Test
    public void labelsAGridFromTheChosenTable() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        assertNotNull("a fresh grid should report the table it used", view.getAliasTable());

        for (String table : cube.getAliasTables()) {
            view.setAliasTable(table);
            view.refresh();
            assertEquals("asked for " + table, table, view.getAliasTable());
        }
    }
}
