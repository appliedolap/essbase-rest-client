package com.appliedolap.essbase;

/**
 * A substitution variable defined on one cube, which shadows an application or server variable of the
 * same name for queries and calculations against that cube.
 *
 * <p>Deliberately not a subtype of {@link EssApplicationVariable}, though it does know its owning
 * application. The two are siblings: a cube variable is not an application variable, and typing it as
 * one would let it be handed to anything expecting {@code EssApplication.getVariables()} - a list
 * whose members can all be deleted from the application, which this one cannot.
 */
public interface EssCubeVariable extends EssVariable {

    /**
     * @return the cube this variable is defined on
     */
    EssCube getCube();

    /**
     * The application owning the cube - not where this variable lives, but often what you want next.
     *
     * @return the owning application
     */
    EssApplication getApplication();

}
