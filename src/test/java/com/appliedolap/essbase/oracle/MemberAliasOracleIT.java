package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMember;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A member's aliases, which arrive with the member rather than being asked for.
 *
 * <p>Worth pinning because it is the thing that makes showing them in an outline free: the server
 * sends every alias table's value for every member of the fetch that built the tree, so annotating an
 * outline with one table's aliases costs no requests at all.
 */
@Category(DestructiveIntegrationTest.class)
public class MemberAliasOracleIT {

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    @Test
    public void aMemberCarriesEveryTablesAlias() {
        EssMember colas = cube.getMember("100");

        assertEquals("Colas", colas.getAliases().get("Default"));
        assertTrue("expected more than the Default table on Sample.Basic, got "
                + colas.getAliases().keySet(), colas.getAliases().size() > 1);
    }

    /** Null and blank read as Default, so a caller with no table configured still gets an alias. */
    @Test
    public void theDefaultTableIsWhatAnUnnamedOneMeans() {
        EssMember colas = cube.getMember("100");

        assertEquals("Colas", colas.getAlias(null));
        assertEquals("Colas", colas.getAlias(""));
        assertEquals("Colas", colas.getAlias("Default"));
    }

    /**
     * A table the member has no alias in is a key with no value, not a missing key - so reading it
     * through the map gives null and reading it through {@link EssMember#getAlias(String)} does too.
     */
    @Test
    public void aTableWithNoAliasForThisMemberReadsAsNothing() {
        EssMember colas = cube.getMember("100");
        Assume.assumeTrue("this cube has no Long Names table",
                colas.getAliases().containsKey("Long Names"));

        assertNull(colas.getAlias("Long Names"));
    }

    /**
     * A member reached by walking the outline carries them too, which is the path a tree takes.
     *
     * <p>Its own test because it is a different code path and it was broken: the listing endpoint
     * deserializes into an untyped map that a hand-written copier turns into a member, and aliases were
     * not among the fields it copied. {@code getMember} built its bean the other way and had them, so
     * one reading worked and the other silently returned nothing - which looked exactly like an outline
     * whose members have no aliases.
     */
    @Test
    public void aMemberReachedByWalkingTheOutlineCarriesItsAliasesToo() {
        EssMember product = cube.getOutline().getDimensions().stream()
                .filter(dimension -> dimension.getName().equals("Product"))
                .findFirst().orElseThrow();

        EssMember colas = product.getChildren().stream()
                .filter(child -> child.getName().equals("100"))
                .findFirst().orElseThrow();

        assertEquals("Colas", colas.getAlias("Default"));
    }

    @Test
    public void aTableTheCubeDoesNotHaveReadsAsNothing() {
        assertNull(cube.getMember("100").getAlias("NoSuchTable"));
    }
}
