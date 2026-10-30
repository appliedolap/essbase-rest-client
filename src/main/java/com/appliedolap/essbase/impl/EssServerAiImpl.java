package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssAiFeature;
import com.appliedolap.essbase.EssAiReadiness;
import com.appliedolap.essbase.EssServerAi;

import java.util.EnumSet;
import java.util.Set;

import static com.appliedolap.essbase.impl.EssCubeAiImpl.Reply;
import static com.appliedolap.essbase.impl.EssCubeAiImpl.call;
import static com.appliedolap.essbase.impl.EssCubeAiImpl.diagnoseFlags;
import static com.appliedolap.essbase.impl.EssCubeAiImpl.diagnoseServer;
import static com.appliedolap.essbase.impl.EssCubeAiImpl.flag;

/**
 * The server-level half of the AI diagnosis, made of exactly the rungs that do not need a cube.
 *
 * <p>Every piece of this already existed inside {@link EssCubeAiImpl} as a separate static step -
 * the chain is walked rung by rung precisely so a break can be attributed to one - so this adds no
 * new reading of the server, only a shorter walk that stops where the cube-specific part begins.
 */
public class EssServerAiImpl implements EssServerAi {

    private final ApiContext api;

    public EssServerAiImpl(ApiContext api) {
        this.api = api;
    }

    @Override
    public EssAiReadiness getReadiness() {
        return getReadiness(null);
    }

    @Override
    public EssAiReadiness getReadiness(EssAiFeature feature) {
        Reply instance;
        try {
            instance = call(api, "GET", "/about/instance", null);
        } catch (RuntimeException e) {
            return EssAiReadiness.undetermined("Could not reach the server to ask what it supports", e);
        }
        EssAiReadiness switchedOff = diagnoseFlags(instance, feature);
        if (switchedOff != null) {
            return switchedOff;
        }

        Reply connections;
        try {
            connections = call(api, "GET", "/ai/connection", null);
        } catch (RuntimeException e) {
            return EssAiReadiness.undetermined("Could not reach the server to check its AI connections", e);
        }
        return diagnoseConnections(connections);
    }

    /**
     * The last rung this can see: either {@link EssCubeAiImpl#diagnoseServer} finds a reason, or the
     * server's own half of the setup is complete.
     *
     * <p>Static for the same reason every other rung is - so the verdict can be tested against a
     * recorded reply rather than only against a configured 26.1 server, which is exactly the thing
     * that is hard to come by.
     */
    static EssAiReadiness diagnoseConnections(Reply connections) {
        EssAiReadiness noConnection = diagnoseServer(connections);
        if (noConnection != null) {
            return noConnection;
        }
        // Deliberately not READY in the sense the cube means it: everything the server is responsible
        // for is in place, and the per-application association is still unknown from here. Saying so is
        // the point of this class, so the explanation says it rather than leaving READY to be read as
        // "AI works", which it does not yet guarantee for any particular cube.
        return EssAiReadiness.of(EssAiReadiness.State.READY,
                "This server has AI switched on and at least one AI connection configured; each "
                        + "application must still be associated with a connection before its cubes can "
                        + "use it");
    }

    @Override
    public Set<EssAiFeature> getEnabledFeatures() {
        Reply instance = call(api, "GET", "/about/instance", null);
        if (instance.status / 100 != 2 || !Boolean.TRUE.equals(flag(instance.body, EssAiFeature.ENABLED_FLAG))) {
            return EnumSet.noneOf(EssAiFeature.class);
        }
        Set<EssAiFeature> enabled = EnumSet.noneOf(EssAiFeature.class);
        for (EssAiFeature feature : EssAiFeature.values()) {
            if (Boolean.TRUE.equals(flag(instance.body, feature.getFlag()))) {
                enabled.add(feature);
            }
        }
        return enabled;
    }

}
