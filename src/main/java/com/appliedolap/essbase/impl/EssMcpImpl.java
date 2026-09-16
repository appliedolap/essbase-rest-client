package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssMcp;
import com.appliedolap.essbase.EssMcpParameter;
import com.appliedolap.essbase.EssMcpResult;
import com.appliedolap.essbase.EssMcpTool;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Talks to the MCP server Essbase 26.1 serves from inside its own REST API.
 *
 * <p>Not through the generated client, even though 26.1 does describe these paths. It describes them
 * as empty stubs - no request body, no parameters, no responses - and with no tag, so they generate
 * as {@code DefaultApi.callTool()} and {@code listTools()} returning {@code void}: callable, and
 * incapable of handing back the answer. Hence plain requests and hand-read JSON.
 */
public class EssMcpImpl implements EssMcp {

    private static final String DISCOVERY = "/ess-mcp";

    private static final String TOOLS = "/ess-mcp/tools";

    private static final String CALL = "/ess-mcp/call";

    private final ApiContext api;

    /** Cached: the answer is fixed for the life of a server, and the tree asks more than once. */
    private Boolean available;

    private String serverInfo;

    public EssMcpImpl(ApiContext api) {
        this.api = api;
    }

    @Override
    public boolean isAvailable() {
        if (available == null) {
            discover();
        }
        return available;
    }

    @Override
    public Optional<String> getServerInfo() {
        if (available == null) {
            discover();
        }
        return Optional.ofNullable(serverInfo);
    }

    /**
     * Asks the discovery endpoint who is there, and treats a 404 as "this server has no MCP server"
     * rather than as a failure - which is the ordinary state of anything before 26.1.
     */
    private void discover() {
        Reply reply = send("GET", DISCOVERY, null);
        if (reply.status == 404) {
            available = false;
            return;
        }
        if (reply.status / 100 != 2) {
            available = false;
            throw new EssApiException("Could not ask this server about its MCP server: HTTP "
                    + reply.status + " " + reply.body);
        }
        available = true;
        JsonNode server = parse(reply.body).path("server");
        String name = server.path("name").asText("");
        String version = server.path("version").asText("");
        serverInfo = (name + " " + version).trim();
    }

    @Override
    public List<EssMcpTool> getTools() {
        if (!isAvailable()) {
            return Collections.emptyList();
        }
        Reply reply = send("GET", TOOLS, null);
        if (reply.status / 100 != 2) {
            throw new EssApiException("Could not list the MCP tools: HTTP " + reply.status + " " + reply.body);
        }
        List<EssMcpTool> tools = new ArrayList<>();
        for (JsonNode tool : parse(reply.body).path("tools")) {
            tools.add(new EssMcpTool(tool.path("name").asText(""),
                    tidy(tool.path("description").asText("")),
                    parametersOf(tool.path("inputSchema"))));
        }
        tools.sort(Comparator.comparing(EssMcpTool::getName));
        return Collections.unmodifiableList(tools);
    }

    /**
     * Reads a tool's arguments out of its JSON Schema, required ones first.
     *
     * <p>Required first because that is the order anyone filling them in wants: the ones without
     * which the call cannot be made at all, before the ones that merely narrow it.
     */
    private static List<EssMcpParameter> parametersOf(JsonNode schema) {
        List<String> required = new ArrayList<>();
        for (JsonNode name : schema.path("required")) {
            required.add(name.asText());
        }
        List<EssMcpParameter> parameters = new ArrayList<>();
        JsonNode properties = schema.path("properties");
        properties.fieldNames().forEachRemaining(name -> {
            JsonNode property = properties.path(name);
            parameters.add(new EssMcpParameter(name,
                    property.path("type").asText("string"),
                    tidy(property.path("description").asText("")),
                    required.contains(name)));
        });
        parameters.sort(Comparator.comparing(EssMcpParameter::isRequired).reversed());
        return parameters;
    }

    @Override
    public EssMcpResult call(String toolName, Map<String, Object> arguments) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", toolName);
        body.put("arguments", arguments == null ? Collections.emptyMap() : arguments);

        Reply reply = send("POST", CALL, body);
        if (reply.status / 100 != 2) {
            throw new EssApiException("Could not call " + toolName + ": HTTP "
                    + reply.status + " " + reply.body);
        }
        JsonNode parsed = parse(reply.body);
        StringBuilder text = new StringBuilder();
        for (JsonNode piece : parsed.path("content")) {
            if (text.length() > 0) {
                text.append('\n');
            }
            text.append(piece.path("text").asText(""));
        }
        return new EssMcpResult(parsed.path("isError").asBoolean(false), text.toString());
    }

    private JsonNode parse(String body) {
        try {
            return api.getClient().getObjectMapper().readTree(body);
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    /** Descriptions arrive with the line breaks of the source they were written in. */
    private static String tidy(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    /**
     * Sends a request and hands back the status with the body rather than throwing on a non-2xx: a
     * 404 from discovery is an answer (no MCP server here), not a failure.
     */
    private Reply send(String method, String path, Object body) {
        try {
            var request = NativeHttp.request(api.getClient(), path).header("Accept", "application/json");
            if (body == null) {
                request = request.GET();
            } else {
                request = request.header("Content-Type", "application/json")
                        .method(method, NativeHttp.jsonBody(api.getClient(), body));
            }
            HttpResponse<InputStream> response = NativeHttp.sendAllowingErrors(api.getClient(), request);
            String responseBody = "";
            if (response.body() != null) {
                try (InputStream stream = response.body()) {
                    responseBody = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
            return new Reply(response.statusCode(), responseBody);
        } catch (ApiException | IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new EssApiException(e);
        }
    }

    private static final class Reply {

        private final int status;

        private final String body;

        private Reply(int status, String body) {
            this.status = status;
            this.body = body;
        }

    }

}
