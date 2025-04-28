package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssSession;
import com.appliedolap.essbase.client.model.SessionAttributes;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Models a session (connection) on the server.
 */
public class EssSessionImpl extends AbstractEssObject implements EssSession {

    private static final Logger logger = LoggerFactory.getLogger(EssSessionImpl.class);

    private final SessionAttributes sessionAttributes;

    private final Long sessionId;

    public EssSessionImpl(ApiContext api, SessionAttributes sessionAttributes) {
        super(api);
        this.sessionAttributes = sessionAttributes;
        sessionId = Long.parseLong(sessionAttributes.getSessionId());
    }

    @Override
    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public String getUserId() {
        return sessionAttributes.getUserId();
    }

    @Override
    public long getLoginTimeInSeconds() {
        return Long.parseLong(sessionAttributes.getLoginTimeInSeconds());
    }

    @Override
    public String getConnectionSource() {
        return sessionAttributes.getConnectionSource();
    }

    @Override
    public void kill(boolean logoff) {
        logger.info("Killing session {}, logging off: {}", sessionId, logoff);
        WrapperUtil.wrap(() -> api.getSessionsApi().sessionsDeleteSessionWithId(sessionId, logoff));
    }

    @Override
    public String getName() {
        return sessionAttributes.getSessionId();
    }

    @Override
    public Type getType() {
        return Type.SESSION;
    }

}