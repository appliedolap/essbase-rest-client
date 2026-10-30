package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAiReadiness;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Offline, against the same recorded replies as {@link EssCubeAiImplTest}. What is under test is the
 * one thing the server-level walk does differently from the cube's: it stops at the end of the
 * server's own responsibilities, and says so rather than letting READY be read as "AI works here".
 */
public class EssServerAiImplTest {

    /** 21.7 answers this for every /ai path, in XML, whatever you put in Accept. */
    private static final String NOT_FOUND_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><errorResponse>"
                    + "<errorMessage>HTTP 404 Not Found</errorMessage></errorResponse>";

    @Test
    public void readsAServerWithNoAiEndpointsAsTooOld() {
        EssAiReadiness readiness =
                EssServerAiImpl.diagnoseConnections(new EssCubeAiImpl.Reply(404, NOT_FOUND_XML));

        assertEquals(EssAiReadiness.State.NOT_SUPPORTED, readiness.getState());
    }

    /**
     * An AI-capable server with nothing configured answers 200 with a zero-length body rather than
     * an empty collection, which is the signal the cube-level walk already relies on.
     */
    @Test
    public void readsAnEmptyBodyAsNothingConfigured() {
        EssAiReadiness readiness =
                EssServerAiImpl.diagnoseConnections(new EssCubeAiImpl.Reply(200, ""));

        assertEquals(EssAiReadiness.State.NO_CONNECTION_CONFIGURED, readiness.getState());
    }

    @Test
    public void readsAConfiguredConnectionAsReady() {
        EssAiReadiness readiness = EssServerAiImpl.diagnoseConnections(
                new EssCubeAiImpl.Reply(200, "{\"name\":\"oci\"}"));

        assertEquals(EssAiReadiness.State.READY, readiness.getState());
        assertTrue(readiness.isReady());
    }

    /**
     * The distinction this class exists for. READY on a cube means a capability call will reach the
     * model; READY here means only that the server's half is done, and a reader who takes it for the
     * stronger claim will go looking for a bug in the wrong place when a cube still fails.
     */
    @Test
    public void saysReadyStillLeavesTheApplicationToAssociate() {
        EssAiReadiness readiness = EssServerAiImpl.diagnoseConnections(
                new EssCubeAiImpl.Reply(200, "{\"name\":\"oci\"}"));

        assertTrue(readiness.getExplanation(), readiness.getExplanation().contains("associated"));
    }

    @Test
    public void reportsAnUnreadableReplyAsUndetermined() {
        EssAiReadiness readiness =
                EssServerAiImpl.diagnoseConnections(new EssCubeAiImpl.Reply(500, "boom"));

        assertEquals(EssAiReadiness.State.UNDETERMINED, readiness.getState());
    }

}
