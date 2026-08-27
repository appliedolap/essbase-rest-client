package scratch;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssDrillthrough;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;

public class DrillthroughDataSourceIT extends AbstractEssbaseServerTest {

    @Test
    @Category(ReadOnlyIntegrationTest.class)
    public void list() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        cube.getDrillthroughs().forEach(d -> System.out.println(d.getName()));
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void deleteDrillthrough() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        EssDrillthrough drillthrough = cube.getDrillthrough("My%20Name");
        drillthrough.delete();
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void createDrillthrough() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        EssDrillthrough drillthrough = cube.createDrillthroughURL("Some_Report", "http://foo", Arrays.asList("Actual"));
        drillthrough.setUrl("http://www.google.com");
        drillthrough.save();
    }

}