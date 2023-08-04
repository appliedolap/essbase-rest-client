package scratch;

import com.appliedolap.essbase.EssServer;
import org.junit.Test;

public class DrillthroughDataSourceTest extends AbstractEssbaseServerTest {

    @Test
    public void about() {
        EssServer.About about = server.getAbout();
        System.out.println("About: " + about.getVersion());
    }

}