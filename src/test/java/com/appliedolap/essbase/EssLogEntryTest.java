package com.appliedolap.essbase;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Offline. Every line here is verbatim from {@code /applications/Sample/logs/latest} on a 26.1 server.
 */
public class EssLogEntryTest {

    private static final String ERROR_LINE =
            "[2026-09-10T20:57:04.112+00:00] [Sample] [ERROR:32] [OTLAPI-54] [OTLAPI] "
                    + "[ecid: 1789073822267,0] [tid: 140680831694400] [REQ_ID: 6aa3199e00000021] "
                    + "[DBNAME: Basic] Cannot find generation or level name";

    private static final String STARTUP_LINE =
            "[2026-09-10T20:57:00.512+00:00] [Sample] [NOTIFICATION:16] [SVR-35] [SVR] "
                    + "[ecid: 1789073820551,0] [tid: 140320840658816] Starting Essbase Server - Application [Sample]";

    @Test
    public void readsTheFivePositionalFields() {
        EssLogEntry entry = EssLogEntry.parse(ERROR_LINE);

        assertTrue(entry.isParsed());
        assertEquals(Instant.parse("2026-09-10T20:57:04.112Z"), entry.getTimestamp());
        assertEquals("Sample", entry.getApplication());
        assertEquals(EssLogEntry.Severity.ERROR, entry.getSeverity());
        assertEquals(32, entry.getSeverityLevel());
        assertEquals("OTLAPI-54", entry.getMessageId());
        assertEquals("OTLAPI", entry.getComponent());
        assertEquals("Cannot find generation or level name", entry.getMessage());
    }

    @Test
    public void readsTheNamedFieldsThatFollowThem() {
        EssLogEntry entry = EssLogEntry.parse(ERROR_LINE);

        assertEquals("6aa3199e00000021", entry.getFields().get("REQ_ID"));
        assertEquals("Basic", entry.getFields().get("DBNAME"));
        assertEquals("140680831694400", entry.getFields().get("tid"));
    }

    @Test
    public void takesOnlyTheFieldsAnEntryActuallyCarries() {
        // A startup message has no REQ_ID or DBNAME - which fields appear varies by event.
        EssLogEntry entry = EssLogEntry.parse(STARTUP_LINE);

        assertEquals(2, entry.getFields().size());
        assertFalse(entry.getFields().containsKey("DBNAME"));
    }

    @Test
    public void doesNotMistakeABracketInTheMessageForAField() {
        // "Starting Essbase Server - Application [Sample]" ends in a bracket that is text, not a field.
        EssLogEntry entry = EssLogEntry.parse(STARTUP_LINE);

        assertEquals("Starting Essbase Server - Application [Sample]", entry.getMessage());
    }

    @Test
    public void keepsALineItCannotRead() {
        EssLogEntry entry = EssLogEntry.parse("this is not an ODL line at all");

        assertFalse(entry.isParsed());
        assertNull(entry.getTimestamp());
        assertEquals("this is not an ODL line at all", entry.getMessage());
        assertEquals(EssLogEntry.Severity.UNKNOWN, entry.getSeverity());
    }

    @Test
    public void readsTheSeveritiesThisServerWrites() {
        assertEquals(EssLogEntry.Severity.NOTIFICATION, EssLogEntry.Severity.parse("NOTIFICATION"));
        assertEquals(EssLogEntry.Severity.WARNING, EssLogEntry.Severity.parse("WARNING"));
        assertEquals(EssLogEntry.Severity.ERROR, EssLogEntry.Severity.parse("ERROR"));
        assertEquals(EssLogEntry.Severity.INCIDENT_ERROR, EssLogEntry.Severity.parse("INCIDENT_ERROR"));
        assertEquals(EssLogEntry.Severity.UNKNOWN, EssLogEntry.Severity.parse("SOMETHINGELSE"));
        assertTrue(EssLogEntry.Severity.ERROR.isProblem());
        assertFalse(EssLogEntry.Severity.NOTIFICATION.isProblem());
    }

    @Test
    public void stripsTheByteOrderMarkEssbaseWritesAtTheHead() throws Exception {
        List<EssLogEntry> entries = parse("﻿" + STARTUP_LINE);

        assertEquals(1, entries.size());
        assertTrue("a BOM left in place stops the first line parsing", entries.get(0).isParsed());
    }

    @Test
    public void foldsAContinuationLineIntoTheEntryAboveIt() throws Exception {
        List<EssLogEntry> entries = parse(ERROR_LINE + "\n  at com.example.Thing.method(Thing.java:42)\n" + STARTUP_LINE);

        assertEquals(2, entries.size());
        assertTrue(entries.get(0).getMessage(),
                entries.get(0).getMessage().endsWith("at com.example.Thing.method(Thing.java:42)"));
        assertEquals("SVR-35", entries.get(1).getMessageId());
    }

    @Test
    public void keepsLeadingRubbishRatherThanSwallowingIt() throws Exception {
        List<EssLogEntry> entries = parse("something before any entry\n" + STARTUP_LINE);

        assertEquals(2, entries.size());
        assertFalse(entries.get(0).isParsed());
        assertTrue(entries.get(1).isParsed());
    }

    private static List<EssLogEntry> parse(String text) throws Exception {
        return EssLogEntry.parseAll(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

}
