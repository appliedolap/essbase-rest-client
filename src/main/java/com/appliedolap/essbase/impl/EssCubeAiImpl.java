package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssAiFeature;
import com.appliedolap.essbase.EssAiReadiness;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeAi;
import com.appliedolap.essbase.EssMdxGeneration;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The AI capabilities of one cube, spoken to over the {@code /ai} endpoints a 26.1 server adds.
 *
 * <p>None of this goes through the generated client: the client is generated from a 21.7
 * specification, which has no {@code /ai} paths at all. Calls are made with {@link NativeHttp} so
 * they still authenticate the way every generated call does - password, session, or a session
 * established in a browser - rather than needing their own credentials.
 */
public class EssCubeAiImpl implements EssCubeAi {

    private static final Logger logger = LoggerFactory.getLogger(EssCubeAiImpl.class);

    /**
     * The profile every 26.1 server is set up with. Capability endpoints take {@code profileName}
     * as a required parameter with no default, so a caller who never thinks about profiles still
     * has to send something.
     */
    public static final String DEFAULT_PROFILE = "default";

    /**
     * The server's one message for every break in the AI setup chain, whichever link is missing.
     */
    private static final String NO_ASSOCIATED_CONNECTION = "Unable to get associated AI connection";

    private final ApiContext api;

    private final EssCube cube;

    private String profileName = DEFAULT_PROFILE;

    public EssCubeAiImpl(ApiContext api, EssCube cube) {
        this.api = api;
        this.cube = cube;
    }

    @Override
    public EssCubeAi withProfile(String profileName) {
        this.profileName = profileName == null ? DEFAULT_PROFILE : profileName;
        return this;
    }

    @Override
    public EssAiReadiness getReadiness() {
        return getReadiness(null);
    }

    @Override
    public EssAiReadiness getReadiness(EssAiFeature feature) {
        Reply instance;
        try {
            instance = call("GET", "/about/instance", null);
        } catch (RuntimeException e) {
            return EssAiReadiness.undetermined("Could not reach the server to ask what it supports", e);
        }
        EssAiReadiness switchedOff = diagnoseFlags(instance, feature);
        if (switchedOff != null) {
            return switchedOff;
        }

        Reply connections;
        try {
            connections = call("GET", "/ai/connection", null);
        } catch (RuntimeException e) {
            return EssAiReadiness.undetermined("Could not reach the server to check its AI connections", e);
        }
        EssAiReadiness serverLevel = diagnoseServer(connections);
        if (serverLevel != null) {
            return serverLevel;
        }

        Reply application;
        try {
            application = call("GET", aiPath("/conversation"), null);
        } catch (RuntimeException e) {
            return EssAiReadiness.undetermined("Could not reach the server to check "
                    + cube.getApplication().getName() + "'s AI connection", e);
        }
        return diagnoseApplication(application, cube.getApplication().getName(), cube.getName());
    }

    /**
     * Reads the switched-on-or-off rung out of {@code /about/instance}, or returns null when nothing
     * there stands in the way and the configuration rungs decide.
     *
     * <p>This is the cheapest and most certain rung, and it comes first for both reasons: the flags
     * say outright whether a capability exists on this server, where the {@code /ai} paths only let
     * you infer it from a 404. A 21.7 server reports none of these keys at all.
     */
    static EssAiReadiness diagnoseFlags(Reply instance, EssAiFeature feature) {
        if (instance.status / 100 != 2) {
            return EssAiReadiness.undetermined("Asking the server what it supports answered HTTP "
                    + instance.status, new EssApiException(instance.describe("GET /about/instance")));
        }
        Boolean enabled = flag(instance.body, EssAiFeature.ENABLED_FLAG);
        if (enabled == null) {
            return EssAiReadiness.of(EssAiReadiness.State.NOT_SUPPORTED,
                    "This server reports no AI capabilities at all; they were introduced in Essbase 26.1");
        }
        if (!enabled) {
            return EssAiReadiness.of(EssAiReadiness.State.DISABLED_ON_SERVER,
                    "AI is switched off on this server, so no application or cube can use it");
        }
        if (feature != null && !Boolean.TRUE.equals(flag(instance.body, feature.getFlag()))) {
            return EssAiReadiness.of(EssAiReadiness.State.FEATURE_DISABLED,
                    "AI is on for this server but " + feature.getFlag() + " is not, so "
                            + feature + " is unavailable however the rest is configured");
        }
        return null;
    }

