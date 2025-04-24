package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssServerImpl;

import java.io.OutputStream;
import java.util.List;
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

    AbstractEssObject.Type getType();

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
    EssApplication getApplication(String applicationName);

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
     * Gets server-scoped variables.
     *
     * @return the server-wide variables
     */
    List<EssVariable> getVariables();

    /**
     * Creates a new server-wide variable with the given name and value.
     *
     * @param name  the name of the variable
     * @param value the value of the variable
     */
    void createVariable(String name, String value);

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
     *                       constants in {@link EssDataSource} for convenience)
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

}