package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

/**
 * The one place this package talks to the connection and datasource endpoints.
 */
final class EssConnections {

    private EssConnections() {
    }

    static String send(ApiContext api, String method, String path, Object body, String operationId) {
        HttpRequest.Builder request = NativeHttp.request(api.getClient(), path)
                .header("Accept", "application/json");
        try {
            if (body == null) {
                request = "DELETE".equals(method) ? request.DELETE()
                        : "POST".equals(method) ? request.POST(HttpRequest.BodyPublishers.noBody())
                        : request.GET();
            } else {
                request = request.header("Content-Type", "application/json")
                        .method(method, NativeHttp.jsonBody(api.getClient(), body));
            }
            try (InputStream in = NativeHttp.send(api.getClient(), request, operationId).body()) {
                return in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

}
