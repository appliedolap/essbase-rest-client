package scratch;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssDrillthrough;
import com.appliedolap.essbase.EssServer;
import org.junit.Test;

import java.util.Arrays;

public class DrillthroughDataSourceTest extends AbstractEssbaseServerTest {

    @Test
    public void list() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        cube.getDrillthroughs().forEach(d -> System.out.println(d.getName()));
    }

    @Test
    public void deleteDrillthrough() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        EssDrillthrough drillthrough = cube.getDrillthrough("My%20Name");
        drillthrough.delete();
    }

    @Test
    public void createDrillthrough() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        EssDrillthrough drillthrough = cube.createDrillthroughURL("Some_Report", "http://foo", Arrays.asList("Actual"));
        drillthrough.setUrl("http://www.google.com");
        //drillthrough.setDrillableRegions(Arrays.asList("Actual"));
        drillthrough.save();
    }

}