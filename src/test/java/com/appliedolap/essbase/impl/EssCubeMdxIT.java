package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMdxGrid;
import com.appliedolap.essbase.EssMdxOptions;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * MDX queries against Sample.Basic, checking that the options reach the server and the result carries
 * the dimensions on each axis. Read-only.
 */
@Category(ReadOnlyIntegrationTest.class)
public class EssCubeMdxIT {

    private static final String QUERY = "SELECT {[Jan], [Feb]} ON COLUMNS, {[100-10], [100-20]} ON ROWS"
            + " FROM Sample.Basic WHERE ([Sales], [Actual], [East])";

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
    public void aPlainQueryNamesTheDimensionsOnEachAxis() {
        EssMdxGrid grid = cube.executeMdx(QUERY, EssMdxOptions.defaults());

        assertEquals(List.of("Product"), grid.getRowDimensions());
        assertEquals(List.of("Year"), grid.getColumnDimensions());
        assertEquals(List.of("Measures", "Scenario", "Market"), grid.getPageDimensions());
        assertEquals(List.of("Sales", "Actual", "East"), grid.getPageMembers());
        assertEquals(1, grid.getHeaderRows());
        assertEquals("100-10", grid.getCell(1, 0));
        assertEquals("1812.0", grid.getCell(1, 1));
    }

    @Test
    public void aliasesNameTheMembers() {
        EssMdxGrid grid = cube.executeMdx(QUERY, EssMdxOptions.defaults().memberIdentifierType(EssCube.MdxIdentifierType.ALIAS));

        assertEquals("Cola", grid.getCell(1, 0));
        assertEquals("Diet Cola", grid.getCell(2, 0));
    }

    @Test
    public void anAliasTableCanBeChosen() {
        EssMdxGrid grid = cube.executeMdx(QUERY, EssMdxOptions.defaults()
                .memberIdentifierType(EssCube.MdxIdentifierType.ALIAS)
                .aliasTableName("Long Names"));

        assertEquals("January", grid.getCell(0, 1));
    }

    @Test
    public void datalessLeavesTheValuesOut() {
        EssMdxGrid grid = cube.executeMdx(QUERY, EssMdxOptions.defaults().dataless(true));

        assertEquals("100-10", grid.getCell(1, 0));
        assertEquals("", grid.getCell(1, 1));
    }

    @Test
    public void rowsThatAllDropOutLeaveAShortHeaderRow() {
        EssMdxGrid grid = cube.executeMdx("SELECT {[Jan]} ON COLUMNS, NON EMPTY {[100-30]} ON ROWS"
                + " FROM Sample.Basic WHERE ([Sales], [Actual], [Nevada])", EssMdxOptions.defaults());

        assertEquals(1, grid.getRows());
        assertEquals("Jan", grid.getCell(0, 0));
    }

    @Test
    public void aQueryThatOutlastsItsTimeoutFails() {
        // Every product in every market, by member properties: slow enough to outlast a millisecond
        String slow = "SELECT {} ON 0, CROSSJOIN([Product].Members, [Market].Members) DIMENSION PROPERTIES"
                + " PROPERTY_EXPR([Product], MEMBER_ALIAS, CurrentAxisMember(), \"A\") ON 1 FROM Sample.Basic";
        try {
            cube.executeMdx(slow, EssMdxOptions.defaults().timeout(Duration.ofMillis(1)));
            fail("expected a timeout");
        } catch (EssApiException e) {
            Throwable cause = e;
            while (cause != null && !(cause instanceof HttpTimeoutException)) {
                cause = cause.getCause();
            }
            assertNotNull("not a timeout: " + e, cause);
        }
    }

}
