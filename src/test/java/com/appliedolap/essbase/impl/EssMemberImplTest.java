package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssMember;
import com.appliedolap.essbase.client.model.MemberBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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


    @Test
    public void readsTheStorageSpellingsTheServerActuallySends() {
        // Observed across Sample.Basic: these three and nothing else. The field is absent entirely for
        // a plain stored member, which is why null has to mean STORED rather than unknown.
        assertEquals(EssMember.DataStorage.DYNAMIC_CALC, EssMember.DataStorage.parse("DYNAMICCALC"));
        assertEquals(EssMember.DataStorage.LABEL_ONLY, EssMember.DataStorage.parse("LABELONLY"));
        assertEquals(EssMember.DataStorage.SHARED, EssMember.DataStorage.parse("SHAREDMEMBER"));
        assertEquals(EssMember.DataStorage.STORED, EssMember.DataStorage.parse(null));
        assertEquals(EssMember.DataStorage.STORED, EssMember.DataStorage.parse(""));
    }

    @Test
    public void toleratesSeparatorsAndCaseInAStorageName() {
        assertEquals(EssMember.DataStorage.DYNAMIC_CALC_AND_STORE,
                EssMember.DataStorage.parse("Dynamic Calc And Store"));
        assertEquals(EssMember.DataStorage.NEVER_SHARE, EssMember.DataStorage.parse("never_share"));
    }

    @Test
    public void saysUnknownRatherThanGuessingAtAStorageItDoesNotName() {
        assertEquals(EssMember.DataStorage.UNKNOWN, EssMember.DataStorage.parse("SOMETHINGNEW"));
    }

    @Test
    public void readsDenseAndSparseAndNothingElse() {
        assertEquals(EssMember.DimensionStorage.DENSE, EssMember.DimensionStorage.parse("DENSE"));
        assertEquals(EssMember.DimensionStorage.SPARSE, EssMember.DimensionStorage.parse("SPARSE"));
        // An attribute dimension has no dimStorageType at all, and neither does a member below one.
        assertEquals(EssMember.DimensionStorage.UNSPECIFIED, EssMember.DimensionStorage.parse(null));
    }

    @Test
    public void readsTheRolesOffTheMemberAndOffTheDimensionRow() {
        Map<String, Object> dimensionRow = yearRow();
        dimensionRow.put("dimensionType", "ACCOUNTS");
        assertTrue("the dimension row names the role", EssMemberImpl.propsToMemberBean(dimensionRow).getAccount());

        // A member below that dimension carries its own flag instead, with no dimensionType.
        Map<String, Object> memberRow = new LinkedHashMap<>();
        memberRow.put("name", "Sales");
        memberRow.put("account", true);
        assertTrue("the member row carries its own flag", EssMemberImpl.propsToMemberBean(memberRow).getAccount());

        Map<String, Object> attributeRow = new LinkedHashMap<>();
        attributeRow.put("name", "Attribute Calculations");
        attributeRow.put("dimensionType", "ATTRIBUTECALC");
        assertTrue(EssMemberImpl.propsToMemberBean(attributeRow).getAttribute());
    }


    @Test
    public void readsTheDimensionTypesTheServerActuallySends() {
        // Observed across Sample.Basic, plus the absent case that every ordinary dimension shows.
        assertEquals(EssMember.DimensionType.TIME, EssMember.DimensionType.parse("TIME"));
        assertEquals(EssMember.DimensionType.ACCOUNTS, EssMember.DimensionType.parse("ACCOUNTS"));
        assertEquals(EssMember.DimensionType.ATTRIBUTE, EssMember.DimensionType.parse("ATTRIBUTE"));
        assertEquals(EssMember.DimensionType.ATTRIBUTE_CALC, EssMember.DimensionType.parse("ATTRIBUTECALC"));
        assertEquals(EssMember.DimensionType.NONE, EssMember.DimensionType.parse(null));
        assertEquals(EssMember.DimensionType.UNKNOWN, EssMember.DimensionType.parse("SOMETHINGNEW"));
    }

    @Test
    public void hasADisplayNameForEveryTypeExceptTheOrdinaryOne() {
        for (EssMember.DimensionType type : EssMember.DimensionType.values()) {
            if (type == EssMember.DimensionType.NONE) {
                assertNull("NONE has nothing to display", type.getLabel());
            } else {
                assertNotNull(type + " needs a label", type.getLabel());
            }
        }
        for (EssMember.DataStorage storage : EssMember.DataStorage.values()) {
            assertNotNull(storage + " needs a label", storage.getLabel());
        }
        assertEquals("Dynamic Calc", EssMember.DataStorage.DYNAMIC_CALC.getLabel());
        assertEquals("Shared Member", EssMember.DataStorage.SHARED.getLabel());
    }

}
