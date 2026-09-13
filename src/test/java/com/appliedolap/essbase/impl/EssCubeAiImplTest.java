package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAiFeature;
import com.appliedolap.essbase.EssAiReadiness;
import com.appliedolap.essbase.EssMdxGeneration;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Offline. The replies here are verbatim from live servers - a 21.7 one that has never heard of the
 * AI endpoints, and a 26.1 one with AI supported but not yet configured - so what is under test is the
 * step that matters: that one indiscriminate HTTP 400 becomes a specific reason and a specific person
 * who can fix it.
 */
public class EssCubeAiImplTest {

    /** 21.7 answers this for every /ai path, in XML, whatever you put in Accept. */
    private static final String NOT_FOUND_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><errorResponse>"
                    + "<errorMessage>HTTP 404 Not Found</errorMessage></errorResponse>";

    /** What 26.1 says for a missing AI connection, whichever link in the chain is actually missing. */
    private static final String NO_CONNECTION_JSON =
            "{\n  \"errorMessage\" : \"Unable to get associated AI connection for application [Sample]\"\n}";

    /** 21.7's whole /about/instance - note the absence, not a false, of every ai flag. */
    private static final String INSTANCE_21_7 = "{\"idcs\":true,\"provisioningSupported\":true}";

    /** 26.1's, with AI on and every capability on. */
    private static final String INSTANCE_26_1 = "{\"idcs\":true,\"aiMdxEnabled\":true,"
            + "\"aiAskEssbaseEnabled\":true,\"aiCalcEnabled\":true,\"aiSemanticSearchEnabled\":true,"
            + "\"aiEnabled\":true}";

    @Test
    public void readsAServerWithNoAiFlagsAsTooOld() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseFlags(
                new EssCubeAiImpl.Reply(200, INSTANCE_21_7), EssAiFeature.MDX_GENERATOR);

        assertEquals(EssAiReadiness.State.NOT_SUPPORTED, readiness.getState());
    }

    @Test
    public void separatesAiSwitchedOffFromAiAbsent() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseFlags(
                new EssCubeAiImpl.Reply(200, "{\"aiEnabled\":false,\"aiMdxEnabled\":true}"), null);

        assertEquals(EssAiReadiness.State.DISABLED_ON_SERVER, readiness.getState());
    }

    @Test
    public void readsOneCapabilityBeingOffWhileTheRestAreOn() {
        String mdxOff = INSTANCE_26_1.replace("\"aiMdxEnabled\":true", "\"aiMdxEnabled\":false");

        assertEquals(EssAiReadiness.State.FEATURE_DISABLED, EssCubeAiImpl.diagnoseFlags(
                new EssCubeAiImpl.Reply(200, mdxOff), EssAiFeature.MDX_GENERATOR).getState());
        // The same server is still fine for a capability that is switched on.
        assertNull(EssCubeAiImpl.diagnoseFlags(
                new EssCubeAiImpl.Reply(200, mdxOff), EssAiFeature.SEMANTIC_SEARCH));
    }

    @Test
    public void letsTheConfigurationRungsDecideWhenEveryFlagIsOn() {
        assertNull(EssCubeAiImpl.diagnoseFlags(
                new EssCubeAiImpl.Reply(200, INSTANCE_26_1), EssAiFeature.MDX_GENERATOR));
    }

    @Test
    public void readsAServerWithNoAiEndpointsAsTooOld() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseServer(new EssCubeAiImpl.Reply(404, NOT_FOUND_XML));

        assertEquals(EssAiReadiness.State.NOT_SUPPORTED, readiness.getState());
        assertTrue(readiness.getExplanation(), readiness.getExplanation().contains("26.1"));
    }

    @Test
    public void readsAnEmptyBodyAsNoConnectionConfigured() {
        // Not 404 and not an empty collection - 200 with nothing in it at all.
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseServer(new EssCubeAiImpl.Reply(200, ""));

        assertEquals(EssAiReadiness.State.NO_CONNECTION_CONFIGURED, readiness.getState());
    }

    @Test
    public void letsTheApplicationDecideWhenTheServerHasAConnection() {
        assertNull(EssCubeAiImpl.diagnoseServer(new EssCubeAiImpl.Reply(200, "{\"name\":\"oci\"}")));
    }

    @Test
    public void readsTheSharedErrorAsAnUnassociatedApplication() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseApplication(
                new EssCubeAiImpl.Reply(400, NO_CONNECTION_JSON), "Sample", "Basic");

        assertEquals(EssAiReadiness.State.APPLICATION_NOT_ASSOCIATED, readiness.getState());
        assertTrue(readiness.getExplanation(), readiness.getExplanation().contains("Sample"));
    }

    @Test
    public void readsASuccessfulReplyAsReady() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseApplication(
                new EssCubeAiImpl.Reply(200, "[]"), "Sample", "Basic");

        assertTrue(readiness.isReady());
    }

    @Test
    public void doesNotGuessAtAFailureItDoesNotRecognize() {
        EssAiReadiness readiness = EssCubeAiImpl.diagnoseApplication(
                new EssCubeAiImpl.Reply(403, "{\"errorMessage\":\"Insufficient permission\"}"), "Sample", "Basic");

        assertEquals(EssAiReadiness.State.UNDETERMINED, readiness.getState());
        assertTrue(readiness.getCause().getMessage().contains("Insufficient permission"));
    }

    @Test
    public void quotesTheServersReasonFromEitherJsonOrXml() {
        assertTrue(new EssCubeAiImpl.Reply(400, NO_CONNECTION_JSON).describe("call")
                .contains("Unable to get associated AI connection for application [Sample]"));
        assertTrue(new EssCubeAiImpl.Reply(404, NOT_FOUND_XML).describe(null).contains("HTTP 404 Not Found"));
    }

    @Test
    public void findsTheGeneratedQueryWhicheverKeyCarriesIt() {
        Map<String, Object> raw = new LinkedHashMap<>();
        raw.put("mdxQuery", "SELECT {[Sales]} ON COLUMNS FROM Sample.Basic");
        raw.put("conversation_id", "abc-123");
        EssMdxGeneration generation = new EssCubeAiImpl.MdxGeneration(raw);

        assertEquals("SELECT {[Sales]} ON COLUMNS FROM Sample.Basic", generation.getMdx());
        assertEquals("abc-123", generation.getConversationId());
        assertEquals(raw, generation.getRawResponse());
    }

    @Test
    public void saysNothingRatherThanGuessingWhenNoKeyLooksLikeAQuery() {
        assertNull(new EssCubeAiImpl.MdxGeneration(Map.of("answer", "I don't know")).getMdx());
    }

}
