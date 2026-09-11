package com.appliedolap.essbase.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpCookie;
import java.time.Instant;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * The cookie names Essbase's session is carried in, and how to format them into a {@code Cookie} header.
 * <p>
 * Shared so that the strategy which <em>captures</em> a session and the one which is <em>handed</em> one
 * format it identically - if those two ever disagreed, a session established by this library would work and
 * the same session supplied from outside would not, which is a miserable thing to debug.
 */
final class SessionCookies {

    private static final Logger logger = LoggerFactory.getLogger(SessionCookies.class);

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
    /**
     * Parses every cookie out of a set of {@code Set-Cookie} header values, handing each name and value to
     * {@code consumer}. Unparseable headers are skipped rather than thrown: a malformed cookie from the
     * server is not a reason to fail the request it arrived on.
     */
    static void forEachCookie(List<String> setCookieHeaders, BiConsumer<String, String> consumer) {
        for (String header : setCookieHeaders) {
            List<HttpCookie> cookies;
            try {
                cookies = HttpCookie.parse(header);
            } catch (IllegalArgumentException e) {
                logger.debug("Skipping unparseable Set-Cookie header '{}': {}", header, e.getMessage());
                continue;
            }
            for (HttpCookie cookie : cookies) {
                consumer.accept(cookie.getName(), cookie.getValue());
            }
        }
    }

    /** The {@code sessionExpiry} cookie's value as an instant, or null if it isn't a number. */
    static Instant parseExpiry(String value) {
        try {
            return Instant.ofEpochMilli(Long.parseLong(value));
        } catch (NumberFormatException e) {
            logger.debug("Could not parse sessionExpiry='{}'", value);
            return null;
        }
    }

    static String header(String sessionId, String weblogicAuthCookie) {
        StringBuilder header = new StringBuilder("$Version=1;").append(JSESSION).append('=').append(sessionId);
        if (weblogicAuthCookie != null) {
            header.append(",$Version=1;").append(WL_JSESSION).append('=').append(weblogicAuthCookie);
        }
        return header.toString();
    }

}
