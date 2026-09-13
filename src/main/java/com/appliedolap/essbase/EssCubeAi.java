package com.appliedolap.essbase;

import java.util.List;

/**
 * The generative-AI features a 26.1 server offers over one cube, reached through
 * {@link EssCube#getAi()}.
 *
 * <p>All of them need setup that lives outside the cube - an AI connection on the server, that
 * connection associated with the cube's application, a chat profile on the connection - and the
 * server reports any gap in that setup as the same HTTP 400 regardless of which piece is missing.
 * {@link #getReadiness()} is the way to find out which, and it is worth calling before a capability
 * rather than after: it turns one opaque error into a specific one, and it says who has to fix it.
 */
public interface EssCubeAi {

    /**
     * Works out whether the AI features on this cube can run, and if not, which prerequisite is
     * missing. Never throws - an unreachable server comes back as
     * {@link EssAiReadiness.State#UNDETERMINED}.
     *
     * <p>Costs one or two small requests and is not cached, so hold on to the result rather than
     * asking again per capability.
     *
     * @return the diagnosis, never null
     */
    EssAiReadiness getReadiness();

    /**
     * Asks the model to write an MDX query answering a question posed in English, starting a new
     * conversation.
     *
     * @param question the question, e.g. "what were sales of Colas in the East in Qtr1?"
     * @return the generated query and the conversation it started
     */
    EssMdxGeneration generateMdx(String question);

    /**
     * Asks a follow-up in an existing conversation, so the model reads the question in the context
     * of what it was already asked.
     *
     * @param question       the follow-up
     * @param conversationId the id from the previous answer, or null to start a new conversation
     * @return the generated query
     */
    EssMdxGeneration generateMdx(String question, String conversationId);

    /**
     * Questions the server suggests as a starting point for this cube - what the web interface
     * offers as examples before anyone has typed anything.
     *
     * @return the sample questions, empty if the server offers none
     */
    List<String> getSampleQuestions();

    /**
     * Forgets a conversation on the server, so a later question doesn't inherit its context.
     *
     * @param profileName the chat profile the conversation belongs to
     */
    void clearConversationHistory(String profileName);

    /**
     * The chat profile capability calls are made against. Every one of them requires a profile
     * name, and the server ships one called {@code default}; set this when a site has made its
     * own.
     *
     * @param profileName the profile name
     * @return this, for chaining
     */
    EssCubeAi withProfile(String profileName);

}
