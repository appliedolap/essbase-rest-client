package com.appliedolap.essbase.misc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Offline. These are verbatim responses from the MDX-execute endpoint on the two server versions
 * available to test against, trimmed to one row of data - 21.7 writes each page dimension as a bare
 * name, 26.1 as an object, and the same client has to read both.
 */
public class MdxJsonTest {

    private static final String PAGE_AS_NAMES = "{ \"metadata\" : {\"page\" : [\"Product\",\"Market\",\"Scenario\"],"
            + "\"column\" : [\"Measures\"],\"row\" : [\"Year\"]},"
            + "\"data\" : [[\"\",\"Sales\"],[\"Year\",\"400855.0\"]] }";

    private static final String PAGE_AS_OBJECTS = "{ \"metadata\" : {\"page\" : ["
            + "{\"id\":0,\"name\":\"Product\",\"pov\":\"Product\",\"expand\":\"0\"},"
            + "{\"id\":1,\"name\":\"Market\",\"pov\":\"Market\",\"expand\":\"0\"},"
            + "{\"id\":2,\"name\":\"Scenario\",\"pov\":\"Scenario\",\"expand\":\"0\"}],"
            + "\"column\" : [\"Measures\"],\"row\" : [\"Year\"]},"
            + "\"data\" : [[\"\",\"Sales\"],[\"Year\",\"400855.0\"]] }";

    private MdxJson read(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, MdxJson.class);
    }

    @Test
    public void readsPageDimensionsWrittenAsNames() throws Exception {
        List<MdxJson.PageDimension> page = read(PAGE_AS_NAMES).getMetadata().getPage();

        assertEquals(3, page.size());
        assertEquals("Product", page.get(0).getName());
        assertNull("21.7 doesn't report a POV member", page.get(0).getPov());
    }

    @Test
    public void readsPageDimensionsWrittenAsObjects() throws Exception {
        List<MdxJson.PageDimension> page = read(PAGE_AS_OBJECTS).getMetadata().getPage();

        assertEquals(3, page.size());
        assertEquals("Product", page.get(0).getName());
        assertEquals("Product", page.get(0).getPov());
        assertEquals(Integer.valueOf(2), page.get(2).getId());
    }

    @Test
    public void readsTheSameGridFromEitherForm() throws Exception {
        for (String json : List.of(PAGE_AS_NAMES, PAGE_AS_OBJECTS)) {
            MdxJson.Metadata metadata = read(json).getMetadata();

            assertEquals(List.of("Measures"), metadata.getColumn());
            assertEquals(List.of("Year"), metadata.getRow());
            assertEquals(List.of(List.of("", "Sales"), List.of("Year", "400855.0")), read(json).getData());
        }
    }

}
