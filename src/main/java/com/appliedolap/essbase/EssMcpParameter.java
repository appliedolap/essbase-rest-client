package com.appliedolap.essbase;

/**
 * One argument of an {@link EssMcpTool}, read out of the tool's JSON Schema.
 */
public final class EssMcpParameter {

    private final String name;

    private final String type;

    private final String description;

    private final boolean required;

    public EssMcpParameter(String name, String type, String description, boolean required) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    /** The JSON Schema type - string, boolean, integer. */
    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        return name + (required ? " (required)" : "");
    }

}
