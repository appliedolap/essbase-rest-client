package com.appliedolap.essbase;

import java.util.Collections;
import java.util.List;

/**
 * A named family of MDX functions - the server's own grouping, not one imposed here.
 */
public final class EssMdxFunctionGroup {

    private final String name;

    private final List<EssMdxFunction> functions;

    public EssMdxFunctionGroup(String name, List<EssMdxFunction> functions) {
        this.name = name;
        this.functions = Collections.unmodifiableList(functions);
    }

    /** The group name, e.g. {@code Member}. */
    public String getName() {
        return name;
    }

    /** The functions in it, in the order the server listed them. */
    public List<EssMdxFunction> getFunctions() {
        return functions;
    }

    @Override
    public String toString() {
        return name;
    }

}
