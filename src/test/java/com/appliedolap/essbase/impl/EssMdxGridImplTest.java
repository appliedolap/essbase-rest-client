package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMdxGrid;
import com.appliedolap.essbase.EssMdxOptions;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.model.MDXInput;
import com.appliedolap.essbase.client.model.NamedQueriesPreferences;
import com.appliedolap.essbase.misc.MdxJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Offline. MDX results as Essbase 21.7 and 26.1 return them, read the way
 * {@link EssCubeImpl#executeMdx(String, EssMdxOptions)} reads them, and the preferences sent for each set
 * of options.
 */
public class EssMdxGridImplTest {

    private final ObjectMapper mapper = new ApiClient().getObjectMapper();

    private EssMdxGrid read(String json) throws Exception {
        return new EssMdxGridImpl(mapper.readValue(json, MdxJson.class));
    }

    @Test
    public void readsCellsAndDimensionsFrom261() throws Exception {
        EssMdxGrid grid = read("{ \"metadata\" : {\"page\" : [{\"id\":0,\"name\":\"Measures\",\"pov\":\"Sales\",\"expand\":\"0\"},"
                + "{\"id\":1,\"name\":\"Scenario\",\"pov\":\"Actual\",\"expand\":\"0\"}],\"column\" : [\"Year\"],\"row\" : [\"Market\",\"Product\"]},"
                + "\"data\" : [[\"\",\"\",\"Jan\"],[\"East\",\"100-10\",\"1812.0\"],[\"West\",\"100-10\",\"#Missing\"]] }");

        assertEquals(List.of("Market", "Product"), grid.getRowDimensions());
        assertEquals(List.of("Year"), grid.getColumnDimensions());
        assertEquals(List.of("Measures", "Scenario"), grid.getPageDimensions());
        assertEquals(List.of("Sales", "Actual"), grid.getPageMembers());
        assertEquals(1, grid.getHeaderRows());
        assertEquals(3, grid.getRows());
        assertEquals(3, grid.getColumns());
        assertEquals("Jan", grid.getCell(0, 2));
        assertEquals("1812.0", grid.getCell(1, 2));
        assertEquals("#Missing", grid.getCell(2, 2));
    }

    @Test
    public void readsPageDimensionsWrittenAsNamesBy21() throws Exception {
        EssMdxGrid grid = read("{\"metadata\":{\"page\":[\"Measures\",\"Scenario\"],\"column\":[\"Year\"],\"row\":[\"Product\"]},"
                + "\"data\":[[\"\",\"Jan\"],[\"100-10\",\"1812.0\"]]}");

        assertEquals(List.of("Measures", "Scenario"), grid.getPageDimensions());
        assertEquals("21.7 doesn't say which members", Arrays.asList(null, null), grid.getPageMembers());
        assertEquals("100-10", grid.getCell(1, 0));
    }

    @Test
    public void aMemberListHasNoHeaderRows() throws Exception {
        EssMdxGrid grid = read("{\"metadata\":{\"page\":[],\"column\":[],\"row\":[\"Product\"]},"
                + "\"data\":[[\"100\"],[\"200\"],[\"300\"]]}");

        assertEquals(0, grid.getHeaderRows());
        assertEquals(3, grid.getRows());
        assertEquals(1, grid.getColumns());
    }

    @Test
    public void aResultWhoseRowsAllDroppedOutIsJustAShortHeaderRow() throws Exception {
        // NON EMPTY over rows that are all #Missing, from 26.1: the header leaves out the cell over Product
        EssMdxGrid grid = read("{\"metadata\":{\"page\":[],\"column\":[\"Year\"],\"row\":[\"Product\"]},\"data\":[[\"Jan\"]]}");

        assertEquals(1, grid.getRows());
        assertEquals(1, grid.getColumns());
        assertEquals(1, grid.getHeaderRows());
        assertEquals("Jan", grid.getCell(0, 0));
    }

    @Test
    public void aShorterRowReadsNullPastItsEnd() throws Exception {
        EssMdxGrid grid = read("{\"metadata\":{\"column\":[\"Year\"],\"row\":[\"Product\"]},\"data\":[[\"Jan\"],[\"100-10\",\"1812.0\"]]}");

        assertEquals(2, grid.getColumns());
        assertNull(grid.getCell(0, 1));
    }

    @Test
    public void noDataAndNoMetadataIsAnEmptyGrid() throws Exception {
        EssMdxGrid grid = read("{}");

        assertEquals(0, grid.getRows());
        assertEquals(0, grid.getColumns());
        assertEquals(0, grid.getHeaderRows());
        assertEquals(List.of(), grid.getRowDimensions());
        assertEquals(List.of(), grid.getPageDimensions());
    }

    @Test
    public void defaultOptionsSendNoPreferences() throws Exception {
        assertNull(EssCubeImpl.toPreferences(EssMdxOptions.defaults()));
        assertNull("a timeout isn't a preference", EssCubeImpl.toPreferences(EssMdxOptions.defaults().timeout(Duration.ofSeconds(5))));
        assertNull(EssCubeImpl.toPreferences(null));

        MDXInput input = new MDXInput();
        input.setQuery("SELECT {} ON 0 FROM Sample.Basic");
        input.setPreferences(EssCubeImpl.toPreferences(EssMdxOptions.defaults()));
        assertEquals("{\"query\":\"SELECT {} ON 0 FROM Sample.Basic\"}", mapper.writeValueAsString(input));
    }

    @Test
    public void onlyTheOptionsSetAreSent() throws Exception {
        NamedQueriesPreferences preferences = EssCubeImpl.toPreferences(EssMdxOptions.defaults()
                .dataless(true)
                .memberIdentifierType(EssCube.MdxIdentifierType.ALIAS)
                .aliasTableName("Long Names"));

        assertEquals("{\"dataless\":true,\"memberIdentifierType\":\"ALIAS\",\"aliasTableName\":\"Long Names\"}",
                mapper.writeValueAsString(preferences));
    }

}
