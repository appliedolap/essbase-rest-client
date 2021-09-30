package scratch;

import com.appliedolap.essbase.EssOutline;
import com.appliedolap.essbase.EssServer;

public class BatchOutlineEditTest {

    public static void main(String[] args) {
        EssServer server = new EssServer("http://docker1:9000/essbase", "admin", "welcome1");
        EssOutline outline = server.getApplication("Sample").getCube("Basic").getOutline();


    }

}