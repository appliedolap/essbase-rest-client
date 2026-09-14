package com.appliedolap.essbase;

import java.util.Collections;
import java.util.List;

/**
 * One tool the server's built-in MCP server offers, as it describes itself.
 */
public final class EssMcpTool {

    private final String name;

    private final String description;

    private final List<EssMcpParameter> parameters;

    public EssMcpTool(String name, String description, List<EssMcpParameter> parameters) {
        this.name = name;
        this.description = description;
        this.parameters = Collections.unmodifiableList(parameters);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /** Its arguments, required ones first. */
    public List<EssMcpParameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return name;
    }

}
