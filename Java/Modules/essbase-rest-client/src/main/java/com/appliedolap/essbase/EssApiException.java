package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * A general, unchecked exception that we can be used to wrap the {@link ApiException} checked exceptions that are all
 * over the generated client code. This class will try and inspect the original exception in order to come up with the
 * best and most informative error message possible (namely by extracting the <code>errorMessage</code> key from the
 * response body in the original exception, if any.
 */
public class EssApiException extends RuntimeException {

    public EssApiException(Throwable throwable) {
        super(getBestMessage(throwable), throwable);
    }

    protected static String getBestMessage(Throwable e) {
        if (e instanceof ApiException) {
            Map<String, String> details = extractMessage(((ApiException)e).getResponseBody());
            String errorMessage = details.get("errorMessage");
            if (errorMessage != null) {
                return errorMessage;
            }
        }
        return e.getMessage();
    }

    protected static Map extractMessage(String message) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(message, Map.class);
        } catch (Exception e) {
            Map<String, String> details = new HashMap<>();
            details.put("errorMessage", message);
            return details;
        }
    }

}