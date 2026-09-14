package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssEffectiveVariable;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssVariable;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Pins down how the three scopes of variable actually behave, because the answer is not what the API
 * looks like it should be and nothing in the documentation says so.
 *
 * <p>The finding, established against a live 26.1 server: variables cascade at <em>evaluation</em>
 * time - a query against a cube resolves cube, then application, then server - but the REST API
 * offers no resolved view. Each list endpoint reports only what is defined at exactly its own level,
 * so a server variable is invisible to {@code application.getVariables()} while still being perfectly
 * usable from a query against that application's cubes.
 *
 * <p>That gap is the reason {@link EssCube#getEffectiveVariables()} exists, and this is what stops it
 * being quietly rewritten into a single call one day.
 */
@Category(DestructiveIntegrationTest.class)
public class EssVariableCascadeIT {

    /** Distinctive enough that a leftover is obviously ours, if a run dies between create and cleanup. */
    private static final String NAME = "cessna_cascade_it";

    private final EssServer server = ConnectionUtils.server();

    private final EssApplication application = server.getApplication("Sample");

    private final EssCube cube = application.getCube("Basic");

    @After
    public void removeWhateverSurvived() {
        find(server.getVariables()).ifPresent(EssVariable::delete);
        find(application.getVariables()).ifPresent(EssVariable::delete);
        find(cube.getVariables()).ifPresent(EssVariable::delete);
    }

    /**
     * The core finding: defining a variable on the server does not make it appear in an application's
     * or a cube's list. If this ever starts failing, Essbase has grown a resolved view and
     * {@link EssCube#getEffectiveVariables()} should be reconsidered.
     */
    @Test
    public void listsDoNotCascade() {
        server.createVariable(NAME, "from_server");

        assertTrue("the server should list its own", find(server.getVariables()).isPresent());
        assertThat("an application does not inherit into its list",
                find(application.getVariables()).isPresent(), is(false));
        assertThat("nor does a cube",
                find(cube.getVariables()).isPresent(), is(false));
    }

    /** The same name can be defined at every level at once; they are three separate definitions. */
    @Test
    public void everyScopeCanDefineTheSameName() {
        server.createVariable(NAME, "from_server");
        application.createVariable(NAME, "from_application");
        cube.createVariable(NAME, "from_cube");

        assertEquals("from_server", find(server.getVariables()).orElseThrow().getValue());
        assertEquals("from_application", find(application.getVariables()).orElseThrow().getValue());
        assertEquals("from_cube", find(cube.getVariables()).orElseThrow().getValue());
    }

    /** Nearest definition wins, and the ones it hides are reported rather than dropped. */
    @Test
    public void effectiveViewResolvesNearestFirst() {
        server.createVariable(NAME, "from_server");
        application.createVariable(NAME, "from_application");
        cube.createVariable(NAME, "from_cube");

        EssEffectiveVariable effective = effective().orElseThrow();
        assertEquals("from_cube", effective.getValue());
        assertEquals(EssVariable.Scope.CUBE, effective.getScope());
        assertTrue(effective.isOverriding());
        assertEquals("both hidden definitions are kept", 2, effective.getShadowed().size());
        assertEquals("furthest away first",
                EssVariable.Scope.SERVER, effective.getShadowed().get(0).getScope());
    }

    /** Removing the nearest definition uncovers the next one rather than removing the name. */
    @Test
    public void deletingTheNearestUncoversTheNext() {
        server.createVariable(NAME, "from_server");
        application.createVariable(NAME, "from_application");
        cube.createVariable(NAME, "from_cube");

        find(cube.getVariables()).orElseThrow().delete();
        assertEquals("from_application", effective().orElseThrow().getValue());

        find(application.getVariables()).orElseThrow().delete();
        assertEquals("from_server", effective().orElseThrow().getValue());
        assertThat("nothing left to hide", effective().orElseThrow().isOverriding(), is(false));
    }

    /**
     * Editing is not creating. The server refuses a second create at the same level, so a caller that
     * treated them as interchangeable would fail on the second write rather than updating.
     */
    @Test
    public void valueIsChangedByEditingRatherThanRecreating() {
        server.createVariable(NAME, "before");
        find(server.getVariables()).orElseThrow().setValue("after");
        assertEquals("after", find(server.getVariables()).orElseThrow().getValue());
    }

    /** Deleting at one scope leaves the others alone - the bug that inherited delete used to cause. */
    @Test
    public void deleteAppliesToItsOwnScopeOnly() {
        server.createVariable(NAME, "from_server");
        application.createVariable(NAME, "from_application");

        find(application.getVariables()).orElseThrow().delete();

        assertThat("the application's is gone", find(application.getVariables()).isPresent(), is(false));
        assertTrue("the server's is untouched", find(server.getVariables()).isPresent());
    }

    private Optional<EssEffectiveVariable> effective() {
        return cube.getEffectiveVariables().stream()
                .filter(variable -> variable.getName().equalsIgnoreCase(NAME))
                .findFirst();
    }

    private static Optional<EssVariable> find(List<? extends EssVariable> variables) {
        return variables.stream()
                .filter(variable -> variable.getName().equalsIgnoreCase(NAME))
                .map(EssVariable.class::cast)
                .findFirst();
    }

}
