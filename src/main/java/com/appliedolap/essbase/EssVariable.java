package com.appliedolap.essbase;

/**
 * A substitution variable, at whichever of the three scopes it is defined.
 *
 * <p>Essbase keeps variables at three levels - server, application, cube - and at <em>evaluation</em>
 * time they cascade: a calculation or query running against a cube sees its own variables, its
 * application's, and the server's, with the nearest definition winning. A cube variable shadows an
 * application one of the same name, which shadows a server one.
 *
 * <p>The REST API does not reflect that. Each list endpoint returns only what is defined at exactly
 * that level: a server variable does not appear in an application's list, and asking for one by name
 * at the wrong level is an error rather than an inherited hit. So the cascade has to be worked out
 * here - see {@link EssCube#getEffectiveVariables()}, which is the only place in this library that
 * answers "what would this cube actually see".
 *
 * @see EssServerVariable
 * @see EssApplicationVariable
 * @see EssCubeVariable
 */
public interface EssVariable extends EssObject {

    /**
     * Where this variable is defined - which is also what it takes precedence over.
     *
     * @return the scope, never null
     */
    Scope getScope();

    @Override
    String getName();

    @Override
    Type getType();

    /**
     * @return the value this variable currently holds
     */
    String getValue();

    /**
     * Changes this variable's value on the server, in place.
     *
     * <p>Separate from creating one: the server refuses to create a variable that already exists at
     * that level ("Variable 'x' already exists in server") and refuses to edit one that doesn't, so
     * the two are not interchangeable.
     *
     * @param value the new value
     */
    void setValue(String value);

    /**
     * Deletes this variable, at its own scope.
     *
     * <p>Only the definition at this variable's own level: deleting a cube variable that shadows an
     * application one uncovers the application one rather than removing both.
     */
    void delete();

    /**
     * Which level a variable is defined at, in cascade order - each shadows the ones before it.
     */
    enum Scope {

        SERVER("Server"),

        APPLICATION("Application"),

        CUBE("Cube");

        private final String label;

        Scope(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /**
         * Whether a variable at this scope takes precedence over one at the given scope. Cube beats
         * application beats server, which is the order the constants are declared in.
         *
         * @param other the scope to compare against
         * @return true if this scope wins
         */
        public boolean shadows(Scope other) {
            return ordinal() > other.ordinal();
        }

    }

}
