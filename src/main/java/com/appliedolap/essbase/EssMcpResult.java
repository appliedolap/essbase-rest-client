package com.appliedolap.essbase;

/**
 * What a tool call came back with.
 *
 * <p>A failure is not an exception here. The MCP endpoint answers HTTP 200 with
 * {@code isError: true} and the reason as text, so the reason is ordinary content and treating it as
 * a transport failure would throw away the only description of what went wrong.
 */
public final class EssMcpResult {

    private final boolean error;

    private final String text;

    public EssMcpResult(boolean error, String text) {
        this.error = error;
        this.text = text;
    }

    public boolean isError() {
        return error;
    }

    /** Whatever the tool returned - usually JSON, and on failure a message. */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return (error ? "error: " : "") + text;
    }

}
