package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssLogSetting;
import com.appliedolap.essbase.EssMaintenanceLimits;
import com.appliedolap.essbase.EssResourceLimit;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The platform service settings - what the server says about its machine, and how it rotates its
 * logs.
 *
 * <p>Read by hand rather than through the generated client, for two different reasons. The log
 * settings are 26.1 only and absent from the 21.7 specification the client is built from. The
 * maintenance limits are worse: the generated call exists but deserializes into a model of an older
 * shape - coreFileSize, openFiles, webLogicHeapSize - where the server now answers with disk and ram.
 * Nothing fails. It returns an object with every field null, which is the least useful way for a call
 * to be wrong.
 */
final class EssSettings {

    private EssSettings() {
    }

    static EssMaintenanceLimits maintenanceLimits(ApiContext api) {
        JsonNode root = get(api, "/settings/maintenance");
        return new EssMaintenanceLimits(resource(root.path("disk")), resource(root.path("ram")));
    }

    private static EssResourceLimit resource(JsonNode node) {
        if (node.isMissingNode()) {
            return null;
        }
        return new EssResourceLimit(node.path("id").asText(null),
                node.has("available") ? node.path("available").asInt() : null,
                node.has("limit") ? node.path("limit").asInt() : null);
    }

    static List<EssLogSetting> logSettings(ApiContext api) {
        List<EssLogSetting> settings = new ArrayList<>();
        for (JsonNode item : get(api, "/settings/odlLogSettings").path("items")) {
            settings.add(new EssLogSetting(item.path("handlerName").asText(null),
                    item.path("logType").asText(null),
                    size(item.path("maxLogSize")), size(item.path("maxAllLogSize"))));
        }
        return Collections.unmodifiableList(settings);
    }

    /** The sizes arrive as strings - "10", not 10 - so they are read leniently and given back as sent. */
    private static Integer size(JsonNode node) {
        if (node.isMissingNode() || node.isNull()) {
            return null;
        }
        try {
            return Integer.valueOf(node.asText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static void setLogSettings(ApiContext api, List<EssLogSetting> settings) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (EssLogSetting setting : settings) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("handlerName", setting.getHandlerName());
            item.put("logType", setting.getLogType());
            // Back as strings, the way they came: the server sends "10" rather than 10.
            item.put("maxLogSize", String.valueOf(setting.getMaxLogSize()));
            item.put("maxAllLogSize", String.valueOf(setting.getMaxAllLogSize()));
            items.add(item);
        }
        try {
            NativeHttp.sendAndDiscard(api.getClient(),
                    NativeHttp.request(api.getClient(), "/settings/odlLogSettings")
                            .header("Content-Type", "application/json")
                            // A bare array, though the GET wraps the same list in an "items" object.
                            // Sending it back the way it arrived earns "Cannot deserialize value of
                            // type ArrayList<ODLLogHandlerSetting> from Object value".
                            .PUT(NativeHttp.jsonBody(api.getClient(), items)),
                    "setodllogsettings");
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    private static JsonNode get(ApiContext api, String path) {
        try {
            String body = NativeHttp.sendForString(api.getClient(),
                    NativeHttp.request(api.getClient(), path).header("Accept", "application/json").GET(),
                    "pSMSettings" + path);
            return api.getClient().getObjectMapper().readTree(body);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

}
