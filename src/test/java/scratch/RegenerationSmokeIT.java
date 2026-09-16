package scratch;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

/**
 * Exercises every call whose generated signature changed in the 26.1 regeneration.
 *
 * <p>Worth its own test because the changes were positional: 26.1 inserted parameters into the
 * middle of six signatures rather than appending them - orderBy became the third argument of
 * getCubes, svParity landed before the grid operation's body - so a wrong edit compiles and then
 * sends a value as the wrong parameter. No unit test can see that; only the server can.
 */
@Category(ReadOnlyIntegrationTest.class)
public class RegenerationSmokeIT {

    private final EssServer server = ConnectionUtils.server();

    @Test
    public void everyChangedCallStillAnswers() {
        List<EssApplication> applications = server.getApplications();
        System.out.println("applications: " + applications.size());
        for (EssApplication application : applications) {
            System.out.println("  " + application.getName());
        }

        System.out.println("server variables: " + server.getVariables().size());
        System.out.println("jobs: " + server.getJobs().size());
        System.out.println("permissions: " + server.getPermissions().size());

        EssApplication first = applications.get(0);
        System.out.println("getApplication(\"" + first.getName() + "\") -> "
                + server.getApplication(first.getName()).getName());

        List<EssCube> cubes = first.getCubes();
        System.out.println("cubes of " + first.getName() + ": " + cubes.size());
        for (EssCube cube : cubes) {
            System.out.println("  " + cube.getName());
        }
        System.out.println("app configurations: " + first.getConfigurations().size());
        System.out.println("app variables: " + first.getVariables().size());
        System.out.println("app jobs: " + first.getJobs().size());

        if (!cubes.isEmpty()) {
            EssCube cube = cubes.get(0);
            System.out.println("cube variables: " + cube.getVariables().size());
            System.out.println("drillthroughs: " + cube.getDrillthroughs().size());
            List<EssMember> dimensions = cube.getOutline().getDimensions();
            System.out.println("dimensions: " + dimensions.size());
            for (EssMember dimension : dimensions) {
                System.out.println("  " + dimension.getName()
                        + " (children: " + dimension.getChildCount() + ")");
            }
            if (!dimensions.isEmpty()) {
                EssMember dimension = dimensions.get(0);
                System.out.println("getMember(" + dimension.getName() + ") -> "
                        + cube.getMember(dimension.getName()).getName());
                System.out.println("children: " + dimension.getChildren().size());
            }
        }
    }

}
