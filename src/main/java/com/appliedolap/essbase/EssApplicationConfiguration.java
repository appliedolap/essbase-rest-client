package com.appliedolap.essbase;

public interface EssApplicationConfiguration {

    /**
     * Gets the application that this configuration item is associated with.
     *
     * @return the parent application
     */
    EssApplication getApplication();

    /**
     * Gets the key for this configuration property.
     *
     * @return the configuration key name
     */
    String getKey();

    /**
     * Gets the value for this configuration property.
     *
     * @return the configuration value
     */
    String getValue();

}