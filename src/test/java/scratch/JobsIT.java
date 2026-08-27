package scratch;

import com.appliedolap.essbase.EssJob;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

@Category(ReadOnlyIntegrationTest.class)
public class JobsIT extends AbstractEssbaseServerTest {

    @Test
    public void listJobs() {
        List<EssJob> jobs = server.getJobs();
        for (EssJob job : jobs) {
            System.out.printf("%20s %10s %10s", job.getName(), job.getJobType(), job.getStatus());
        }
    }

}