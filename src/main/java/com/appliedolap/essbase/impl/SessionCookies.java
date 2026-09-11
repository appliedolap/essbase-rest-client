package com.appliedolap.essbase.impl;

/**
 * The cookie names Essbase's session is carried in, and how to format them into a {@code Cookie} header.
 * <p>
 * Shared so that the strategy which <em>captures</em> a session and the one which is <em>handed</em> one
 * format it identically - if those two ever disagreed, a session established by this library would work and
 * the same session supplied from outside would not, which is a miserable thing to debug.
 */
final class SessionCookies {

    static final String JSESSION = "JSESSIONID";

    static final String WL_JSESSION = "_WL_AUTHCOOKIE_JSESSIONID";

    static final String SESSION_EXPIRY = "sessionExpiry";

    private SessionCookies() {
    }

    /**
     * Builds the {@code Cookie} header. The {@code $Version=1;} prefixes and the comma separator are
     * RFC 2965 form rather than the more familiar {@code a=b; c=d} - preserved exactly as this library has
     * always sent it, because it is known to work against a real server and cookie header parsing is not
     * somewhere to make untested changes.
     *
     * @param weblogicAuthCookie may be null, in which case only the session id is sent
     */
    static String header(String sessionId, String weblogicAuthCookie) {
        StringBuilder header = new StringBuilder("$Version=1;").append(JSESSION).append('=').append(sessionId);
        if (weblogicAuthCookie != null) {
            header.append(",$Version=1;").append(WL_JSESSION).append('=').append(weblogicAuthCookie);
        }
        return header.toString();
    }

}
