package com.appliedolap.essbase;

/**
 * One MDX function the server understands, as the server itself describes it.
 *
 * <p>A bean with nothing but accessors on purpose: this is reference material rather than anything
 * addressable, so there is no server object behind it to act on. That shape also means a caller can
 * hand it straight to a generic property table without a dedicated view.
 */
public final class EssMdxFunction {

    private final String group;

    private final String name;

    private final String syntax;

    private final String comment;

    public EssMdxFunction(String group, String name, String syntax, String comment) {
        this.group = group;
        this.name = name;
        this.syntax = syntax;
        this.comment = comment;
    }

    /** The group the server files this function under - Member, Set, Tuple, Number and so on. */
    public String getGroup() {
        return group;
    }

    /** The function name, e.g. {@code Ancestor}. */
    public String getName() {
        return name;
    }

    /**
     * How to call it, e.g. {@code Ancestor ( member , layer | index [, hierarchy ] )}, with optional
     * parts in square brackets and alternatives separated by a pipe.
     */
    public String getSyntax() {
        return syntax;
    }

    /** A sentence on what it does. */
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return name;
    }

}
