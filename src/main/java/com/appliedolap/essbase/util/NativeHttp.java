package com.appliedolap.essbase.util;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Small bridge for REST endpoints where the native OpenAPI generator drains or cannot model
 * the streaming response/request body. This keeps hand-written calls on the configured
 * {@link ApiClient}, including auth/session interceptors and the custom HttpClient wrapper.
 */
public final class NativeHttp {

    private NativeHttp() {
    }

    public static HttpRequest.Builder request(ApiClient client, String path) {
        return HttpRequest.newBuilder(URI.create(joinBaseAndPath(client.getBaseUri(), path)));
    }

    public static HttpResponse<InputStream> send(ApiClient client, HttpRequest.Builder builder, String operationId)
            throws ApiException, IOException {
        try {
            Duration readTimeout = client.getReadTimeout();
            if (readTimeout != null) {
                builder.timeout(readTimeout);
            }
            if (client.getRequestInterceptor() != null) {
                client.getRequestInterceptor().accept(builder);
            }

            HttpResponse<InputStream> response = client.getHttpClient().send(
                    builder.build(), HttpResponse.BodyHandlers.ofInputStream());

            if (client.getResponseInterceptor() != null) {
                client.getResponseInterceptor().accept(response);
            }
            if (response.statusCode() / 100 != 2) {
                throw apiException(operationId, response);
            }
            return response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
    }

    public static void sendAndDiscard(ApiClient client, HttpRequest.Builder builder, String operationId)
            throws ApiException, IOException {
        HttpResponse<InputStream> response = send(client, builder, operationId);
        if (response.body() != null) {
            try (InputStream body = response.body()) {
                body.transferTo(OutputStream.nullOutputStream());
            }
        }
    }

    public static HttpRequest.BodyPublisher jsonBody(ApiClient client, Object body) throws ApiException {
        try {
            return HttpRequest.BodyPublishers.ofByteArray(client.getObjectMapper().writeValueAsBytes(body));
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    public static void copyBodyTo(HttpResponse<InputStream> response, OutputStream outputStream) throws IOException {
        try (InputStream body = response.body()) {
            body.transferTo(outputStream);
        }
    }

    public static String withQuery(String path, String name, Object value) {
        if (name == null || name.isEmpty() || value == null) {
            return path;
        }
        String separator = path.contains("?") ? "&" : "?";
        return path + separator + ApiClient.urlEncode(name) + "=" + ApiClient.urlEncode(String.valueOf(value));
    }

    public static String encodePathKeepingSlashes(String path) {
        String normalized = trimLeadingSlash(path);
        if (normalized.isEmpty()) {
            return normalized;
        }
        String[] segments = normalized.split("/", -1);
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < segments.length; i++) {
            if (i > 0) {
                encoded.append('/');
            }
            encoded.append(ApiClient.urlEncode(segments[i]));
        }
        return encoded.toString();
    }

    public static String trimLeadingSlash(String path) {
        String normalized = path == null ? "" : path;
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }

    private static ApiException apiException(String operationId, HttpResponse<InputStream> response) throws IOException {
        String responseBody = null;
        if (response.body() != null) {
            try (InputStream body = response.body()) {
                responseBody = new String(body.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        String message = operationId + " call failed with: " + response.statusCode()
                + (responseBody == null || responseBody.isEmpty() ? "" : " - " + responseBody);
        return new ApiException(response.statusCode(), message, response.headers(), responseBody);
    }

    private static String joinBaseAndPath(String baseUri, String path) {
        String normalizedPath = path == null ? "" : path;
        if (normalizedPath.isEmpty()) {
            return baseUri;
        }
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        if (baseUri.endsWith("/")) {
            return baseUri.substring(0, baseUri.length() - 1) + normalizedPath;
        }
        return baseUri + normalizedPath;
    }

}