package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssApplicationConfiguration;

/**
 * One configuration key on an application: the catalogue entry describing it, and the value this
 * application gives it if any.
 */
public class EssApplicationConfigurationImpl implements EssApplicationConfiguration {

    private final EssApplication application;

    private final String key;

    private final String value;

    private final String description;

    private final String syntax;

    private final String example;

    EssApplicationConfigurationImpl(EssApplication application, String key, String value) {
        this(application, key, value, null, null, null);
    }

    EssApplicationConfigurationImpl(EssApplication application, String key, String value,
                                    String description, String syntax, String example) {
        this.application = application;
        this.key = key;
        this.value = value;
        this.description = description;
        this.syntax = syntax;
        this.example = example;
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

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getExample() {
        return example;
    }

    /**
     * Whether the application sets this key - which is exactly whether it has a value here, since this
     * is built by merging the catalogue with the application's configurations. See the interface note
     * for why the server's own {@code configured} field is not consulted.
     */
    @Override
    public boolean isConfigured() {
        return value != null;
    }

    @Override
    public EssApplicationConfiguration set(String value) {
        return application.configuration().set(key, value);
    }

    @Override
    public void delete() {
        application.configuration().delete(key);
    }

    @Override
    public String toString() {
        return isConfigured() ? key + " = " + value : key;
    }

}
