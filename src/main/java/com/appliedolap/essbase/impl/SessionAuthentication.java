package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpCookie;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Username and password until the server hands back a session, then the session from then on.
 * <p>
 * This is what the library has always done outside "stateless" mode, and it is worth keeping rather than
 * always sending Basic: Essbase creates a server-side session per authenticated request, so re-presenting
 * the password on every call leaves a trail of sessions behind.
 */
public class SessionAuthentication implements EssAuthentication {

    private static final Logger logger = LoggerFactory.getLogger(SessionAuthentication.class);

    private final BasicAuthentication basic;

    // Volatile because one client is shared across threads: the request that establishes the session and
    // the requests that then use it are routinely not the same thread.
    private volatile String sessionId;

    private volatile String weblogicAuthCookie;

    private volatile Instant sessionExpiry;

    public SessionAuthentication(String username, String password) {
        this.basic = new BasicAuthentication(username, password);
    }

    @Override
    public Optional<String> username() {
        return basic.username();
    }

    @Override
    public String authorizationHeader() {
        // "Session Session" is what this library has always sent once authenticated. Measured against
        // Essbase 21.7, the cookies alone are sufficient and this header is ignored - but it is harmless,
        // and removing it is a behaviour change that deserves its own test against a live server rather
        // than being smuggled into a refactor.
        return sessionId == null ? basic.authorizationHeader() : "Session Session";
    }

    @Override
    public String cookieHeader() {
        return sessionId == null ? null : SessionCookies.header(sessionId, weblogicAuthCookie);
    }

    @Override
    public void observeSetCookies(List<String> setCookieHeaders) {
        for (String header : setCookieHeaders) {
            List<HttpCookie> cookies;
            try {
                cookies = HttpCookie.parse(header);
            } catch (IllegalArgumentException e) {
                logger.debug("Skipping unparseable Set-Cookie header '{}': {}", header, e.getMessage());
                continue;
            }
            for (HttpCookie cookie : cookies) {
                accept(cookie.getName(), cookie.getValue());
            }
        }
    }

    private void accept(String name, String value) {
        if (SessionCookies.SESSION_EXPIRY.equals(name)) {
            try {
                // Kept, not merely logged: a caller that knows when its session dies can renew or warn
                // ahead of time instead of finding out through a surprise 401 mid-operation.
                sessionExpiry = Instant.ofEpochMilli(Long.parseLong(value));
                logger.debug("Session expires in {}s",
                        (sessionExpiry.toEpochMilli() - System.currentTimeMillis()) / 1000.0f);
            } catch (NumberFormatException e) {
                logger.debug("Could not parse sessionExpiry='{}'", value);
            }
        } else if (SessionCookies.JSESSION.equals(name)) {
            // Only the first one. Essbase re-issues JSESSIONID on later responses, and adopting a newer
            // value mid-flight would switch sessions underneath in-flight requests.
            if (sessionId == null) {
                sessionId = value;
                logger.debug("Setting session ID: {}", value);
            }
        } else if (SessionCookies.WL_JSESSION.equals(name)) {
            weblogicAuthCookie = value;
            logger.debug("Have WL session");
        }
    }

    @Override
    public Optional<Instant> sessionExpiry() {
        // Gated on there being a session. Essbase sends sessionExpiry on responses before one exists -
        // observed arriving 43 hours in the past on the very first exchange - and reporting that as "your
        // session expired two days ago" to a caller that has no session at all is worse than saying
        // nothing. Once a session is established the value tracks it correctly, sliding forward as the
        // server extends it on use.
        return sessionId == null ? Optional.empty() : Optional.ofNullable(sessionExpiry);
    }

    /**
     * Forgets the session, falling back to the username and password. The next request authenticates with
     * them and the server issues a fresh session, so signing off and carrying on works rather than leaving
     * the client wedged presenting a session the server has already discarded.
     */
    @Override
    public void sessionEnded() {
        sessionId = null;
        weblogicAuthCookie = null;
        sessionExpiry = null;
        logger.debug("Session ended; will re-authenticate on the next request");
    }

    /** The session this has established, or null if it is still authenticating with a password. */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * The WebLogic authentication cookie for the established session, or null.
     * <p>
     * Exposed alongside {@link #getSessionId()} so a session established here can be handed to
     * {@link com.appliedolap.essbase.EssAuthentication#sessionCookie} - the same handoff a caller performs
     * when the session came from somewhere else entirely, such as a browser sign-in.
     */
    public String getWeblogicAuthCookie() {
        return weblogicAuthCookie;
    }

}
