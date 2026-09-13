package com.appliedolap.essbase;

import java.util.Collections;
import java.util.Map;

/**
 * What the MDX generator made of a question asked in English.
 *
 * <p>The generated query is the point of the call, but it is a suggestion and not a promise: it is
 * written by a language model against the cube's outline, so it can name a member that doesn't
 * exist or read the question the wrong way. Run it like any other MDX - through
 * {@link EssCube#executeMdx(String)} - and let the server be the judge.
 *
 * <p>{@link #getConversationId()} is how a follow-up question keeps its context. The first call in
 * a conversation passes {@code isConvStart}; every later one quotes the id from the answer before
 * it, which is what lets "and the same for Qtr2" mean anything.
 */
public interface EssMdxGeneration {

    /**
     * The generated query, or null if the model answered without one.
     */
    String getMdx();

    /**
     * The id tying this answer to the ones before and after it, for a multi-turn conversation.
     */
    String getConversationId();

    /**
     * Whatever else came back, unmodified.
     *
     * <p>Present because this end of the API is young and undocumented: the response is modeled on
     * what a server was observed to send, and anything that model doesn't name yet is still
     * reachable here rather than dropped on the floor.
     */
    default Map<String, Object> getRawResponse() {
        return Collections.emptyMap();
    }

}
