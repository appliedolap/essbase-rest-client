package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAuthentication;

import java.util.Objects;

/**
 * An already-established session, presented as cookies.
 * <p>
 * Sends no {@code Authorization} header at all. That isn't an oversight: Essbase accepts the session
 * cookies on their own, and there is no username or password here to build one from - which is the entire
 * point of this strategy.
 */
public class SessionCookieAuthentication implements EssAuthentication {

    private final String cookieHeader;

    /**
     * @param sessionId the {@code JSESSIONID} value
     * @param weblogicAuthCookie the {@code _WL_AUTHCOOKIE_JSESSIONID} value, or null if there isn't one
     */
    public SessionCookieAuthentication(String sessionId, String weblogicAuthCookie) {
        Objects.requireNonNull(sessionId, "sessionId");
        this.cookieHeader = SessionCookies.header(sessionId, weblogicAuthCookie);
    }

    @Override
    public String authorizationHeader() {
        return null;
    }

    @Override
    public String cookieHeader() {
        return cookieHeader;
    }

}
