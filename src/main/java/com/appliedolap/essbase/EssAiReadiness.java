package com.appliedolap.essbase;

/**
 * Why a cube's AI features will or won't work, worked out before calling one of them.
 *
 * <p>Every AI capability sits on top of the same chain of setup, and the server reports a break
 * anywhere in that chain with one indiscriminate message - {@code HTTP 400 "Unable to get
 * associated AI connection for application [Sample]"} - whether nobody has ever configured
 * generative AI on the server, or a connection exists and this application simply isn't pointed at
 * it. Those need opposite responses from whoever is reading the error, so this walks the chain and
 * says which link is missing.
 *
 * <p>A readiness check is diagnostic and never throws: a failure to reach the server is itself a
 * result ({@link State#UNDETERMINED}, carrying the cause).
 */
public final class EssAiReadiness {

    public enum State {

        /**
         * Every prerequisite this can see is in place, so a capability call will reach the model.
         * Setup this cannot observe may still be missing - see the class note on vectorization.
         */
        READY,

        /**
         * The server has no AI endpoints at all. They arrived in 26.1; 21.7 answers 404 (in XML,
         * whatever you put in {@code Accept}).
         */
        NOT_SUPPORTED,

        /**
         * The server supports AI but no AI connection has been configured on it, so there is no
         * model for any application to talk to. Fixed once for the whole server, and not cheaply:
         * a GenAI connection under Sources stands on an Oracle AI Database connection, because it is
         * the database that holds the OCI credentials and does the vector work - Essbase is a client
         * of the database's AI features rather than of a model directly.
         *
         * <p>Worth separating from {@link #APPLICATION_NOT_ASSOCIATED} because the web interface
         * does not: it offers to associate an application with a connection and then shows an empty
         * picker reading "No matches found", which looks like a missing application rather than a
         * server that has nothing to pick.
         */
        NO_CONNECTION_CONFIGURED,

        /**
         * The server has at least one AI connection, but this cube's application is not associated
         * with one. Fixed per application.
         */
        APPLICATION_NOT_ASSOCIATED,

        /**
         * The check itself could not be completed - the server was unreachable, or answered in a
         * way this doesn't recognize. {@link #getCause()} has the detail.
         */
        UNDETERMINED

    }

    private final State state;

    private final String explanation;

    private final Throwable cause;

    private EssAiReadiness(State state, String explanation, Throwable cause) {
        this.state = state;
        this.explanation = explanation;
        this.cause = cause;
    }

    public static EssAiReadiness of(State state, String explanation) {
        return new EssAiReadiness(state, explanation, null);
    }

    public static EssAiReadiness undetermined(String explanation, Throwable cause) {
        return new EssAiReadiness(State.UNDETERMINED, explanation, cause);
    }

    public State getState() {
        return state;
    }

    /**
     * A sentence naming the missing link and who fixes it, suitable for showing to a user.
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * The failure behind {@link State#UNDETERMINED}, or null in every other state.
     */
    public Throwable getCause() {
        return cause;
    }

    public boolean isReady() {
        return state == State.READY;
    }

    @Override
    public String toString() {
        return state + ": " + explanation;
    }

}
