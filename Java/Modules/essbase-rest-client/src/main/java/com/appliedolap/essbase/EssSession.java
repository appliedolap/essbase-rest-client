package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.SessionAttributes;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Models a session (connection) on the server.
 */
public class EssSession extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssSession.class);

    private final SessionAttributes sessionAttributes;

    private final Long sessionId;

    EssSession(ApiContext api, SessionAttributes sessionAttributes) {
        super(api);
        this.sessionAttributes = sessionAttributes;
        sessionId = Long.parseLong(sessionAttributes.getSessionId());
    }

    /**
     * Gets the ID of this session.
     *
     * @return the session ID
     */
    public Long getSessionId() {
        return sessionId;
    }

    /**
     * Get the user for the sessino
     * @return
     */
    public String getUserId() {
        return sessionAttributes.getUserId();
    }

    /**
     * Number of seconds session has been created
     *
     * @return the number of seconds the session has been created
     */
    public long getLoginTimeInSeconds() {
        return Long.parseLong(sessionAttributes.getLoginTimeInSeconds());
    }

    /**
     * Get the connection source (not sure what this signifies but it's in the source JSON...)
     *
     * @return the connection source
     */
    public String getConnectionSource() {
        return sessionAttributes.getConnectionSource();
    }

    /**
     * Kill the session.
     *
     * @param logoff true to also log it off (e.g., it'll disappear from the list of sessions)
     */
    public void kill(boolean logoff) {
        logger.info("Killing session {}, logging off: {}", sessionId, logoff);
        WrapperUtil.wrap(() -> api.getSessionsApi().sessionsDeleteSessionWithId(sessionId, logoff));
    }

    /**
     * Standard implementation.
     *
     * @return the name of session (the ID)
     */
    @Override
    public String getName() {
        return sessionAttributes.getSessionId();
    }

}