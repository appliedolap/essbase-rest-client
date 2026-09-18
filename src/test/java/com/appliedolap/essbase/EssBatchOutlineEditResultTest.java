package com.appliedolap.essbase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Offline. Every log here is one a live Essbase 26.1 server actually sent, copied verbatim.
 */
public class EssBatchOutlineEditResultTest {

    private static final String ADDED_ONE =
            "Info[1370001]: at line 2 char 46 element[otlEditMain], outline version reset to [0].\n"
                    + "Total member added : 1\nTotal member updated : 0\nTotal member deleted : 0\n"
                    + "Total XML edit time:[0.032274]\n";

    /** What the server says when the edit could not be applied - and it says it with HTTP 200. */
    private static final String MEMBER_NOT_FOUND =
            "Info[1370001]: at line 2 char 46 element[otlEditMain], outline version reset to [0].\n"
                    + "Error[1370271]: at line 3 char 43 element[mbrUpdate], member [NoSuchMemberAtAll] not exist.\n"
                    + "Error[1370272]: at line 7 char 16 element[mbrUpdate], error update member"
                    + " [NoSuchMemberAtAll] info with status[1370271].\n"
                    + "Total member added : 0\nTotal member updated : 0\nTotal member deleted : 0\n"
                    + "Total XML edit time:[0.000000]\n";

    private static final String ALREADY_EXISTS =
            "Info[1370001]: at line 2 char 87 element[otlEditMain], outline version reset to [0].\n"
                    + "Warn[1370101]: at line 9 char 13 element[mbrAdd], member [BoeProbe] already exist."
                    + " mbrInfo will be ignored.\n"
                    + "Total member added : 0\nTotal member updated : 0\nTotal member deleted : 0\n"
                    + "Total XML edit time:[0.000000]\n";

    /** An action the server did not recognise: no error, no warning, nothing done, HTTP 200. */
    private static final String UNRECOGNISED_ACTION =
            "Info[1370001]: at line 2 char 47 element[otlEditMain], outline version reset to [0].\n"
                    + "Total member added : 0\nTotal member updated : 0\nTotal member deleted : 0\n"
                    + "Total XML edit time:[0.000000]\n";

    @Test
    public void readsTheCounts() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult(ADDED_ONE);

        assertEquals(1, result.getAdded());
        assertEquals(0, result.getUpdated());
        assertEquals(0, result.getDeleted());
        assertEquals(1, result.getTotalChanged());
        assertTrue(result.isSuccessful());
    }

    /**
     * The point of the class. This log arrived with HTTP 200, and anything that trusted the status
     * code would have reported a successful outline edit that changed nothing.
     */
    @Test
    public void anErrorLogIsNotSuccessfulHoweverTheRequestWent() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult(MEMBER_NOT_FOUND);

        assertFalse(result.isSuccessful());
        assertEquals(2, result.getErrors().size());
        assertEquals(1370271, result.getErrors().get(0).getNumber());
        assertTrue(result.getErrors().get(0).getText().contains("NoSuchMemberAtAll"));
        assertEquals(0, result.getTotalChanged());
    }

    /**
     * "Already exists" is how re-running an add reports that there was nothing to do, so it is a
     * warning rather than a failure - re-running a document has to stay something you can do.
     */
    @Test
    public void aWarningIsStillSuccessful() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult(ALREADY_EXISTS);

        assertTrue(result.isSuccessful());
        assertTrue(result.hasWarnings());
        assertEquals(1370101, result.getWarnings().get(0).getNumber());
    }

    /**
     * The failure the counts exist to catch: a misspelled action is skipped in silence, so the only
     * evidence is that a document asking for work reports none done.
     */
    @Test
    public void anUnrecognisedActionIsSilentButChangesNothing() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult(UNRECOGNISED_ACTION);

        assertTrue(result.isSuccessful());
        assertFalse(result.hasWarnings());
        assertEquals(0, result.getTotalChanged());
    }

    @Test
    public void keepsTheServersOwnWords() {
        assertEquals(MEMBER_NOT_FOUND, new EssBatchOutlineEditResult(MEMBER_NOT_FOUND).getLog());
    }

    /**
     * A log this cannot read at all still has to produce a result rather than an exception, with the
     * counts saying "unknown" rather than "zero" - the two mean very different things to a caller.
     */
    @Test
    public void survivesALogItCannotRead() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult("something else entirely");

        assertEquals(-1, result.getAdded());
        assertEquals(-1, result.getTotalChanged());
        assertTrue(result.getMessages().isEmpty());
        assertTrue(result.isSuccessful());
        assertEquals("something else entirely", result.getLog());
    }

    @Test
    public void survivesNoLogAtAll() {
        EssBatchOutlineEditResult result = new EssBatchOutlineEditResult(null);

        assertEquals("", result.getLog());
        assertEquals(-1, result.getTotalChanged());
        assertTrue(result.getMessages().isEmpty());
    }

}
