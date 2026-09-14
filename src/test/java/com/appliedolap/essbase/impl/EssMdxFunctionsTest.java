package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssMdxFunction;
import com.appliedolap.essbase.EssMdxFunctionGroup;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Offline. The payload is verbatim from a 26.1 server, trimmed to two groups of two, because the
 * thing worth pinning down is what the server wraps its text in: every syntax and comment arrives
 * padded with newlines and the indentation of the XML document it was lifted out of.
 */
public class EssMdxFunctionsTest {

    private static final String RESPONSE =
            "{\n" +
            "  \"groups\": [\n" +
            "    {\n" +
            "      \"name\": \"Member\",\n" +
            "      \"functions\": [\n" +
            "        {\n" +
            "          \"name\": \"Ancestor\",\n" +
            "          \"syntax\": \"\\n                Ancestor ( member , layer | index [, hierarchy ] )\\n            \",\n" +
            "          \"comment\": \"\\n                Returns a member that is an ancestor of the specified member, at a specified generation or level.\\n            \"\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"LinkMember\",\n" +
            "          \"syntax\": \"\\n                LinkMember ([ member, ] hierarchy )\\n            \",\n" +
            "          \"comment\": \"\\n                Returns a member\\u2019s shared member along a given hierarchy.\\n            \"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Set\",\n" +
            "      \"functions\": [\n" +
            "        {\n" +
            "          \"name\": \"CrossJoin\",\n" +
            "          \"syntax\": \"\\n                CrossJoin ( set1, set2 )\\n            \",\n" +
            "          \"comment\": \"\\n                Returns a cross-section of two sets from different dimensions.\\n            \"\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"CrossJoinAttribute \",\n" +
            "          \"syntax\": \"\\n                CrossJoinAttribute  ( set1, set2 )\\n            \",\n" +
            "          \"comment\": \"\\n                Returns a cross-section of two sets from different dimensions, but skips calculation of non-existing intersections\\n            \"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    @Test
    public void readsGroupsInServerOrder() {
        List<EssMdxFunctionGroup> groups = EssMdxFunctions.parse(RESPONSE, new com.fasterxml.jackson.databind.ObjectMapper());
        assertEquals(2, groups.size());
        assertEquals("Member", groups.get(0).getName());
        assertEquals("Set", groups.get(1).getName());
    }

    @Test
    public void stripsTheLayoutOutOfSyntaxAndComment() {
        EssMdxFunction ancestor = EssMdxFunctions
                .parse(RESPONSE, new com.fasterxml.jackson.databind.ObjectMapper())
                .get(0).getFunctions().get(0);
        assertEquals("Ancestor", ancestor.getName());
        assertEquals("Ancestor ( member , layer | index [, hierarchy ] )", ancestor.getSyntax());
        assertEquals("Returns a member that is an ancestor of the specified member, "
                + "at a specified generation or level.", ancestor.getComment());
    }

    /** The group is carried onto each function, so one can be shown without its parent nearby. */
    @Test
    public void stampsEachFunctionWithItsGroup() {
        List<EssMdxFunctionGroup> groups = EssMdxFunctions.parse(RESPONSE, new com.fasterxml.jackson.databind.ObjectMapper());
        for (EssMdxFunctionGroup group : groups) {
            for (EssMdxFunction function : group.getFunctions()) {
                assertEquals(group.getName(), function.getGroup());
            }
        }
    }

}
