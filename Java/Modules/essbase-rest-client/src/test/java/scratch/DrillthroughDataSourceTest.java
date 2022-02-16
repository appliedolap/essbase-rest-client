package scratch;

import com.appliedolap.essbase.EssDrillthrough;
import com.appliedolap.essbase.EssServer;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DrillthroughDataSourceTest {

    public static void main(String[] args) {
        EssServer server = new EssServer("http://docker1:9000/essbase", "admin", "welcome1");
        EssDrillthrough drillthrough = server.getApplication("Sample").getCube("Basic").getDrillthrough("TRANS_NO_PARAMS");

        Map<String, String> pov = new HashMap<>();
        pov.put("Year", "Jan");
        drillthrough.run(pov, System.out);
    }

}