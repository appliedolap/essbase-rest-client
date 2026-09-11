package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.BasicAuthentication;
import com.appliedolap.essbase.impl.BearerTokenAuthentication;
import com.appliedolap.essbase.impl.SessionAuthentication;
import com.appliedolap.essbase.impl.SessionCookieAuthentication;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * How a client proves who it is to Essbase.
 * <p>
 * Essbase's REST API accepts more than one answer to that question. A username and password over HTTP Basic
 * is the obvious one, but the API also honours a plain session - once a request has been authenticated, the
 * {@code JSESSIONID} and {@code _WL_AUTHCOOKIE_JSESSIONID} cookies it returns are sufficient on their own,
 * with no {@code Authorization} header at all (verified against Essbase 21.7). That matters well beyond
 * tidiness: a deployment fronted by an external identity provider may have no password this API can check,
 * because a federated user's credentials live at the identity provider and never reach Essbase. Being able
 * to present a session that was established some other way is the difference between supporting such a
 * deployment and not.
 * <p>
 * Implementations may be consulted from several threads, since one client is shared, and must be safe for
 * that.
 */
public interface EssAuthentication {

    /**
     * The value for the {@code Authorization} header, or null to send none. Null is meaningful rather than
     * a degenerate case - a cookie-borne session needs no {@code Authorization} header.
     */
    String authorizationHeader();

    /** The value for the {@code Cookie} header, or null to send none. */
    default String cookieHeader() {
        return null;
    }

    /**
     * Offers the {@code Set-Cookie} headers from a response, so an implementation that establishes a
     * session as it goes can pick one up. Called for every response; most implementations ignore it.
     * <p>
     * Takes the raw header values rather than the response so that the decision this makes - which is the
     * subtle part - can be tested without a server or an {@code HttpResponse}.
     *
     * @param setCookieHeaders every {@code Set-Cookie} header value on the response, possibly empty
     */
    default void observeSetCookies(List<String> setCookieHeaders) {
    }

    /**
     * Told that the session this was using has been ended server-side, so that any session state held here
     * is discarded rather than being presented again after it has stopped being valid.
     * <p>
     * What that leaves behind depends on the strategy. One holding a username and password falls back to
     * them and will establish a fresh session on the next call; one that only ever had a session has
     * nothing to fall back to, and subsequent calls will be rejected - correctly, because signing off is
     * exactly what the caller asked for.
     */
    default void sessionEnded() {
    }

    /**
     * When the current session expires, if that is known. Essbase reports it in a {@code sessionExpiry}
     * cookie alongside the session itself, so it is only ever known for a session this library established.
     */
    default Optional<Instant> sessionExpiry() {
        return Optional.empty();
    }

    /**
     * Username and password on every request, with no session. Corresponds to what this library has always
     * called "stateless".
     */
    static EssAuthentication basic(String username, String password) {
        return new BasicAuthentication(username, password);
    }

    /**
     * Username and password until the server returns a session, then the session thereafter. The default,
     * and what this library has always done in its normal (non-stateless) mode.
     */
    static EssAuthentication session(String username, String password) {
        return new SessionAuthentication(username, password);
    }

    /**
     * An existing session, presented as cookies, with no username or password anywhere.
     * <p>
     * For a session established outside this library - most obviously by a user signing in through an
     * external identity provider, where there is no password for Essbase to check.
     *
     * @param sessionId the {@code JSESSIONID} value
     * @param weblogicAuthCookie the {@code _WL_AUTHCOOKIE_JSESSIONID} value, or null if there isn't one
     */
    static EssAuthentication sessionCookie(String sessionId, String weblogicAuthCookie) {
        return new SessionCookieAuthentication(sessionId, weblogicAuthCookie);
    }

    /**
     * A bearer token, sent as {@code Authorization: Bearer <token>}.
     * <p>
     * Provided for deployments whose REST endpoint accepts an OAuth token from an identity provider.
     * Whether a given Essbase accepts one is a property of that deployment: an on-premises 21.7 instance
     * rejects bearer tokens outright, while an Oracle Analytics Cloud instance fronted by IDCS may not.
     * Test against the server in question before relying on it.
     */
    static EssAuthentication bearerToken(String token) {
        return new BearerTokenAuthentication(token);
    }

}
