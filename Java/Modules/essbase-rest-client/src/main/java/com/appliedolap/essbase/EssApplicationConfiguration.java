package com.appliedolap.essbase;

/**
 * Represents a configuration item (basically speaking, a key/value pair) on an application.
 */
public class EssApplicationConfiguration {

    private final EssApplication application;

    private final String key;

    private final String value;

    EssApplicationConfiguration(EssApplication application, String key, String value) {
        this.application = application;
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the application that this configuration item is associated with.
     *
     * @return the parent application
     */
    public EssApplication getApplication() {
        return application;
    }

    /**
     * Gets the key for this configuration property.
     *
     * @return the configuration key name
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the value for this configuration property.
     *
     * @return the configuration value
     */
    public String getValue() {
        return value;
    }

}