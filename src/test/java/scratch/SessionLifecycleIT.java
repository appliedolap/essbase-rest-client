package scratch;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssAuthentication;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.impl.EssServerImpl;
import com.appliedolap.essbase.impl.SessionAuthentication;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Signing off, proven end to end: the session really stops working afterwards.
 * <p>
 * Categorised read-only because the only server state it ends is a session it created itself moments
 * earlier. It touches no application, cube or file, and cannot affect another client's session.
 */
@Category(ReadOnlyIntegrationTest.class)
public class SessionLifecycleIT {

    private Properties properties() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(System.getProperty("user.home") + "/essbase-test.properties")) {
            properties.load(in);
        }
        return properties;
    }

    @Test
    public void signingOffInvalidatesTheSessionAndTheClientRecovers() throws IOException {
        Properties properties = properties();
        String endpoint = properties.getProperty("essbase.endpoint");

        SessionAuthentication established = new SessionAuthentication(
                properties.getProperty("essbase.username"), properties.getProperty("essbase.password"));
        EssServer server = new EssServerImpl(endpoint, established);
        assertFalse(server.getApplications().isEmpty());
        String sessionId = established.getSessionId();
        assertNotNull("a session should have been established", sessionId);

        // The server tells us when this session dies; make sure that reached us rather than only a log line.
        Instant expiry = server.getSessionExpiry().orElseThrow(
                () -> new AssertionError("the session expiry should have been captured"));
        assertTrue("expiry should be in the future, was " + expiry, expiry.isAfter(Instant.now()));
        System.out.println("Session expires at " + expiry);

        // A second client holding the same session, to observe the sign-off from outside.
        EssServer sameSession = new EssServerImpl(endpoint,
                EssAuthentication.sessionCookie(sessionId, established.getWeblogicAuthCookie()));
        assertFalse("the shared session should work before sign-off", sameSession.getApplications().isEmpty());

        server.signOff();

        // The session is genuinely gone server-side, not merely forgotten locally - which is why this is
        // asserted through the second client, that still presents the very same cookies.
        try {
            sameSession.getApplications();
            fail("the session should have stopped working after signing off");
        } catch (EssApiException expected) {
            System.out.println("Signed-off session correctly rejected: " + expected.getMessage());
        }

        // The original client still has a username and password, so it simply authenticates again.
        assertFalse("should transparently re-authenticate after signing off",
                server.getApplications().isEmpty());
        assertNotNull("a fresh session should have been established", established.getSessionId());
        System.out.println("Re-authenticated after sign-off with a new session.");
    }

}
