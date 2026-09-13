package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssConnection;
import com.appliedolap.essbase.client.ApiClient;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A global connection on the server.
 */
public class EssConnectionImpl extends AbstractEssObject implements EssConnection {

    private final Map<String, Object> connection;

    EssConnectionImpl(ApiContext api, Map<String, Object> connection) {
        super(api);
        this.connection = connection;
    }

    @Override
    public String getName() {
        return text("name");
    }

    @Override
    public Type getType() {
        return Type.CONNECTION;
    }

    @Override
    public ConnectionType getConnectionType() {
        // The kind lives in "subtype"; "type" is the coarser FILE/DB/ESSBASE/BI/AI grouping, and a
        // subtype of FILE under a type of FILE is the common case.
        String subtype = text("subtype");
        return ConnectionType.parse(subtype != null ? subtype : text("type"));
    }

    @Override
    public String getDescription() {
        return text("description");
    }

    @Override
    public String getPath() {
        return text("path");
    }

    @Override
    public String getHost() {
        return text("host");
    }

    /**
     * Tests the saved connection.
     * <p>
     * A failure is an answer rather than an error - the caller asked whether it works - so the
     * server's reason comes back instead of an exception. For a database connection on a server
     * without the right driver that reason is an {@code ORA-} code from Oracle's driver, whatever
     * type the connection claims to be.
     */
    @Override
    public Optional<String> test() {
        try {
            EssConnections.send(api, "POST",
                    "/connections/" + ApiClient.urlEncode(getName()) + "/actions/test", null,
                    "globalConnectionsTestConnectionExisting");
            return Optional.empty();
        } catch (EssApiException e) {
            return Optional.of(e.getMessage());
        }
    }

    @Override
    public void delete() {
        EssConnections.send(api, "DELETE", "/connections/" + ApiClient.urlEncode(getName()), null,
                "globalConnectionsDeleteConnection");
    }

    /** Everything the server said about this connection, for a caller wanting more than the accessors. */
    public Map<String, Object> getProperties() {
        return new LinkedHashMap<>(connection);
    }

    private String text(String key) {
        Object value = connection.get(key);
        return value == null ? null : value.toString();
    }

    @Override
    public String toString() {
        return getName();
    }

}
