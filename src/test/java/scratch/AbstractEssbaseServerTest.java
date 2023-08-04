package scratch;

import com.appliedolap.essbase.EssServer;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractEssbaseServerTest {

    private static final String DEFAULT_PROPERTIES_FILENAME = "essbase-test.properties";

    private static String endpoint;

    private static String username;

    private static String password;

    protected EssServer server;

    @BeforeClass
    public static void beforeClass() throws Exception {
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
        server = new EssServer(endpoint, username, password);
    }

}