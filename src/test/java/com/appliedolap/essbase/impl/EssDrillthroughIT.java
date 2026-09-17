package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Drill-through against a cube, both flavours.
 *
 * <p>Needs Sample.Basic and a data source called {@code DrillCsvSource} over a delimited file - see
 * docker/essbase in the Cessna repository, which is where that fixture is built. Skipped rather than
 * failed where they are absent, since the point is the local iteration loop and not every server has
 * one.
 */
@Category(DestructiveIntegrationTest.class)
public class EssDrillthroughIT {

    private static final String DATA_SOURCE = "DrillCsvSource";

    private static final String URL_REPORT = "cessna_dt_it_url";

    private static final String DS_REPORT = "cessna_dt_it_ds";

    private final EssServer server = ConnectionUtils.server();

    private EssCube cube;

    @Before
    public void findTheFixture() {
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server has no Sample.Basic", e);
        }
        removeWhateverSurvived();
    }

    @After
    public void removeWhateverSurvived() {
        for (String name : List.of(URL_REPORT, DS_REPORT)) {
            try {
                cube.getDrillthrough(name).delete();
            } catch (NoSuchEssbaseObjectException expected) {
                // the normal case
            }
        }
    }

    /** The flavour Drillbridge deploys: a URL for a client to open, with nothing to run server-side. */
    @Test
    public void createsAndReadsAUrlReport() {
        List<String> regions = List.of("@DESCENDANTS(Market)");
        EssDrillthrough created = cube.createDrillthroughURL(URL_REPORT, "http://example.invalid/drill/1/v2", regions);

        assertEquals(URL_REPORT, created.getName());
        assertEquals(EssDrillthrough.DrillthroughType.URL, created.getDrillthroughType());
        assertEquals("http://example.invalid/drill/1/v2", created.getUrl());
        assertEquals(regions, created.getDrillableRegions());

        EssDrillthrough reread = cube.getDrillthrough(URL_REPORT);
        reread.setUrl("http://example.invalid/drill/2/v2");
        reread.save();
        assertEquals("http://example.invalid/drill/2/v2", cube.getDrillthrough(URL_REPORT).getUrl());
    }

    /**
     * The whole point of the exercise: a report Essbase runs itself, returning the rows behind a cell.
     */
    @Test
    public void executesADataSourceReportAndReturnsTheDetail() {
        Assume.assumeTrue("needs the " + DATA_SOURCE + " fixture", hasDataSource());

        Map<String, EssDrillthroughColumnMapping> mappings = new LinkedHashMap<>();
        mappings.put("Market", new EssDrillthroughColumnMapping("Market",
                EssDrillthroughColumnMapping.MappingType.DIMENSION));
        mappings.put("Product", new EssDrillthroughColumnMapping("Product",
                EssDrillthroughColumnMapping.MappingType.DIMENSION));

        EssDrillthrough report = cube.createDrillthroughDataSource(DS_REPORT, DATA_SOURCE,
                List.of("InvoiceNumber", "Customer", "Units", "Sales"), mappings,
                List.of("@DESCENDANTS(Market)"));

        assertEquals(EssDrillthrough.DrillthroughType.DATASOURCE, report.getDrillthroughType());
        assertEquals(List.of("InvoiceNumber", "Customer", "Units", "Sales"), report.getColumns());
        assertEquals("Market", report.getColumnMappings().get("Market").getDimension());

        Map<String, Set<String>> pov = new LinkedHashMap<>();
        pov.put("Market", Set.of("East"));
        pov.put("Product", Set.of("100-10"));
        EssDrillthroughResult result = report.execute(pov);

        assertEquals(List.of("InvoiceNumber", "Customer", "Units", "Sales"),
                result.getColumns().stream().map(EssDrillthroughResult.Column::getName).toList());
        assertEquals("the datatypes come back alongside the names", "DOUBLE",
                result.getColumns().get(3).getDataType());
        assertFalse("East/100-10 has detail behind it", result.isEmpty());
        assertEquals("every row has a value per column", 4, result.getRows().get(0).size());
    }

    /**
     * A wider cell must return more rows, or the mappings are not filtering anything.
     *
     * <p>The point of interest is the shape of the POV rather than the counts. A drill identifies a
     * <em>cell</em>, and Essbase builds an MDX query to find it, so the POV has to name at least two
     * dimensions even when the report maps only one - name a single dimension and the server answers
     * "Syntax error in input MDX query" rather than anything about the POV being short. Dimensions the
     * report does not map take no part in the filtering; they are there to make the cell a cell.
     */
    @Test
    public void thePovActuallyFilters() {
        Assume.assumeTrue("needs the " + DATA_SOURCE + " fixture", hasDataSource());
        Map<String, EssDrillthroughColumnMapping> mappings = new LinkedHashMap<>();
        mappings.put("Market", new EssDrillthroughColumnMapping("Market",
                EssDrillthroughColumnMapping.MappingType.DIMENSION));
        EssDrillthrough report = cube.createDrillthroughDataSource(DS_REPORT, DATA_SOURCE,
                List.of("InvoiceNumber", "Sales"), mappings, List.of("@DESCENDANTS(Market)"));

        int east = report.execute(pov(Set.of("East"))).getRowCount();
        int eastAndWest = report.execute(pov(Set.of("East", "West"))).getRowCount();

        assertTrue("East alone should be some rows", east > 0);
        assertTrue("two markets should be more rows than one (" + east + " vs " + eastAndWest + ")",
                eastAndWest > east);
    }

    /**
     * Pins the two-dimension rule, because nothing about the failure says what is wrong with the call.
     */
    @Test
    public void aSingleDimensionIsNotACell() {
        Assume.assumeTrue("needs the " + DATA_SOURCE + " fixture", hasDataSource());
        Map<String, EssDrillthroughColumnMapping> mappings = new LinkedHashMap<>();
        mappings.put("Market", new EssDrillthroughColumnMapping("Market",
                EssDrillthroughColumnMapping.MappingType.DIMENSION));
        EssDrillthrough report = cube.createDrillthroughDataSource(DS_REPORT, DATA_SOURCE,
                List.of("InvoiceNumber"), mappings, List.of("@DESCENDANTS(Market)"));
        try {
            report.execute(Map.of("Market", Set.of("East")));
            fail("a one-dimension POV does not describe a cell and should be refused");
        } catch (EssApiException expected) {
            assertTrue("expected the MDX complaint, got: " + expected.getMessage(),
                    expected.getMessage() != null && expected.getMessage().contains("MDX"));
        }
    }

    /** Market is what the report maps; Product is only here to make the POV a cell. */
    private static Map<String, Set<String>> pov(Set<String> markets) {
        Map<String, Set<String>> pov = new LinkedHashMap<>();
        pov.put("Market", markets);
        pov.put("Product", Set.of("100-10"));
        return pov;
    }

    private boolean hasDataSource() {
        return server.getDataSources().stream().anyMatch(ds -> DATA_SOURCE.equals(ds.getName()));
    }

}
