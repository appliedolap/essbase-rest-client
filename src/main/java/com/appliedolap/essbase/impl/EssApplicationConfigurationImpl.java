package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssApplicationConfiguration;

/**
 * Represents a configuration item (basically speaking, a key/value pair) on an application.
 */
public class EssApplicationConfigurationImpl implements EssApplicationConfiguration {

    private final EssApplication application;

    private final String key;

    private final String value;

    EssApplicationConfigurationImpl(EssApplication application, String key, String value) {
        this.application = application;
        this.key = key;
        this.value = value;
    }

    @Override
    public EssApplication getApplication() {
        return application;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

}