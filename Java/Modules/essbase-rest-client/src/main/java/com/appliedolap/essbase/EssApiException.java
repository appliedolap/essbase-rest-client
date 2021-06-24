package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class EssApiException extends RuntimeException {

    public EssApiException(Throwable throwable) {
        super(getBestMessage(throwable), throwable);
    }

    public static String getBestMessage(Throwable e) {
        if (e instanceof ApiException) {
            Map<String, String> details = extractMessage(((ApiException)e).getResponseBody());
            String errorMessage = details.get("errorMessage");
            if (errorMessage != null) {
                return errorMessage;
            }
        }
        return e.getMessage();
    }

    public static Map<String, String> extractMessage(String message) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(message, Map.class);
        } catch (Exception e) {
            System.err.println("Couldn't extract message");
            Map<String, String> details = new HashMap<>();
            details.put("errorMessage", message);
            return details;
        }
    }

}