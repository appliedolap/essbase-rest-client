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
 * The one way this package talks to the scripts endpoints.
 *
 * <p>Shared by {@link EssScriptImpl} and the listing on {@link EssCubeImpl} so the {@code file} type
 * parameter is applied in one place. Leaving it off, or sending it empty, is rejected with "Invalid
 * file type. Supported files are Calculation and MDX scripts" - which is the error a Swagger UI form
 * produces from a blank field, and not an obvious one to diagnose.
 */
final class EssScripts {

    private EssScripts() {
    }

    static String send(ApiContext api, String method, String path, Object body, String operationId) {
        HttpRequest.Builder request = NativeHttp.request(api.getClient(), path)
                .header("Accept", "application/json");
        try {
            if (body == null) {
                request = "DELETE".equals(method) ? request.DELETE() : request.GET();
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
