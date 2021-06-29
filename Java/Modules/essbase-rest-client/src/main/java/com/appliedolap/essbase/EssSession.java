package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.SessionAttributes;

/**
 * Models a session (connection) on the server.
 */
public class EssSession extends EssObject {

    private final EssCube cube;

    private final SessionAttributes sessionAttributes;

    EssSession(ApiContext api, EssCube cube, SessionAttributes sessionAttributes) {
        super(api);
        this.cube = cube;
        this.sessionAttributes = sessionAttributes;
    }

    /**
     * Gets the ID of this session.
     *
     * @return the session ID
     */
    public String getSessionId() {
        return sessionAttributes.getSessionId();
    }

//    public void delete() {
//
//    }

    @Override
    public String getName() {
        return sessionAttributes.getSessionId();
    }

}