package scratch;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssServer;
import org.junit.Before;

public abstract class AbstractEssbaseServerTest {

    protected EssServer server;

    @Before
    public void setUp() {
        server = ConnectionUtils.server();
    }

}
