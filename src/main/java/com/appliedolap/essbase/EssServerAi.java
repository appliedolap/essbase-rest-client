package com.appliedolap.essbase;

import java.util.Set;

/**
 * What a server can say about its own generative-AI setup, reached through {@link EssServer#getAi()}.
 *
 * <p>The AI setup chain has two halves and they are fixed by different people in different places.
 * Whether the server supports AI at all, whether it is switched on, which capabilities are switched
 * on, and whether any GenAI connection exists are all properties of the server: one answer, fixed
 * once, true for every application on it. Only the last link - whether a given application is
 * pointed at one of those connections - varies per application.
 *
 * <p>{@link EssCube#getAi()} walks the whole chain and so re-asks the server-level half once per
 * cube, which is the right thing when the question is "will this cube's AI work" and the wrong
 * thing when it is "has anyone configured AI here at all". This answers the second question, once.
 *
 * <p>Consequently {@link EssAiReadiness.State#READY} means something narrower here than it does on a
 * cube: every server-level prerequisite is in place, and each application still has to be associated
 * with a connection separately. {@link EssAiReadiness.State#APPLICATION_NOT_ASSOCIATED} is never
 * returned, because no application is in view.
 */
public interface EssServerAi {

    /**
     * Works out how far this server's AI setup goes, without reference to any application or cube.
     * Never throws - an unreachable server comes back as {@link EssAiReadiness.State#UNDETERMINED}.
     *
     * @return the diagnosis, never null
     */
    EssAiReadiness getReadiness();

    /**
     * The same diagnosis, narrowed to one capability, so that a server with AI on but the MDX
     * generator off reports {@link EssAiReadiness.State#FEATURE_DISABLED} rather than readiness.
     *
     * @param feature the capability to ask about, or null for AI in general
     * @return the diagnosis, never null
     */
    EssAiReadiness getReadiness(EssAiFeature feature);

    /**
     * Which AI capabilities this server has switched on, in one request.
     *
     * <p>Says nothing about whether anything is configured behind them - a capability can be on with
     * no GenAI connection on the server at all, which is the usual state of a fresh 26.1 install.
     * This is the inventory; {@link #getReadiness(EssAiFeature)} is the diagnosis.
     *
     * @return the enabled capabilities, empty on a server too old to have any
     */
    Set<EssAiFeature> getEnabledFeatures();

}
