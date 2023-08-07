package scratch;

import com.appliedolap.essbase.EssFile;
import com.appliedolap.essbase.EssJob;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EssServerIT extends AbstractEssbaseServerTest {

    private static final Logger logger = LoggerFactory.getLogger(EssServerIT.class);

    @Test
    public void getFile() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
    }

    @Test
    public void createApplicationFromWorkbook() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
        EssJob job = server.createApplicationFromWorkbook("Sample", "Basic", sampleBasic);
        System.out.println("Desc: " + job.getDescription());
    }

    @Test
    public void createApplicationFromWorkbookWithWait() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
        EssJob job = server.createApplicationFromWorkbook("Sample", "Basic", sampleBasic);
        EssJob finished = job.waitForCompletion();

        logger.info("Job finished in {} seconds", TimeUnit.MILLISECONDS.toSeconds(finished.getDuration()));

        System.out.println("Desc: " + job.getDescription());
    }

}