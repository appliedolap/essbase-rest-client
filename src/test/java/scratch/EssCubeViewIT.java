package scratch;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EssCubeViewIT extends AbstractEssbaseServerTest {

    private static final Logger logger = LoggerFactory.getLogger(EssCubeViewIT.class);

    @Before
    public void resetDefaultView() {
        sampleBasic().resetDefaultView();
    }

    private EssCube sampleBasic() {
        return server.getApplication("Sample").getCube("Basic");
    }

    private void logGrid(EssCubeView view) {
        logger.info("Grid is {} rows x {} columns", view.getRows(), view.getColumns());
        for (int row = 0; row < view.getRows(); row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < view.getColumns(); col++) {
                line.append(String.format("%20s", view.getCell(row, col)));
            }
            logger.info(line.toString());
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void openDefaultView() {
        EssCubeView view = sampleBasic().openCubeView();
        assertNotNull(view);
        logGrid(view);
    }

    // openCubeView() is NOT idempotent: it returns whatever ad hoc view state Essbase already has
    // active server-side for this user+cube, which persists across brand-new sessions (confirmed:
    // separate test runs, each a fresh WebLogic session, still saw a prior run's zoom/pivot state).
    // It is NOT a named/saved layout though - "Default" is not a real layout name, so this is most
    // likely genuine Essbase-kernel-level cube view state, not anything reachable via the layout API.
    // Don't assume openCubeView() gives a pristine starting point - every test/consumer needs to
    // drive the view to its own known starting shape explicitly.
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void openingNonexistentLayoutThrows() {
        try {
            sampleBasic().openCubeView("Default");
            fail("Expected an EssApiException - there is no real saved layout named \"Default\"");
        } catch (EssApiException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("No layout exists with this name"));
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void cellTypeDistinguishesDataFromMembers() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logGrid(view);

        // rows 2+ are the zoomed-in Product members: col0=member, col1="Year" member, col2=data, col3=blank
        for (int row = 2; row < view.getRows(); row++) {
            for (int col = 0; col < view.getColumns(); col++) {
                EssCubeView.CellType expected = col == 2 ? EssCubeView.CellType.DATA : EssCubeView.CellType.MEMBER;
                assertEquals("row " + row + ", col " + col, expected, view.getCellType(row, col));
            }
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomIn() {
        EssCubeView view = sampleBasic().openCubeView();
        logger.info("Before zoom in:");
        logGrid(view);

        view.zoomIn(1, 0);
        logger.info("After zoom in on (1, 0):");
        logGrid(view);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void zoomInThenZoomOut() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("After zoom in:");
        logGrid(view);

        view.zoomOut(1, 0);
        logger.info("After zoom out:");
        logGrid(view);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void keepOnly() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("After zoom in:");
        logGrid(view);

        view.keepOnly(1, 0);
        logger.info("After keep only on (1, 0):");
        logGrid(view);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void removeOnly() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("After zoom in:");
        logGrid(view);

        view.removeOnly(1, 0);
        logger.info("After remove only on (1, 0):");
        logGrid(view);
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void refresh() {
        EssCubeView view = sampleBasic().openCubeView();
        view.refresh();
        logGrid(view);
    }

    // Both of these passed (with no engine error) before resetDefaultView() was wired into @Before -
    // but that was against a contaminated, previously-mutated starting grid, not the true pristine
    // baseline. Against the real clean baseline, both now fail with "Cannot pivot last column."
    // Still needs a genuinely valid pivot target - same open question as pivotToPov below.
    @Ignore
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotColumnForColumn() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("Before pivot:");
        logGrid(view);

        // swap the dimension in column 0 (Product) with the one in column 1 (Year)
        view.pivot(2, 0, 2, 1);
        logger.info("After pivot:");
        logGrid(view);
    }

    @Ignore
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotAxisToAxis() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("Before pivot:");
        logGrid(view);

        // move Year (2,1) to where Measures (1,2) is
        view.pivot(2, 1, 1, 2);
        logger.info("After pivot:");
        logGrid(view);
    }

    // Still unresolved: "Your pivot operation cannot be performed on this report." Pinning one
    // member (Colas) of a still-multi-member Product axis to the POV may not be a legal pivotToPov
    // target as-is - needs real ad hoc grid domain input, same as the earlier pivot() investigation.
    @Ignore
    @Test
    @Category(DestructiveIntegrationTest.class)
    public void pivotToPov() {
        EssCubeView view = sampleBasic().openCubeView();
        view.zoomIn(1, 0);
        logger.info("Before pivot to POV:");
        logGrid(view);

        // pin "Colas" (2,0) to the POV, dropping Product from the row axis
        view.pivotToPov(2, 0);
        logger.info("After pivot to POV:");
        logGrid(view);
    }

}
