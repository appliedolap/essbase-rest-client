package scratch;

import com.appliedolap.essbase.EssApplication;
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
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Proves the claim the cookie strategy rests on: that Essbase accepts a session on its own, with no
 * username or password presented at all.
 * <p>
 * That is what makes external identity providers reachable. A federated user has no password this API can
 * check - their credentials live at the identity provider and never arrive here - so the only way to act as
 * one is to present a session established elsewhere. This test establishes the session with a password
 * because that is what a test can do unattended, then throws the password away and proves the session alone
 * is sufficient. Where the session came from is exactly the part Essbase does not care about, which is the
 * whole point.
 */
@Category(ReadOnlyIntegrationTest.class)
public class SessionCookieAuthenticationIT {

    @Test
    public void aSessionAloneAuthenticatesWithNoPassword() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(System.getProperty("user.home") + "/essbase-test.properties")) {
            properties.load(in);
        }
        String endpoint = properties.getProperty("essbase.endpoint");

        // Establish a session the ordinary way, with a password.
        SessionAuthentication established = new SessionAuthentication(
                properties.getProperty("essbase.username"), properties.getProperty("essbase.password"));
        EssServer withPassword = new EssServerImpl(endpoint, established);
        assertFalse("the warm-up call should have returned applications",
                withPassword.getApplications().isEmpty());
        assertNotNull("the server should have issued a session", established.getSessionId());

        // Now hand only the cookies to a brand new server. No username, no password anywhere - if this
        // works, a session obtained by any other means works too.
        EssAuthentication cookiesOnly = EssAuthentication.sessionCookie(
                established.getSessionId(), established.getWeblogicAuthCookie());
        EssServer withSessionOnly = new EssServerImpl(endpoint, cookiesOnly);

        List<EssApplication> applications = withSessionOnly.getApplications();
        assertFalse("the session alone should have been accepted", applications.isEmpty());
        System.out.println("Authenticated with a session and no password; "
                + applications.size() + " applications visible.");
    }

}
