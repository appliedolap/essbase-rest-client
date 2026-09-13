package com.appliedolap.essbase;

import java.util.Locale;

/**
 * A calculation or MDX script on a cube.
 *
 * <p>Both kinds live behind one set of endpoints, told apart by a {@code file} query parameter, so
 * they are one type here too - see {@link ScriptType}.
 */
public interface EssScript extends EssObject {

    /**
     * Which kind of script this is, which decides both the endpoint's {@code file} parameter and how
     * the script is run.
     */
    enum ScriptType {

        /** A calculation script. */
        CALC("calc", ".csc", "Calc Script"),

        /** An MDX script. */
        MDX("mdx", ".mdx", "MDX Script");

        private final String parameter;

        private final String extension;

        private final String label;

        ScriptType(String parameter, String extension, String label) {
            this.parameter = parameter;
            this.extension = extension;
            this.label = label;
        }

        /**
         * The value the scripts endpoints want in {@code file}. Sending anything else - including an
         * empty string, which is what a Swagger UI form does with a blank field - is rejected with
         * "Invalid file type. Supported files are Calculation and MDX scripts".
         *
         * @return the parameter value
         */
        public String getParameter() {
            return parameter;
        }

        /**
         * The file extension Essbase stores the script under. Needed because running an MDX script
         * identifies it by file name rather than by script name - see {@link EssScript#execute()}.
         *
         * @return the extension, dot included
         */
        public String getExtension() {
            return extension;
        }

        /** The kind as a person would write it. */
        public String getLabel() {
            return label;
        }

        public static ScriptType parse(String text) {
            String name = text == null ? "" : text.trim().toUpperCase(Locale.ROOT);
            for (ScriptType type : values()) {
                if (type.name().equals(name) || type.parameter.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Not a script type: " + text);
        }

    }

    /**
     * Which kind of script this is.
     *
     * @return the type, never null
     */
    ScriptType getScriptType();

    /**
     * The cube this script belongs to.
     *
     * @return the cube
     */
    EssCube getCube();

    Long getModifiedTime();

    Long getSize();

    /**
     * The script's text, fetched from the server.
     *
     * @return the content
     */
    String getContent();

    /**
     * Replaces the script's text on the server.
     *
     * @param content the new text
     */
    void save(String content);

    /**
     * Checks the script without running it.
     *
     * <p>Worth doing before a save as well as before a run: Essbase validates against the live
     * outline, so it catches a member name that no longer exists.
     *
     * @return empty when the script is valid, otherwise the server's complaint
     */
    java.util.Optional<String> validate();

    void delete();

    /**
     * Runs the script and waits for it.
     *
     * <p>A calculation and an MDX script are both jobs, but not the same one and not parameterised
     * the same way - a calc is identified by its bare name and an MDX script by its file name. That
     * difference is handled here rather than by the caller.
     *
     * @throws EssApiException if the script fails, carrying the server's reason
     */
    void execute();

}
