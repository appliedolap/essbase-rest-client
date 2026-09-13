package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.client.model.MemberBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Offline. The row here is verbatim from {@code GET /outline/Sample/Basic}, which is the shape this
 * has to survive - the outline viewer returns untyped rows, so nothing but this conversion stands
 * between the server's JSON and a member that knows whether it has children.
 */
public class EssMemberImplTest {

    private static Map<String, Object> yearRow() {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", "Year");
        row.put("uniqueName", "Year");
        row.put("numberOfChildren", 4);
        row.put("levelNumber", 2);
        row.put("descendantsCount", 16);
        row.put("memberId", "id__34");
        row.put("memberSolveOrder", 40);
        row.put("dimension", true);
        row.put("dimensionType", "TIME");
        row.put("dataStorageType", "DYNAMICCALC");
        return row;
    }

    @Test
    public void readsAWholeNumberHoweverJacksonBoxedIt() {
        // The regression this guards: Integer is what Jackson gives for a whole number, and the old
        // test for Double alone sent every count to its default of zero - so every member in every
        // outline reported no children and looked like a leaf.
        for (Object boxed : new Object[] {4, 4L, 4.0d, 4.0f, (short) 4}) {
            Map<String, Object> row = yearRow();
            row.put("numberOfChildren", boxed);

            assertEquals(boxed.getClass().getSimpleName(),
                    Integer.valueOf(4), EssMemberImpl.propsToMemberBean(row).getNumberOfChildren());
        }
    }

    @Test
    public void fallsBackOnlyWhenTheNumberIsMissingOrNotANumber() {
        assertEquals(7, EssMemberImpl.optionalInt(null, 7));
        assertEquals(7, EssMemberImpl.optionalInt("lots", 7));
        assertEquals(0, EssMemberImpl.optionalInt(0, 7));
    }

    @Test
    public void carriesTheWholeRowAcrossNotJustTheCounts() {
        MemberBean bean = EssMemberImpl.propsToMemberBean(yearRow());

        assertEquals("Year", bean.getName());
        assertEquals("Year", bean.getUniqueName());
        assertEquals("id__34", bean.getMemberId());
        assertEquals("DYNAMICCALC", bean.getDataStorageType());
        assertEquals(Integer.valueOf(2), bean.getLevelNumber());
        assertEquals(Long.valueOf(16), bean.getDescendantsCount());
        assertTrue("the row says dimension:true", bean.getDimension());
    }

    @Test
    public void derivesTheRoleFlagsFromTheDimensionTypeTheServerNames() {
        Map<String, Object> accounts = yearRow();
        accounts.put("dimensionType", "ACCOUNTS");
        assertTrue(EssMemberImpl.propsToMemberBean(accounts).getAccount());
        assertFalse(EssMemberImpl.propsToMemberBean(accounts).getAttribute());

        Map<String, Object> attribute = yearRow();
        attribute.put("dimensionType", "ATTRIBUTE");
        assertTrue(EssMemberImpl.propsToMemberBean(attribute).getAttribute());

        // TIME is neither, and a row with no dimensionType at all must not throw.
        assertFalse(EssMemberImpl.propsToMemberBean(yearRow()).getAccount());
        assertFalse(EssMemberImpl.propsToMemberBean(new HashMap<>()).getAttribute());
    }

}
