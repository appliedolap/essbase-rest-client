package com.appliedolap.essbase;

import java.util.Collections;
import java.util.List;

/**
 * One variable name as a cube actually sees it: the definition that wins, and the ones it hides.
 *
 * <p>This is the view the REST API will not give you. Essbase resolves variables at evaluation time -
 * a query against a cube sees cube, application and server definitions with the nearest winning - but
 * every list endpoint reports only its own level, so nothing on the server will tell you that
 * {@code &curmonth} resolves here to the application's value rather than the server's.
 *
 * <p>Verified against a live server: with {@code shared} defined as Jan on the server, Feb on the
 * application and Mar on the cube, a query returned Mar; deleting the cube definition returned Feb,
 * and deleting the application's returned Jan.
 */
public final class EssEffectiveVariable {

    private final EssVariable effective;

    private final List<EssVariable> shadowed;

    public EssEffectiveVariable(EssVariable effective, List<EssVariable> shadowed) {
        this.effective = effective;
        this.shadowed = Collections.unmodifiableList(shadowed);
    }

    /**
     * @return the definition that actually applies
     */
    public EssVariable getEffective() {
        return effective;
    }

    /**
     * The definitions this one hides, furthest away first - so a cube variable shadowing both an
     * application and a server one lists the server's first.
     *
     * @return the hidden definitions, empty when this name is defined at only one level
     */
    public List<EssVariable> getShadowed() {
        return shadowed;
    }

    /**
     * @return whether this name is defined at more than one level
     */
    public boolean isOverriding() {
        return !shadowed.isEmpty();
    }

    public String getName() {
        return effective.getName();
    }

    public String getValue() {
        return effective.getValue();
    }

    /**
     * @return the scope the winning definition lives at
     */
    public EssVariable.Scope getScope() {
        return effective.getScope();
    }

    @Override
    public String toString() {
        return getName() + "=" + getValue() + " (" + getScope().getLabel() + ")";
    }

}
