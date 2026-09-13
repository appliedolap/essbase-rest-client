package com.appliedolap.essbase;

import java.util.Locale;
import java.util.Optional;

/**
 * A named, reusable pointer to somewhere data lives, plus how to reach it.
 *
 * <p>Deliberately says nothing about <em>which</em> data - that is an {@link EssDataSource}, which is
 * built on top of one of these. The split is about lifetime: credentials and locations change rarely
 * and are sensitive, queries change constantly, and many data sources can share one connection.
 *
 * <p>A file connection names a single file in the catalogue, not a folder.
 */
public interface EssConnection extends EssObject {

    /**
     * The kinds of connection Essbase models. The enum is the server's own, and it is strict - it
     * rejects any spelling but these, case included.
     */
    enum ConnectionType {

        FILE(true), EXCELFILE(true), DELIMITEDFILE(true), FIXEDWIDTHFILE(true), TEMPLATE(true),
        ORACLE(false), MYSQL(false), MS_SQL(false), DB2(false), SPARK(false), JDBC(false), DB(false),
        ESSBASE(true), BI(false), AI(false),

        /** A type this client doesn't name. */
        UNKNOWN(false);

        private final boolean file;

        ConnectionType(boolean file) {
            this.file = file;
        }

        /** Whether this kind reads from the Essbase file catalogue rather than an outside system. */
        public boolean isFile() {
            return file;
        }

        /**
         * Whether this kind needs a JDBC driver on the server.
         *
         * <p>Worth asking before offering one: an Essbase deployment carrying only Oracle's driver
         * answers every non-Oracle attempt with an {@code ORA-} code from the wrong driver, which is
         * not a diagnosis anyone can act on.
         */
        public boolean isDatabase() {
            return this == ORACLE || this == MYSQL || this == MS_SQL || this == DB2
                    || this == SPARK || this == JDBC || this == DB;
        }

        public static ConnectionType parse(String text) {
            if (text == null) {
                return UNKNOWN;
            }
            for (ConnectionType type : values()) {
                if (type.name().equalsIgnoreCase(text.trim())) {
                    return type;
                }
            }
            return UNKNOWN;
        }

        /** The value the API wants, which is the constant's own name in upper case. */
        public String getParameter() {
            return name().toUpperCase(Locale.ROOT);
        }

    }

    ConnectionType getConnectionType();

    String getDescription();

    /**
     * For a file connection, the catalogue path of the file - {@code /gallery/Technical/Filters/UserDetails.csv}.
     * Null for a connection that isn't file-based.
     *
     * @return the path, or null
     */
    String getPath();

    /** The host a database connection points at, or null for a file connection. */
    String getHost();

    /**
     * Checks the connection without saving anything.
     *
     * @return empty when it connects, otherwise the server's reason
     */
    Optional<String> test();

    void delete();

}
