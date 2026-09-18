package com.appliedolap.essbase;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Offline. The sample is the shape of a real Vision export, trimmed - the header block, the {@code #--!}
 * separator, the header row, then members.
 */
public class EssPlanningDimensionFileTest {

    private static final String SAMPLE = "﻿#!-- HEADERBLOCK DIMENSION XML\n"
            + "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
            + "<DIMENSIONS>\n"
            + " <Dimension name=\"Account\" dimensionType=\"Accounts\" density=\"Dense\" UUID=\"abc\" >\n"
            + " <Plan1Density>Sparse</Plan1Density>\n"
            + "</DIMENSIONS>\n"
            + "#--!\n"
            + "Account, Parent, Alias: Default, Alias: English, Data Storage, Two Pass Calculation,"
            + " Description, Formula, UDA, Time Balance, Skip Value, Variance Reporting, UUID,"
            + " Plan Type (Vision), Aggregation (Vision), Data Storage (Vision), Solve Order (Vision)\n"
            + "Totals,Account,All Totals,,label only,false,,<none>,,flow,none,non-expense,id-totals,true,+,label only,0\n"
            + "Sales,Totals,Net Sales,Ventes,store,true,The top line,\"\"\"Units\"\" * \"\"Price\"\"\","
            + "\"Revenue,Key\",balance,missing,non-expense,id-sales,true,+,store,2\n"
            + "Costs,Totals,,,store,false,,<none>,,flow,none,expense,id-costs,false,-,store,0\n"
            + "Shared Sales,Costs,,,shared,false,,<none>,,flow,none,non-expense,id-shared,true,~,shared,0\n";

    private static EssPlanningDimensionFile sample() {
        return EssPlanningDimensionFile.parse(SAMPLE);
    }

    @Test
    public void readsTheDimensionFromBothHalvesOfTheFile() {
        EssPlanningDimensionFile file = sample();

        assertEquals("Account", file.getDimensionName());
        assertEquals("Dense", file.getDensity());
        assertEquals("Accounts", file.getDimensionType());
    }

    @Test
    public void readsTheMembers() {
        List<EssPlanningDimensionFile.Member> members = sample().getMembers();

        assertEquals(4, members.size());
        assertEquals("Totals", members.get(0).getName());
        assertEquals("Account", members.get(0).getParent());
        assertEquals("id-sales", members.get(1).getId());
    }

    /** A quoted field holding commas, and a doubled quote standing for one - both occur in real files. */
    @Test
    public void readsQuotedFieldsWithCommasAndQuotesInThem() {
        EssPlanningDimensionFile.Member sales = sample().getMembers().get(1);

        assertEquals("\"Units\" * \"Price\"", sales.getFormula("Vision"));
        assertEquals(List.of("Revenue", "Key"), sales.getUdas());
    }

    @Test
    public void readsEveryAliasTable() {
        EssPlanningDimensionFile.Member sales = sample().getMembers().get(1);

        assertEquals("Net Sales", sales.getAliases().get("Default"));
        assertEquals("Ventes", sales.getAliases().get("English"));
        // An empty alias column is not an alias, so it is not written at all.
        assertFalse(sample().getMembers().get(2).getAliases().containsKey("Default"));
    }

    /** Planning writes an absent value as the literal {@code <none>}, which is not a formula. */
    @Test
    public void treatsTheLiteralNoneAsAbsent() {
        assertEquals("", sample().getMembers().get(0).getFormula("Vision"));
    }

    @Test
    public void readsThePlanTypesOwnColumns() {
        EssPlanningDimensionFile.Member sales = sample().getMembers().get(1);

        assertEquals("+", sales.getConsolidation("Vision"));
        assertEquals("store", sales.getDataStorage("Vision"));
        assertEquals("2", sales.getSolveOrder("Vision"));
    }

    /** A dimension is shared across an application's cubes, and a member can be switched off for one. */
    @Test
    public void skipsMembersThisPlanTypeDoesNotHave() {
        EssBatchOutlineEdit edit = sample().toBatchOutlineEdit("Vision");

        assertTrue(edit.toXml(), edit.toXml().contains("mbrName=\"Sales\""));
        assertFalse(edit.toXml(), edit.toXml().contains("mbrName=\"Costs\""));
    }

    /**
     * Label-only is invalid on a member with no children, and every member is childless at the instant
     * it is added - so it belongs to the refinement pass, not to the build.
     */
    @Test
    public void leavesAggregatingStorageOutOfTheBuild() {
        String xml = sample().toBatchOutlineEdit("Vision").toXml();

        assertFalse(xml, xml.contains("labelOnly"));
        assertTrue(xml, xml.contains("<dataStorage>storeData</dataStorage>"));
        assertTrue(sample().toStorageRefinement("Vision").toXml().contains("labelOnly"));
    }

    /** A shared member takes the prototype's properties; giving it its own is an error. */
    @Test
    public void givesASharedMemberNothingButItsNameAndParent() {
        String xml = sample().toBatchOutlineEdit("Vision").toXml();
        int shared = xml.indexOf("mbrName=\"Shared Sales\"");

        assertTrue(xml, shared > 0);
        String action = xml.substring(shared, xml.indexOf("</mbrAddOrUpdate>", shared));
        assertTrue(action, action.contains("<dataStorage>shared</dataStorage>"));
        assertFalse(action, action.contains("<consolidation>"));
        assertFalse(action, action.contains("<alias"));
    }

    @Test
    public void mapsPlanningsWordsOntoEssbases() {
        String xml = sample().toBatchOutlineEdit("Vision").toXml();

        // "balance" is Planning's word for what Essbase calls "last", and "flow" for no time balance.
        assertTrue(xml, xml.contains("<timeBalance>last</timeBalance>"));
        assertTrue(xml, xml.contains("<timeBalance>none</timeBalance>"));
        assertTrue(xml, xml.contains("expense=\"false\""));
    }

    @Test
    public void addsParentsBeforeChildren() {
        String xml = sample().toBatchOutlineEdit("Vision").toXml();

        assertTrue(xml.indexOf("mbrName=\"Totals\"") < xml.indexOf("mbrName=\"Sales\""));
    }

    @Test
    public void mapsMemberIdsToNamesForReadingTheBatchOutlineEditXml() {
        assertEquals("Sales", sample().memberNamesById().get("id-sales"));
    }

    @Test
    public void rejectsAFileThatIsNotOneOfThese() {
        try {
            EssPlanningDimensionFile.parse("name,parent\nA,B\n");
            org.junit.Assert.fail("should not have accepted a bare CSV");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("DIMENSIONS"));
        }
    }

}
