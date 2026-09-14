package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssMdxFunction;
import com.appliedolap.essbase.EssMdxFunctionGroup;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads the server's own MDX function reference.
 *
 * <p>The endpoint is scoped to a cube, but what it returns is not: the same server answers
 * byte-for-byte identically for every cube, because this describes the MDX dialect the server
 * implements rather than anything about the data. It answers for a stopped application too. So the
 * cube in the path is a door rather than a subject, which is why {@code EssServer} offers this as
 * well and simply picks one.
 */
public final class EssMdxFunctions {

    private EssMdxFunctions() {
    }

    public static List<EssMdxFunctionGroup> read(ApiContext api, String applicationName, String cubeName) {
        String path = "/applications/" + ApiClient.urlEncode(applicationName)
                + "/databases/" + ApiClient.urlEncode(cubeName) + "/mdxFunctions";
        try {
            String body = NativeHttp.sendForString(api.getClient(),
                    NativeHttp.request(api.getClient(), path).header("Accept", "application/json").GET(),
                    "databasesGetMdxFunctions");
            return parse(body, api.getClient().getObjectMapper());
        } catch (com.appliedolap.essbase.client.ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Turns the server's reply into groups. Separate from the call so it can be tested against a
     * recorded payload rather than a running server.
     */
    static List<EssMdxFunctionGroup> parse(String body, ObjectMapper mapper) {
        try {
            JsonNode root = mapper.readTree(body);
            List<EssMdxFunctionGroup> groups = new ArrayList<>();
            for (JsonNode group : root.path("groups")) {
                String groupName = group.path("name").asText("");
                List<EssMdxFunction> functions = new ArrayList<>();
                for (JsonNode function : group.path("functions")) {
                    functions.add(new EssMdxFunction(groupName,
                            tidy(function.path("name").asText("")),
                            tidy(function.path("syntax").asText("")),
                            tidy(function.path("comment").asText(""))));
                }
                groups.add(new EssMdxFunctionGroup(groupName, functions));
            }
            return Collections.unmodifiableList(groups);
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Squeezes out the layout the server ships inside the text.
     *
     * <p>Every syntax and comment arrives wrapped in newlines and a dozen spaces of indentation,
     * because these are lifted from an XML document and carry its formatting with them - a raw
     * syntax reads {@code "\n                Ancestor ( member , layer )\n            "}. Left alone
     * that turns every row of a table into a three-line cell around one line of content.
     */
    private static String tidy(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

}
