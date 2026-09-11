package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import java.util.Objects;

/**
 * An already-established session, presented as cookies.
 * <p>
 * Sends no {@code Authorization} header at all. That isn't an oversight: Essbase accepts the session
 * cookies on their own, and there is no username or password here to build one from - which is the entire
 * point of this strategy.
 */
public class SessionCookieAuthentication implements EssAuthentication {

    private static final Logger logger = LoggerFactory.getLogger(SessionCookieAuthentication.class);

    private final String cookieHeader;

    // Volatile because one client is shared across threads, and these are written from whichever thread
    // happens to receive a response.
    private volatile Instant sessionExpiry;

    private volatile boolean ended;

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
        return ended ? null : cookieHeader;
    }

    /**
     * Picks up how long this session has left.
     * <p>
     * A session supplied from outside arrives with no expiry attached, but the server reports one on every
     * response, so it can simply be read as requests go by. Worth doing precisely because this strategy has
     * nothing to fall back on: a password-backed session that dies re-authenticates silently, whereas this
     * one just starts failing, so being able to say "12 minutes left" before that happens is the difference
     * between a prompt and a puzzle.
     */
    @Override
    public void observeSetCookies(List<String> setCookieHeaders) {
        SessionCookies.forEachCookie(setCookieHeaders, (name, value) -> {
            if (SessionCookies.SESSION_EXPIRY.equals(name)) {
                Instant parsed = SessionCookies.parseExpiry(value);
                if (parsed != null) {
                    sessionExpiry = parsed;
                }
            }
        });
    }

    @Override
    public Optional<Instant> sessionExpiry() {
        return ended ? Optional.empty() : Optional.ofNullable(sessionExpiry);
    }

    /**
     * Stops presenting the session. There is nothing to fall back to, which is correct - signing off is
     * what the caller asked for - but continuing to send cookies the server has discarded would turn a
     * clear "no credentials" into a confusing "these credentials are rejected".
     */
    @Override
    public void sessionEnded() {
        ended = true;
        sessionExpiry = null;
        logger.debug("Supplied session ended; no credentials remain to present");
    }

}
