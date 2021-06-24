package com.appliedolap.essbase;

public class EssApplicationConfiguration {

    private String key;

    private String value;

    private boolean configured;

    public EssApplicationConfiguration(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isConfigured() {
        return configured;
    }

}