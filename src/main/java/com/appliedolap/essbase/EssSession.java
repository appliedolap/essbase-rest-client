package com.appliedolap.essbase;

public interface EssSession extends EssObject {

    /**
     * Gets the ID of this session.
     *
     * @return the session ID
     */
    Long getSessionId();

    /**
     * Get the user for the session.
     *
     * @return the user ID
     */
    String getUserId();

    /**
     * Number of seconds session has been created
     *
     * @return the number of seconds the session has been created
     */
    long getLoginTimeInSeconds();

    /**
     * Get the connection source (not sure what this signifies, but it's in the source JSON...)
     *
     * @return the connection source
     */
    String getConnectionSource();

    /**
     * Kill the session.
     *
     * @param logoff true to also log it off (e.g., it'll disappear from the list of sessions)
     */
    void kill(boolean logoff);

    /**
     * Standard implementation.
     *
     * @return the name of session (the ID)
     */
    @Override
    String getName();

    @Override
    Type getType();

}