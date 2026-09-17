package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssApplicationImpl;
import com.appliedolap.essbase.impl.EssDataSourceImpl;
import com.appliedolap.essbase.impl.EssServerImpl;

import java.io.OutputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface EssServer {

    /**
     * For now, we're setting a generous upper limit on the number of applications that can be returned in a listing. We
     * may want to revisit this in the future. This library is currently designed to hide pagination details so that
     * they don't leak into the abstractions provided in this library, but we may need to rethink this in the future.
     */
    int MAX_APPLICATIONS = 1000;

    String DEFAULT_REST_API_PATH = "/rest/v1";

    String getName();

    EssObject.Type getType();

    /**
     * Fetch the list of applications available on the server for the currently connected user. The number of returned
     * applications is limited to {@value MAX_APPLICATIONS} (the value of {@link #MAX_APPLICATIONS}).
     *
     * @return a list of applications
     */
    List<EssApplication> getApplications();

    /**
     * Gets an application with the given name
     *
     * @param applicationName the application name
     * @return an application object for the application
     */
    EssApplicationImpl getApplication(String applicationName);

    /**
     * Gets the list of files available through the files API. The returned files may include folders.
     *
     * @return the list of files on this server
     */
    List<EssFile> getFiles();

    /**
     * Gets a file using the specified path and name.
     *
     * @param path     the path to the file, such as <code>gallery/Applications/Demo Samples/Block Storage</code>
     * @param filename the name of the file, such as <code>Sample_Basic.xlsx</code>
     * @return the file if it exists, null otherwise
     */
    EssFile getFile(String path, String filename);

    List<EssSession> getSessions();

    /**
     * The server's own OpenAPI/Swagger definition of its REST API.
     * <p>
     * Where it lives depends on the release, which is the reason this exists rather than callers building
     * the URL themselves: 21.7 serves a Swagger 2.0 document at {@code /rest/v1/swagger.json} and answers
     * 404 for {@code openapi.json}, while 26.1 serves an OpenAPI 3.0.1 document at
     * {@code /rest/v1/openapi.json} and answers 404 for {@code swagger.json}. Asking for the wrong one is
     * not a soft failure - it is a 404 - so the candidates are tried in turn.
     *
     * @throws EssApiException if no known location answered
     */
    EssApiSpec getApiSpec();

    /**
     * Everything the server reports about this deployment, exactly as it reports it.
     * <p>
     * Deliberately untyped, unlike {@link #getAboutInstance()}. This endpoint is a bag of capability flags
     * describing one deployment, and which flags exist varies by version: 21.7 answers with
     * {@code idcs} and {@code provisioningSupported}, while 26.1 answers with ten fields including a split
     * of that one into service- and application-role provisioning, several AI feature flags, and a logout
     * URL. A generated model pins the field names at whatever the spec said when it was generated and
     * silently discards the rest, so against a newer server the typed view goes empty precisely when the
     * server has more to say. A map cannot go out of date that way.
     * <p>
     * Values are left as the server sent them - booleans stay booleans - so a caller can test a flag as
     * well as display it. {@code idcs} is the interesting one: it says the deployment authenticates
     * through Oracle's identity service, which is what makes a username and password useless for a
     * federated user.
     *
     * @return the fields the server returned, in the order it returned them
     */
    Map<String, Object> getInstanceDetails();

    /**
     * Everything the server reports about the product, exactly as it reports it.
     * <p>
     * Untyped for the same reason {@link #getInstanceDetails()} is: {@link #getAbout()} names four
     * fields fixed at whatever the spec said when the client was generated, and a 26.1 server answers
     * with five - it adds {@code listingVersion}, which a typed view drops on the floor without
     * saying so. The fields here are whatever came back.
     *
     * @return the fields the server returned, in the order it returned them
     */
    Map<String, Object> getAboutDetails();

    /**
     * How this connection authenticates, so that code outside the generated client can present the same
     * identity - a download bypass, or a local proxy standing in front of the server's own web pages.
     * <p>
     * Returns the live strategy rather than a snapshot: ask it for headers at the moment of the request,
     * because a session-based strategy's answer changes once a session is established, and again once it
     * is signed off.
     */
    EssAuthentication getAuthentication();

    /**
     * Ends this client's own session on the server.
     * <p>
     * Distinct from {@link #killSessions(boolean)}, which ends other people's. This is signing off: the
     * session this client established stops being valid, and the server stops holding it.
     * <p>
     * What happens next depends on how this connection authenticates. With a username and password, the
     * next call simply authenticates again and gets a fresh session. With a session supplied from outside -
     * a federated sign-in, say - there is nothing to fall back to and subsequent calls will be rejected,
     * which is precisely what signing off means in that case.
     */
    void signOff();

    /**
     * When this client's session expires, if the server has said. Empty when there is no session, or when
     * the session was supplied from outside rather than established here - the expiry is reported alongside
     * the session, so only the client that established it hears about it.
     */
    Optional<Instant> getSessionExpiry();

    /**
     * Kill all sessions on the server.
     *
     * @param logoff true to also log them off
     */
    void killSessions(boolean logoff);

    /**
     * Kill all sessions on the server for the given user.
     *
     * @param userId the user ID
     * @param logoff true to also log them off
     */
    void killSessions(String userId, boolean logoff);

    /**
     * Gets the home path of the currently connected user. The value of the home path is returned by a REST API call
     * (curiously, one that returns plaintext instead of JSON but whatever). The value is ostensibly
     * <code>/users/</code> followed by the name of the connected user (e.g. <code>/users/admin</code>).
     *
     * @return a folder object for the user's home path
     */
    EssFolder getHomePath();

    /**
     * The shared folder every user can see, {@code /shared}.
     *
     * <p>The counterpart to {@link #getHomePath()}, which was here without it.
     *
     * @return the shared folder
     */
    EssFolder getSharedPath();

    /**
     * How this server wants large files uploaded - the part size, and how many parts it allows.
     *
     * <p>Rarely needed directly: {@link EssFolder#uploadFile} already reads this and sends a file
     * whichever way the server asks for. Worth having for anything that wants to say in advance what
     * an upload is going to involve.
     *
     * @return the upload configuration
     */
    EssUploadConfig getUploadConfig();

    /**
     * Gets the list of utilities on this server.
     *
     * @return the server utilities
     */
    List<EssUtility> getUtilities();

    /**
     * Gets the list of jobs on this server.
     *
     * @return the list of jobs
     */
    List<EssJob> getJobs();

    /**
     * Gets a list of groups on this server
     *
     * @return the list of groups
     */
    List<EssGroup> getGroups();

    /**
     * Creates a group.
     *
     * <p>Only on a server that owns its own security; a deployment behind an external identity provider
     * keeps its groups in the provider and refuses this, the same as it refuses {@link #getGroups()}.
     *
     * @param name the group name, which is also its id
     * @param description what the group is for, or null
     * @param role the service-level role it grants its members - {@code User}, {@code Power User} or
     *             {@code Service Administrator}. Required: the server rejects a group without one.
     * @return the group as the server created it
     */
    EssGroup createGroup(String name, String description, String role);

    /**
     * One group by name.
     *
     * @param name the group name
     * @return the group
     * @throws com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException if there is no such group
     */
    EssGroup getGroup(String name);

    /**
     * The users this server holds.
     *
     * <p>Only meaningful where Essbase owns its own security. A deployment behind an external identity
     * provider - {@code idcs} in {@link #getInstanceDetails()} - keeps its users in the provider and
     * answers HTTP 400 "This operation is not supported. Contact administrator" here, the same as it
     * does for {@link #getGroups()}. Check the flag before asking, rather than treating the refusal as
     * a failure.
     *
     * @return the users, where the server has any to report
     */
    List<EssUser> getUsers();

    /**
     * Who holds which service-level role - Service Administrator, Power User, User.
     *
     * <p>Unlike {@link #getUsers()} and {@link #getGroups()}, this answers on a deployment behind an
     * external identity provider: the directory lives there, but the role assignments are Essbase's
     * own. It is the one way to see who can do what on such a server.
     *
     * @return the service role assignments
     */
    List<EssPermission> getPermissions();

    /**
     * Gets server-scoped variables.
     *
     * @return the server-wide variables
     */
    List<EssServerVariable> getVariables();

    /**
     * Creates a new server-wide variable with the given name and value.
     *
     * @param name  the name of the variable
     * @param value the value of the variable
     */
    EssServerVariable createVariable(String name, String value);

    /**
     * Gets the "about" information for this server.
     *
     * @return the server about info
     */
    EssServerImpl.About getAbout();

    EssServerImpl.AboutInstance getAboutInstance();

    /**
     * Creates an application (and a database) with the given names. While we tend to historically think of Essbase as
     * employing the concept of an application containing one or more databases/cubes, most of the operations in the
     * REST API are centered around actions you do on a particular cube, and in cases like these where you are creating
     * a cube, there is no separate application creation step, it just gets created or re-used as the case may be.
     *
     * <p>The default database creation options will be BSO cube with scenarios and duplicates turned off. For more
     * granular control of the created database type, use
     * {@link #createApplication(String, String, EssServerImpl.DatabaseCreateOptions)}.
     *
     * @param applicationName the application name
     * @param databaseName    the database name
     */
    void createApplication(String applicationName, String databaseName);

    /**
     * Create an application/database with the given name and options.
     *
     * @param applicationName       the name of the application
     * @param databaseName          the name of the database/cube
     * @param databaseCreateOptions the database creation options
     */
    void createApplication(String applicationName, String databaseName, EssServerImpl.DatabaseCreateOptions databaseCreateOptions);

    /**
     * Creates or updates an application from an uploaded workbook.
     *
     * @param application the application
     * @param database    the cube/database
     * @param file        the XLSX file
     * @return a new job for the creation process
     */
    EssJob createApplicationFromWorkbook(String application, String database, EssFile file);

    /**
     * Returns the list of URLs known to this server. Generally speaking this seems to be the URL for the Jet UI, REST
     * API, XMLA provider, and some others.
     *
     * @return list of URLs from the corresponding API
     */
    List<EssURL> getURLs();

    /**
     * Get the list of global data sources defined on the server.
     *
     * @return list of global data sources
     */
    /**
     * The global connections defined on this server.
     *
     * @return the connections
     */
    List<EssConnection> getConnections();

    /**
     * One connection by name.
     *
     * @param name the connection name
     * @return the connection, or empty if there is none
     */
    java.util.Optional<EssConnection> getConnection(String name);

    /**
     * Creates a connection to a file in the Essbase catalogue.
     *
     * <p>The path names a <em>file</em>, not the folder holding it - a file connection stands for one
     * file, and the data source built on it carries no filename of its own.
     *
     * @param name        the connection name
     * @param catalogPath the file, e.g. {@code /gallery/Technical/Filters/UserDetails.csv}
     * @param description optional description
     * @return the connection as created
     */
    EssConnection createFileConnection(String name, String catalogPath, String description);

    /**
     * Tests a connection that hasn't been saved, so a caller can check before committing.
     *
     * @param name        the connection name to test under
     * @param catalogPath the file to point at
     * @return empty when it connects, otherwise the server's reason
     */
    java.util.Optional<String> testFileConnection(String name, String catalogPath);

    /**
     * Removes a global data source.
     *
     * @param name the data source name
     */
    void deleteDataSource(String name);

    List<EssDataSource> getDataSources();

    /**
     * Get the data source with the given name. Will throw
     * {@link com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException} if there is no data source with that
     * name.
     *
     * @param dataSourceName the data source name
     * @return the data source with that name
     */
    EssDataSource getDataSource(String dataSourceName);

    /**
     * Calls the global data source endpoint to execute a query against a data source with the given parameters.
     *
     * @param query          the query
     * @param includeHeaders whether to include headers in the result
     * @param delimiter      the delimiter (currently only space and tab are supported by the server, you can use
     *                       constants in {@link EssDataSourceImpl} for convenience)
     * @param params         the parameters, if any. If none, supply an empty map
     * @param outputStream   the output stream to write results to
     */
    // TODO: get the metadataOnly flag into the OpenAPI call; it exists but is not getting generated. It causes you to get JDBC headers only but with no data
    void streamDataSource(String query, boolean includeHeaders, String delimiter, Map<String, Object> params, OutputStream outputStream);

    /**
     * A cube type, despite the name.
     */
    public enum ApplicationType {

        /**
         * ASO/Aggregate Storage Option
         */
        ASO("A"),

        /**
         * BSO/Block Storage Option (including hybrid)
         */
        BSO("B");

        private final String code;

        ApplicationType(String code) {
            this.code = code;
        }

        /**
         * This is private because we don't want this shitty abbreviation leaking into the public API. Note: this might
         * not be the case after all. Not sure, leaving in for now
         *
         * @return the code for the database type as used in the create request
         */
        private String getCode() {
            return code;
        }

    }


    /**
     * The kinds of server log this deployment offers, each a value for the other two methods here.
     *
     * <p>Empty rather than an error when the server has no server-level logs at all. They are not
     * universal: both servers this was developed against answer 404 to every {@code /logs} path while
     * serving application logs perfectly well, so "this deployment doesn't have them" is an ordinary
     * answer and not a failure. A caller should ask this before offering the feature.
     *
     * @return the server log types, empty if this deployment serves none
     */
    List<String> getServerLogTypes();

    /**
     * Writes the latest server log of the given type to the stream.
     *
     * @param serverType one of {@link #getServerLogTypes()}
     * @param outputStream where to write it
     */
    void downloadLatestServerLog(String serverType, OutputStream outputStream);

    /**
     * Writes every server log of the given type, as a zip, to the stream.
     *
     * @param serverType one of {@link #getServerLogTypes()}
     * @param outputStream where to write it
     */
    void downloadServerLogsAsZip(String serverType, OutputStream outputStream);

    /**
     * The MDX functions this server understands, in the server's own groups.
     *
     * <p>A property of the server's MDX dialect, not of any cube - the same server answers
     * byte-for-byte identically whichever cube is asked. There is no server-level endpoint for it
     * though, so this finds a cube and asks through that; a stopped application will do, since the
     * answer does not depend on the data.
     *
     * @return the function groups, in the order the server listed them
     * @throws EssApiException if the server has no cube to ask through
     */
    List<EssMdxFunctionGroup> getMdxFunctions();

    /**
     * The MCP server built into this Essbase, if it has one.
     *
     * <p>Points the opposite way from {@link EssCube#getAi()}: this is Essbase offering itself as a
     * tool provider to somebody else's model, rather than Essbase calling out to one. A server can
     * have this working while every {@code /ai} endpoint is unconfigured, and usually does.
     *
     * @return the MCP server, which answers {@code isAvailable()} false on anything before 26.1
     */
    EssMcp getMcp();

    /**
     * What the server reports about the machine it runs on - free disk and memory, and any configured
     * ceilings. The only window the REST API offers onto the host.
     *
     * @return the limits
     */
    EssMaintenanceLimits getMaintenanceLimits();

    /**
     * How each ODL log handler rotates.
     *
     * <p>Worth having beside the logs themselves: a handler holding ten megabytes per file and five
     * hundred in total has silently discarded everything older, and nothing in the log says so.
     *
     * @return one entry per handler
     */
    List<EssLogSetting> getLogSettings();

    /**
     * Changes how the log handlers rotate.
     *
     * <p>Send every handler, not only the changed one - this replaces the set. Start from
     * {@link #getLogSettings()} and use {@link EssLogSetting#withSizes} on the one being altered.
     *
     * @param settings the full set of handlers
     */
    void setLogSettings(List<EssLogSetting> settings);

}