    /**
     * Reads one boolean out of the instance document, or null when the key isn't there at all -
     * which is the difference between "off" and "this server has never heard of it".
     */
    private static Boolean flag(String body, String name) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(name) + "\"\\s*:\\s*(true|false)").matcher(body);
        return matcher.find() ? Boolean.valueOf(matcher.group(1)) : null;
    }

    /**
     * Reads the server-wide half of the diagnosis out of a reply to {@code GET /ai/connection}, or
     * returns null when that rung is clear and the application-level one decides.
     */
    static EssAiReadiness diagnoseServer(Reply connections) {
        if (connections.status == 404) {
            return EssAiReadiness.of(EssAiReadiness.State.NOT_SUPPORTED,
                    "This server has no AI endpoints; they were introduced in Essbase 26.1");
        }
        if (connections.status / 100 != 2) {
            return EssAiReadiness.undetermined("Asking the server for its AI connections answered HTTP "
                    + connections.status, new EssApiException(connections.describe("GET /ai/connection")));
        }
        // An AI-capable server with nothing configured answers 200 with a zero-length body and no content
        // type - not 404, and not an empty collection - so an empty body is the "none configured" signal
        // rather than a malformed response.
        if (connections.body.isBlank()) {
            return EssAiReadiness.of(EssAiReadiness.State.NO_CONNECTION_CONFIGURED,
                    "No AI connection is configured on this server, so there is nothing for an "
                            + "application to be associated with; one is created under Sources as a GenAI "
                            + "connection, which in turn needs an Oracle AI Database connection to hold its "
                            + "OCI credentials");
        }
        return null;
    }

    /**
     * Reads the application half of the diagnosis out of a reply to any AI endpoint scoped to a cube.
     */
    static EssAiReadiness diagnoseApplication(Reply reply, String applicationName, String cubeName) {
        if (reply.status / 100 == 2) {
            return EssAiReadiness.of(EssAiReadiness.State.READY,
                    "Application " + applicationName + " is associated with an AI connection");
        }
        if (reply.body.contains(NO_ASSOCIATED_CONNECTION)) {
            return EssAiReadiness.of(EssAiReadiness.State.APPLICATION_NOT_ASSOCIATED,
                    "This server has an AI connection but application " + applicationName
                            + " is not associated with one; associate it before any AI feature on "
                            + cubeName + " will work");
        }
        return EssAiReadiness.undetermined("Asking about " + applicationName
                + "'s AI connection answered HTTP " + reply.status,
                new EssApiException(reply.describe(null)));
    }

    @Override
    public Set<EssAiFeature> getEnabledFeatures() {
        Reply instance = call("GET", "/about/instance", null);
        if (instance.status / 100 != 2 || !Boolean.TRUE.equals(flag(instance.body, EssAiFeature.ENABLED_FLAG))) {
            return EnumSet.noneOf(EssAiFeature.class);
        }
        Set<EssAiFeature> enabled = EnumSet.noneOf(EssAiFeature.class);
        for (EssAiFeature feature : EssAiFeature.values()) {
            if (Boolean.TRUE.equals(flag(instance.body, feature.getFlag()))) {
                enabled.add(feature);
            }
        }
        return enabled;
    }

    @Override
    public EssMdxGeneration generateMdx(String question) {
        return generateMdx(question, null);
    }

    @Override
    public EssMdxGeneration generateMdx(String question, String conversationId) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("A question is required to generate MDX from");
        }
        // The question travels as a query parameter rather than in the body, so it is subject to
        // whatever URL length the server and anything between here and it will accept.
        String path = NativeHttp.withQuery(aiPath("/mdxgenerator"), "profileName", profileName);
        path = NativeHttp.withQuery(path, "isConvStart", conversationId == null);
        path = NativeHttp.withQuery(path, "nlq", question);
        path = NativeHttp.withQuery(path, "includeAttributesInNlq", false);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("data", conversationId == null ? Collections.emptyMap()
                : Collections.singletonMap("conversation_id", conversationId));

        Reply reply = call("POST", path, body);
        if (reply.status / 100 != 2) {
            throw explain(reply, "generate MDX for " + cube.getName());
        }
        return new MdxGeneration(parse(reply.body));
    }

    @Override
    public List<String> getSampleQuestions() {
        Reply reply = call("POST", aiPath("/listSampleQueries"), Collections.emptyMap());
        if (reply.status / 100 != 2) {
            throw explain(reply, "list sample questions for " + cube.getName());
        }
        JsonNode parsed = parseNode(reply.body);
        List<String> questions = new ArrayList<>();
        collectStrings(parsed, questions);
        return questions;
    }

    @Override
    public void clearConversationHistory(String profileName) {
        String path = NativeHttp.withQuery(aiPath("/conversationHistory"), "profileName",
                profileName == null ? this.profileName : profileName);
        Reply reply = call("DELETE", path, null);
        if (reply.status / 100 != 2) {
            throw explain(reply, "clear conversation history for " + cube.getName());
        }
    }

    /**
     * Turns the server's one-size-fits-all 400 into the specific reason, by asking what is actually
     * set up. Only worth the extra round trip on the failure path, which is why readiness isn't
     * checked before every call.
     */
    private EssApiException explain(Reply reply, String attempted) {
        if (reply.body.contains(NO_ASSOCIATED_CONNECTION) || reply.status == 404) {
            EssAiReadiness readiness = getReadiness();
            if (!readiness.isReady()) {
                return new EssApiException("Could not " + attempted + ": " + readiness.getExplanation());
            }
        }
        return new EssApiException("Could not " + attempted + ": " + reply.describe(null));
    }

    private String aiPath(String suffix) {
        return "/ai/applications/" + ApiClient.urlEncode(cube.getApplication().getName())
                + "/databases/" + ApiClient.urlEncode(cube.getName()) + suffix;
    }

    /**
     * Sends a request and hands back whatever came, status included, rather than throwing on a
     * non-2xx: every diagnosis here is made out of the error bodies, so they cannot be exceptions.
     */
    private Reply call(String method, String path, Object body) {
        ApiClient client = api.getClient();
        HttpRequest.Builder request = NativeHttp.request(client, path)
                .header("Accept", "application/json");
        if (body == null) {
            request = "DELETE".equals(method) ? request.DELETE() : request.GET();
        } else {
            try {
                request = request.header("Content-Type", "application/json")
                        .method(method, NativeHttp.jsonBody(client, body));
            } catch (ApiException e) {
                throw new EssApiException(e);
            }
        }
        if (client.getReadTimeout() != null) {
            request.timeout(client.getReadTimeout());
        }
        if (client.getRequestInterceptor() != null) {
            client.getRequestInterceptor().accept(request);
        }
        try {
            HttpResponse<InputStream> response = client.getHttpClient()
                    .send(request.build(), HttpResponse.BodyHandlers.ofInputStream());
            // Run the response interceptor for the same reason every other call does: it is what reads a
            // renewed session deadline and any Set-Cookie back out, and skipping it on this family of
            // calls would quietly leave the caller believing a stale one.
            if (client.getResponseInterceptor() != null) {
                client.getResponseInterceptor().accept(response);
            }
            String responseBody = "";
            if (response.body() != null) {
                try (InputStream stream = response.body()) {
                    responseBody = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
            logger.debug("{} {} answered HTTP {}", method, path, response.statusCode());
            return new Reply(response.statusCode(), responseBody);
        } catch (IOException e) {
            throw new EssApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EssApiException(e);
        }
    }

    private Map<String, Object> parse(String json) {
        JsonNode node = parseNode(json);
        Map<String, Object> parsed = new LinkedHashMap<>();
        if (node != null && node.isObject()) {
            node.fields().forEachRemaining(entry -> parsed.put(entry.getKey(), asJava(entry.getValue())));
        }
        return parsed;
    }

    private JsonNode parseNode(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return api.getClient().getObjectMapper().readTree(json);
        } catch (IOException e) {
            throw new EssApiException("The server's answer was not JSON: " + json);
        }
    }

    private static Object asJava(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (node.isValueNode()) {
            return node.isNumber() ? node.numberValue() : node.isBoolean() ? node.booleanValue() : node.asText();
        }
        if (node.isArray()) {
            List<Object> values = new ArrayList<>();
            node.forEach(child -> values.add(asJava(child)));
            return values;
        }
        Map<String, Object> values = new LinkedHashMap<>();
        node.fields().forEachRemaining(entry -> values.put(entry.getKey(), asJava(entry.getValue())));
        return values;
    }

    private static void collectStrings(JsonNode node, List<String> into) {
        if (node == null) {
            return;
        }
        if (node.isTextual()) {
            into.add(node.asText());
        } else {
            node.forEach(child -> collectStrings(child, into));
        }
    }

    static final class Reply {

        private static final Pattern JSON_ERROR = Pattern.compile("\"errorMessage\"\\s*:\\s*\"([^\"]*)\"");

        private static final Pattern XML_ERROR = Pattern.compile("<errorMessage>([^<]*)</errorMessage>");

        final int status;

        final String body;

        Reply(int status, String body) {
            this.status = status;
            this.body = body;
        }

        /**
         * The server puts its reason in an {@code errorMessage} field, but not always in JSON - a 21.7
         * server answers a 404 for these paths in XML however you set {@code Accept} - so this reads
         * the field where it can and falls back to the body as sent.
         */
        String describe(String operation) {
            String detail = body.isBlank() ? null : body.trim();
            Matcher json = JSON_ERROR.matcher(body);
            Matcher xml = XML_ERROR.matcher(body);
            if (json.find()) {
                detail = json.group(1);
            } else if (xml.find()) {
                detail = xml.group(1);
            }
            return (operation == null ? "" : operation + " ") + "HTTP " + status
                    + (detail == null || detail.isBlank() ? "" : " - " + detail);
        }

    }

    /**
     * The generator's answer. Which keys a successful response carries is not in the specification -
     * the 26.1 document declares the operation with an empty 200 - so the query and conversation id
     * are looked for under the spellings a server has been seen to use, and everything is kept.
     */
    static final class MdxGeneration implements EssMdxGeneration {

        private static final List<String> MDX_KEYS = List.of("mdx", "mdxQuery", "query", "generatedMdx", "mdx_query");

        private static final List<String> CONVERSATION_KEYS = List.of("conversation_id", "conversationId");

        private final Map<String, Object> raw;

        MdxGeneration(Map<String, Object> raw) {
            this.raw = raw;
        }

        @Override
        public String getMdx() {
            return find(MDX_KEYS);
        }

        @Override
        public String getConversationId() {
            return find(CONVERSATION_KEYS);
        }

        @Override
        public Map<String, Object> getRawResponse() {
            return Collections.unmodifiableMap(raw);
        }

        private String find(List<String> keys) {
            for (String key : keys) {
                Object value = raw.get(key);
                if (value instanceof String && !((String) value).isBlank()) {
                    return (String) value;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return String.valueOf(getMdx());
        }

    }

}
