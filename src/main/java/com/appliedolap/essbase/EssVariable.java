package com.appliedolap.essbase;

public interface EssVariable extends EssObject {

    /**
     * Gets the scope of this variable.
     *
     * @return the variable scope
     */
    Scope getScope();

    /**
     * Gets the name of this variable.
     *
     * @return the name of this variable
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the value of this variable.
     *
     * @return the value of this variable
     */
    String getValue();

    /**
     * Deletes the variable.
     */
    void delete();

    /**
     * Models variable scope.
     */
    enum Scope {

        SERVER, APPLICATION, CUBE

    }

}