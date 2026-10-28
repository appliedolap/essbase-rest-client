package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssDimension;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * What the dimension list says about each of Sample.Basic's dimensions. Read-only.
 */
@Category(ReadOnlyIntegrationTest.class)
public class EssDimensionStorageIT {

    private final EssServer server = ConnectionUtils.server();

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
    public void reportsStorageTypeAndMemberCounts() {
        Map<String, EssDimension> dimensions = new LinkedHashMap<>();
        for (EssDimension dimension : cube.getDimensions()) {
            dimensions.put(dimension.getName(), dimension);
        }

        assertEquals(EssDimension.StorageType.DENSE, dimensions.get("Year").getStorageType());
        assertEquals(EssDimension.StorageType.SPARSE, dimensions.get("Product").getStorageType());
        assertTrue(dimensions.get("Year").getMemberCount() > dimensions.get("Year").getStoredMemberCount());
        assertEquals("an attribute dimension stores nothing", Integer.valueOf(0), dimensions.get("Caffeinated").getStoredMemberCount());
    }

}
