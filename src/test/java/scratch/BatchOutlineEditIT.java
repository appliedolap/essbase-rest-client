package scratch;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssLock;
import com.appliedolap.essbase.impl.EssApplicationImpl;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

@Category(DestructiveIntegrationTest.class)
public class BatchOutlineEditIT extends AbstractEssbaseServerTest {

    @Test
    public void batchOutlineEdit() {
        EssApplicationImpl app = server.getApplication("Sample");
        try {
            EssCube cube = app.getCube("Basic");
            List<EssLock> locks = cube.getLockedObjects(0, 50);
            EssLock lock = locks.get(0);
            cube.unlockObject(lock);
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

}
