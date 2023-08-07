package scratch;

import com.appliedolap.essbase.EssFile;
import com.appliedolap.essbase.EssJob;
import org.junit.Test;

import java.util.List;

public class JobsIT extends AbstractEssbaseServerTest {

    @Test
    public void getFile() {
        List<EssJob> jobs = server.getJobs();
        for (EssJob job : jobs) {
            System.out.printf("%20s %10s %10s", job.getName(), job.getJobType(), job.getStatus());
        }
    }

}