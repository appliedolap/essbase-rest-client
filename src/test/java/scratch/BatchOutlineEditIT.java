package scratch;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssBatchOutlineEdit;
import com.appliedolap.essbase.EssBatchOutlineEditResult;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssLock;
import com.appliedolap.essbase.EssMember;
import com.appliedolap.essbase.impl.EssApplicationImpl;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(DestructiveIntegrationTest.class)
public class BatchOutlineEditIT extends AbstractEssbaseServerTest {

    /** A parent of the tests' own under Market, so nothing of Sample's moves and one delete cleans up. */
    private static final String PARENT = "BatchOutlineEditIT";

    /** Set only by the sibling-order tests, so the cleanup leaves the other test's cube alone. */
    private EssCube cube;

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

    @Test
    public void aMemberAddedWithoutASiblingGoesFirst() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.addMember(PARENT, "Market");
        edit.addMember("IT Jan", PARENT);
        edit.addMember("IT Feb", PARENT);

        assertEquals(List.of("IT Feb", "IT Jan"), childrenAfter(edit));
    }

    @Test
    public void aMemberAddedAfterASiblingGoesRightAfterIt() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.addMember(PARENT, "Market");
        edit.addMember("IT Jan", PARENT);
        edit.addMember("IT Mar", PARENT).after("IT Jan");
        edit.addMember("IT Feb", PARENT).after("IT Jan");

        assertEquals(List.of("IT Jan", "IT Feb", "IT Mar"), childrenAfter(edit));
    }

    @After
    public void deleteParent() {
        if (cube != null) {
            EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
            edit.deleteMember(PARENT);
            cube.batchOutlineEdit(edit);
        }
    }

    private List<String> childrenAfter(EssBatchOutlineEdit edit) {
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server has no Sample.Basic", e);
        }
        EssBatchOutlineEditResult result = cube.batchOutlineEdit(edit);
        assertTrue(result.getLog(), result.isSuccessful());
        return cube.getMember(PARENT).getChildren().stream().map(EssMember::getName).toList();
    }

}
