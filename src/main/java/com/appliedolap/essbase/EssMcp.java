package com.appliedolap.essbase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The MCP server built into Essbase 26.1, reached through {@link EssServer#getMcp()}.
 *
 * <p>This is the integration that points the other way from {@link EssCubeAi}: there, Essbase is the
 * client and a model answers it; here, Essbase is the tool provider and the model is somebody else's
 * - which is what lets ChatGPT query a cube directly. The two share nothing but the word AI, and a
 * server can perfectly well have this working while every {@code /ai} endpoint is unconfigured.
 *
 * <p>Spoken to over the plain endpoints ({@code /ess-mcp}, {@code /ess-mcp/tools},
 * {@code /ess-mcp/call}) rather than the JSON-RPC interface the same path also serves. Both work and
 * return the same envelopes; these are three ordinary requests instead of a protocol.
 *
 * <p>Some tools relay the caller's session to Essbase rather than re-presenting credentials, so they
 * fail with an internal 401 for a caller using HTTP Basic and nothing else. That never arises
 * through this library, which authenticates and keeps a session - but it is worth knowing when the
 * same call made by hand behaves differently.
 */
public interface EssMcp {

    /**
     * Whether this server has an MCP server at all. False on anything older than 26.1, which answers
     * 404 to the discovery endpoint.
     *
     * @return true if the endpoint answered
     */
    boolean isAvailable();

    /**
     * What the MCP server calls itself, e.g. {@code Essbase MCP 0.2.3}.
     *
     * @return the name and version, or empty if there is no MCP server here
     */
    Optional<String> getServerInfo();

    /**
     * The tools it offers.
     *
     * @return the tools, empty if there is no MCP server here
     */
    List<EssMcpTool> getTools();

    /**
     * Runs one tool.
     *
     * <p>Arguments are sent as given; the endpoint validates them. A tool that fails comes back as a
     * result with {@link EssMcpResult#isError()} set rather than as an exception.
     *
     * @param toolName which tool
     * @param arguments its arguments, by name
     * @return what it said
     */
    EssMcpResult call(String toolName, Map<String, Object> arguments);

}
