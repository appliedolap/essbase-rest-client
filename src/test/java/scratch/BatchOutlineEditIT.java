package scratch;

import com.appliedolap.essbase.EssBatchOutlineEdit;
import com.appliedolap.essbase.EssBatchOutlineEditResult;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMember;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(DestructiveIntegrationTest.class)
public class BatchOutlineEditIT extends AbstractEssbaseServerTest {

    /** A parent of the tests' own under Market, so nothing of Sample's moves and one delete cleans up. */
    private static final String PARENT = "BatchOutlineEditIT";

    private EssCube cube;

    @Before
    public void findSampleBasic() {
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server has no Sample.Basic", e);
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
        EssBatchOutlineEditResult result = cube.batchOutlineEdit(edit);
        assertTrue(result.getLog(), result.isSuccessful());
        return cube.getMember(PARENT).getChildren().stream().map(EssMember::getName).toList();
    }

}
