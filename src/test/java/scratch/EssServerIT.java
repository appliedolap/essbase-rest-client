package scratch;

import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssFile;
import com.appliedolap.essbase.EssJob;
import com.appliedolap.essbase.EssOutline;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EssServerIT extends AbstractEssbaseServerTest {

    private static final Logger logger = LoggerFactory.getLogger(EssServerIT.class);

    @Test
    @Category(ReadOnlyIntegrationTest.class)
    public void getFile() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void createApplicationFromWorkbook() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
        EssJob job = server.createApplicationFromWorkbook("Sample", "Basic", sampleBasic);
        System.out.println("Desc: " + job.getDescription());
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void createApplicationFromWorkbookWithWait() throws InterruptedException {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
        EssJob job = server.createApplicationFromWorkbook("Sample", "Basic", sampleBasic);
        EssJob finished = job.waitForCompletion();

        logger.info("Job finished in {} seconds", TimeUnit.MILLISECONDS.toSeconds(finished.getDuration()));

        System.out.println("Desc: " + job.getDescription());
    }

    @Test
    @Category(ReadOnlyIntegrationTest.class)
    public void outline() {
        EssCube cube = server.getApplication("Sample").getCube("Basic");
        EssOutline outline = cube.getOutline();
        byte[] xml = outline.downloadXml();
        System.out.println("XML: " + new String(xml));
    }

    /**
     * Exports every cube on the server through one connection, which is what used to break: the
     * export left the cube it had exported active on the session, so the first one succeeded and
     * every later one was refused with "Cannot set active cube ... already active on cube ...".
     */
    @Test
    @Category(ReadOnlyIntegrationTest.class)
    public void exportsEveryOutlineOverOneConnection() {
        List<EssCube> cubes = new ArrayList<>();
        for (EssApplication application : server.getApplications()) {
            cubes.addAll(application.getCubes());
        }
        assertFalse("server has no cubes to export", cubes.isEmpty());

        for (EssCube cube : cubes) {
            byte[] xml = cube.getOutline().downloadXml();
            logger.info("Exported {}.{}: {} bytes", cube.getApplication().getName(), cube.getName(), xml.length);
            assertTrue(cube.getApplication().getName() + "." + cube.getName() + " exported nothing", xml.length > 0);
        }
    }

}
