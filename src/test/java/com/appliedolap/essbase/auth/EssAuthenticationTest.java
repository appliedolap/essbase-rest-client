package com.appliedolap.essbase.auth;

import com.appliedolap.essbase.EssAuthentication;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Offline. Every assertion here is about which headers a strategy produces, which needs no server - so
 * unlike the {@code *IT} tests this runs in CI and on any machine.
 */
public class EssAuthenticationTest {

    private static final String JSESSION = "Set-Cookie: JSESSIONID=abc123; path=/; SameSite=Strict; HttpOnly";

    private static final String WL_COOKIE = "_WL_AUTHCOOKIE_JSESSIONID=wl789; path=/; SameSite=Strict; secure; HttpOnly";

    private static String setCookie(String name, String value) {
        return name + "=" + value + "; path=/; SameSite=Strict; HttpOnly";
    }

    @Test
    public void basicSendsCredentialsOnEveryRequest() {
        EssAuthentication auth = EssAuthentication.basic("admin", "welcome1");
        String expected = "Basic " + Base64.getEncoder()
                .encodeToString("admin:welcome1".getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, auth.authorizationHeader());
        assertNull("basic auth carries no cookie", auth.cookieHeader());
    }

    /** Basic must not start using a session just because the server offered one. */
    @Test
    public void basicIgnoresASessionTheServerOffers() {
        EssAuthentication auth = EssAuthentication.basic("admin", "welcome1");
        String before = auth.authorizationHeader();
        auth.observeSetCookies(List.of(setCookie("JSESSIONID", "abc123")));
        assertEquals(before, auth.authorizationHeader());
        assertNull(auth.cookieHeader());
    }

    @Test
    public void sessionStartsWithBasicThenSwitchesToTheSession() {
        EssAuthentication auth = EssAuthentication.session("admin", "welcome1");
        assertTrue("before any response, must authenticate with the password",
                auth.authorizationHeader().startsWith("Basic "));
        assertNull(auth.cookieHeader());

        auth.observeSetCookies(List.of(setCookie("JSESSIONID", "abc123"), WL_COOKIE));

        assertEquals("Session Session", auth.authorizationHeader());
        assertNotNull(auth.cookieHeader());
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("JSESSIONID=abc123"));
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("_WL_AUTHCOOKIE_JSESSIONID=wl789"));
    }

    /**
     * Essbase re-issues JSESSIONID on later responses. Adopting a newer one would switch sessions
     * underneath requests already in flight, so the first one wins.
     */
    @Test
    public void sessionKeepsTheFirstSessionIdItWasGiven() {
        EssAuthentication auth = EssAuthentication.session("admin", "welcome1");
        auth.observeSetCookies(List.of(setCookie("JSESSIONID", "first")));
        auth.observeSetCookies(List.of(setCookie("JSESSIONID", "second")));
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("JSESSIONID=first"));
    }

    /** A malformed Set-Cookie must not take the client down. */
    @Test
    public void sessionSurvivesAnUnparseableSetCookie() {
        EssAuthentication auth = EssAuthentication.session("admin", "welcome1");
        auth.observeSetCookies(List.of("=====not a cookie====="));
        assertTrue(auth.authorizationHeader().startsWith("Basic "));
    }

    @Test
    public void sessionIgnoresUnrelatedCookies() {
        EssAuthentication auth = EssAuthentication.session("admin", "welcome1");
        auth.observeSetCookies(List.of(setCookie("sessionExpiry", "1789000000000"),
                setCookie("SomethingElse", "x")));
        assertTrue("neither cookie establishes a session", auth.authorizationHeader().startsWith("Basic "));
        assertNull(auth.cookieHeader());
    }

    /**
     * The point of the whole exercise: a session presented from outside, with no password anywhere, and
     * deliberately no Authorization header - Essbase accepts the cookies alone.
     */
    @Test
    public void sessionCookieSendsCookiesAndNoAuthorizationHeader() {
        EssAuthentication auth = EssAuthentication.sessionCookie("abc123", "wl789");
        assertNull("a cookie-borne session needs no Authorization header", auth.authorizationHeader());
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("JSESSIONID=abc123"));
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("_WL_AUTHCOOKIE_JSESSIONID=wl789"));
    }

    @Test
    public void sessionCookieToleratesAMissingWeblogicCookie() {
        EssAuthentication auth = EssAuthentication.sessionCookie("abc123", null);
        assertTrue(auth.cookieHeader(), auth.cookieHeader().contains("JSESSIONID=abc123"));
        assertTrue("no empty trailing cookie", !auth.cookieHeader().contains("_WL_AUTHCOOKIE_JSESSIONID="));
    }

    /**
     * A session captured by this library and the same session handed back to it must produce byte-identical
     * headers; if they ever diverged, a session would work one way and fail the other.
     */
    @Test
    public void aCapturedSessionAndASuppliedOneFormatIdentically() {
        EssAuthentication captured = EssAuthentication.session("admin", "welcome1");
        captured.observeSetCookies(List.of(setCookie("JSESSIONID", "abc123"), WL_COOKIE));
        EssAuthentication supplied = EssAuthentication.sessionCookie("abc123", "wl789");
        assertEquals(captured.cookieHeader(), supplied.cookieHeader());
    }

    @Test
    public void bearerTokenSendsTheToken() {
        EssAuthentication auth = EssAuthentication.bearerToken("abc.def.ghi");
        assertEquals("Bearer abc.def.ghi", auth.authorizationHeader());
        assertNull(auth.cookieHeader());
    }

}
