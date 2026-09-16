package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * A general, unchecked exception that we can be used to wrap the {@link ApiException} checked exceptions that are all
 * over the generated client code. This class will try and inspect the original exception in order to come up with the
 * best and most informative error message possible, namely by extracting the <code>errorMessage</code> key from the
 * response body in the original exception, if any.
 */
public class EssApiException extends RuntimeException {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public EssApiException(String message) {
        super(message);
    }

    public EssApiException(Throwable throwable) {
        super(getBestMessage(throwable), throwable);
    }

    /**
     * For adding context a caller has and the cause does not - which part of an upload failed, say.
     *
     * <p>Keeps the cause, deliberately. Anything reading these walks the chain to its root for the
     * most specific frame, so throwing away the cause to add a sentence would trade the diagnosis for
     * the commentary.
     *
     * @param message what was being attempted
     * @param throwable what went wrong
     */
    public EssApiException(String message, Throwable throwable) {
        super(message + ": " + getBestMessage(throwable), throwable);
    }

    protected static String getBestMessage(Throwable e) {
        if (e instanceof ApiException) {
            Map<String, String> details = extractMessage(((ApiException) e).getResponseBody());
            String errorMessage = details.get("errorMessage");
            if (errorMessage != null) {
                return errorMessage;
            }
        }
        return e.getMessage();
    }

    protected static Map<String, String> extractMessage(String message) {
        if (message == null || message.isBlank()) {
            return new HashMap<>();
        }
        try {
            Map<String, Object> parsedDetails = OBJECT_MAPPER.readValue(message, new TypeReference<Map<String, Object>>() {});
            if (parsedDetails != null) {
                Map<String, String> details = new HashMap<>();
                for (Map.Entry<String, Object> entry : parsedDetails.entrySet()) {
                    if (entry.getValue() != null) {
                        details.put(entry.getKey(), entry.getValue().toString());
                    }
                }
                return details;
            }
        } catch (Exception e) {
            // Plain-text/non-JSON response bodies are handled below.
        }
        Map<String, String> details = new HashMap<>();
        details.put("errorMessage", message);
        return details;
    }

}