package com.appliedolap.essbase;

import org.junit.Test;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Offline. Asserts on the document text, because the document text is the entire interface - the
 * server skips any element it does not recognise without a word, so a wrong spelling here is a silent
 * no-op rather than an error anyone would see.
 */
public class EssBatchOutlineEditTest {

    @Test
    public void writesTheRootTheServerExpects() {
        String xml = EssBatchOutlineEdit.create().toXml();

        assertTrue(xml, xml.contains("<otlEditMain xmlns=\"mbredit\" otlVersion=\"-1\">"));
        assertTrue(xml, xml.contains("</otlEditMain>"));
    }

    /**
     * The spelling that matters most: the published REST spec says {@code ALL_DATA}, and sending that
     * is answered with HTTP 500. Only the schema's spelling works.
     */
    @Test
    public void restructureUsesTheSpellingTheServerAccepts() {
        String xml = EssBatchOutlineEdit.create()
                .restructure(EssBatchOutlineEdit.Restructure.ALL_DATA).toXml();

        assertTrue(xml, xml.contains("restructOption=\"allData\""));
        assertFalse(xml, xml.contains("ALL_DATA"));
    }

    @Test
    public void omitsOptionalAttributesThatWereNeverSet() {
        String xml = EssBatchOutlineEdit.create().toXml();

        assertFalse(xml, xml.contains("validate="));
        assertFalse(xml, xml.contains("keepTransaction="));
        assertFalse(xml, xml.contains("restructOption="));
    }

    @Test
    public void writesAMemberAddWithItsInfo() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.addMember("Widget", "Product")
                .consolidation(EssBatchOutlineEdit.Consolidation.ADD)
                .dataStorage(EssBatchOutlineEdit.DataStorage.NEVER_SHARE)
                .alias("Our widget")
                .alias("French", "Le widget")
                .uda("New")
                .solveOrder(3)
                .twoPassCalc(true);
        String xml = edit.toXml();

        assertTrue(xml, xml.contains("<mbrAdd mbrName=\"Widget\" parent=\"Product\">"));
        assertTrue(xml, xml.contains("<mbrInfo twoPassCalc=\"true\">"));
        assertTrue(xml, xml.contains("<consolidation>+</consolidation>"));
        assertTrue(xml, xml.contains("<dataStorage>neverShare</dataStorage>"));
        assertTrue(xml, xml.contains("<solveOrder>3</solveOrder>"));
        assertTrue(xml, xml.contains("<alias aliasTable=\"Default\" alias=\"Our widget\"/>"));
        assertTrue(xml, xml.contains("<alias aliasTable=\"French\" alias=\"Le widget\"/>"));
        assertTrue(xml, xml.contains("<udas name=\"New\" replace=\"true\" remove=\"false\"/>"));
    }

    @Test
    public void writesAnActionWithNoInfoAsAnEmptyElement() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.deleteMember("Discontinued");

        assertTrue(edit.toXml(), edit.toXml().contains("<mbrDelete thisMbr=\"Discontinued\"/>"));
    }

    @Test
    public void keepsActionsInTheOrderTheyWereAdded() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.addMember("A", "Product");
        edit.renameMember("A", "B");
        edit.deleteMember("B");
        String xml = edit.toXml();

        assertEquals(3, edit.size());
        assertTrue(xml.indexOf("mbrAdd") < xml.indexOf("mbrRename"));
        assertTrue(xml.indexOf("mbrRename") < xml.indexOf("mbrDelete"));
    }

    /**
     * A formula is full of {@code <} and {@code >}. Unescaped, the document becomes a different set of
     * actions - and since the server silently skips what it does not recognise, the failure would be
     * an edit that reports success and does nothing.
     */
    @Test
    public void escapesContentThatWouldOtherwiseBeMarkup() throws Exception {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.updateMember("Margin & Co").formula("IF (\"Sales\" < 100) 1; ELSE 2; ENDIF;");
        String xml = edit.toXml();

        assertTrue(xml, xml.contains("thisMbr=\"Margin &amp; Co\""));
        assertFalse(xml, xml.contains("\"Sales\" < 100"));
        assertTrue(xml, xml.contains("&lt; 100"));
        // And it is still a document a parser accepts, which is the point of the escaping.
        DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void everyDocumentItWritesParses() throws Exception {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create()
                .validate(true).validateFormulas(true).keepTransaction(true)
                .restructure(EssBatchOutlineEdit.Restructure.NO_DATA);
        edit.addOrUpdateMember("A", "Product").after("B").comment("hello");
        edit.moveMember("A", "Market");
        edit.associateAttribute("100-10", "Caffeinated", "True");
        edit.dissociateAttributes("100-10");
        edit.updateDimension("Market")
                .storage(EssBatchOutlineEdit.DimensionStorage.SPARSE)
                .category(EssBatchOutlineEdit.DimensionCategory.NONE)
                .nameUnique(false);
        edit.associateAttributeDimension("Product", "Caffeinated");
        edit.sortChildren("Product", EssBatchOutlineEdit.SortOrder.ASCENDING, true);
        edit.setDataStorage("Product", EssBatchOutlineEdit.DataStorage.DYNAMIC_CALC,
                EssBatchOutlineEdit.ApplyTo.GENERATION, 2);
        edit.markForDelete("Market");
        edit.deleteMarked("Market");

        String xml = edit.toXml();
        DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        assertEquals(10, edit.size());
        assertTrue(xml, xml.contains("<dataStorageSet thisMbr=\"Product\" dataStorage=\"dynamic\""
                + " applyTo=\"generation\" number=\"2\"/>"));
        assertTrue(xml, xml.contains("<properties nameUnique=\"false\">"));
    }

    /** The escape hatch for what the builder does not model, so a gap never means dropping to raw XML. */
    @Test
    public void canWriteAnActionItDoesNotModel() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.action("smartListDelete").attribute("smartListName", "Colours");

        assertTrue(edit.toXml(),
                edit.toXml().contains("<smartListDelete smartListName=\"Colours\"/>"));
    }

    /** EPM Cloud's own documents address every member by Planning id rather than by name. */
    @Test
    public void canAddressMembersById() {
        EssBatchOutlineEdit edit = EssBatchOutlineEdit.create();
        edit.updateMember("fd942166-21ae-4b36-b9f8-287164c1940c").byMemberId().alias("Net Income");

        assertTrue(edit.toXml(), edit.toXml().contains("isMbrId=\"true\""));
    }

}
