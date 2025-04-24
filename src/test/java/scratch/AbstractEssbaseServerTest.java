package scratch;

import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.impl.EssServerImpl;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static com.appliedolap.essbase.ApiClientFactory.ESSBASE_NETWORK_LOGGING;

public abstract class AbstractEssbaseServerTest {

    private static final String DEFAULT_PROPERTIES_FILENAME = "essbase-test.properties";

    private static String endpoint;

    private static String username;

    private static String password;

    protected EssServer server;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty(ESSBASE_NETWORK_LOGGING, "true");

        Properties properties = new Properties();
        try (InputStream fis = new FileInputStream(System.getProperty("user.home") + "/" + DEFAULT_PROPERTIES_FILENAME)) {
            properties.load(fis);
            endpoint = properties.getProperty("essbase.endpoint");
            username = properties.getProperty("essbase.username");
            password = properties.getProperty("essbase.password");
        }
    }

    @Before
    public void setUp() throws Exception {
        server = new EssServerImpl(endpoint, username, password);
    }

}