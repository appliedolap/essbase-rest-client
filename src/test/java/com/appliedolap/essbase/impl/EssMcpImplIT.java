package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssMcp;
import com.appliedolap.essbase.EssMcpResult;
import com.appliedolap.essbase.EssMcpTool;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Read-only. Listing tools changes nothing, and the one tool called here only reads.
 *
 * <p>Written to pass on a server without an MCP server as well as one with, because the absence is a
 * supported answer rather than a failure - anything before 26.1 answers 404 to discovery, and the
 * point of {@link EssMcp#isAvailable()} is to make that sayable.
 */
@Category(ReadOnlyIntegrationTest.class)
public class EssMcpImplIT {

    private final EssMcp mcp = ConnectionUtils.server().getMcp();

    @Test
    public void absenceIsAnAnswerRatherThanAFailure() {
        if (!mcp.isAvailable()) {
            assertTrue("a server with no MCP server offers no tools", mcp.getTools().isEmpty());
            assertTrue("and names none", mcp.getServerInfo().isEmpty());
        } else {
            assertTrue("a server with one says what it is", mcp.getServerInfo().isPresent());
        }
    }

    @Test
    public void toolsDescribeThemselves() {
        Assume.assumeTrue("no MCP server on this Essbase", mcp.isAvailable());

        List<EssMcpTool> tools = mcp.getTools();
        assertFalse("an MCP server with no tools would be pointless", tools.isEmpty());
        for (EssMcpTool tool : tools) {
            assertNotNull(tool.getName());
            assertFalse("every tool is named", tool.getName().isBlank());
            // Required-first ordering, which is what a caller filling arguments in wants.
            boolean seenOptional = false;
            for (com.appliedolap.essbase.EssMcpParameter parameter : tool.getParameters()) {
                if (!parameter.isRequired()) {
                    seenOptional = true;
                } else {
                    assertFalse(tool.getName() + " lists a required argument after an optional one",
                            seenOptional);
                }
            }
        }
    }

    /**
     * A failing tool comes back as a result rather than an exception - the endpoint answers HTTP 200
     * with isError set, so a client that treated it as a transport failure would throw away the only
     * description of what went wrong.
     */
    @Test
    public void aFailingToolIsAResultNotAnException() {
        Assume.assumeTrue("no MCP server on this Essbase", mcp.isAvailable());

        EssMcpResult result = mcp.call("no_such_tool_exists", Map.of());
        assertTrue("an unknown tool is reported as an error result", result.isError());
        assertNotNull("with something to show for it", result.getText());
    }

}
