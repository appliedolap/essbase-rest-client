package com.appliedolap.essbase;

/**
 * One application configuration setting - a key from Essbase's catalogue, what it is for, and the
 * value this application gives it if any.
 *
 * <p>Reached through {@link EssApplication#configuration()}. A key exists whether or not it is set:
 * the server publishes a fixed catalogue of them (64 on 26.1) with a description, a syntax and a
 * worked example each, and an application configures whichever subset it needs. So this type stands
 * for a key, and {@link #isConfigured()} says whether this application has given it a value.
 */
public interface EssApplicationConfiguration {

    /**
     * Gets the application that this configuration item is associated with.
     *
     * @return the parent application
     */
    EssApplication getApplication();

    /**
     * Gets the key for this configuration property.
     *
     * @return the configuration key name
     */
    String getKey();

    /**
     * Gets the value for this configuration property, or null when this application doesn't set it.
     *
     * @return the configuration value, or null
     */
    String getValue();

    /**
     * What the setting does, in Essbase's own words.
     *
     * @return the description, or null for a key not in the server's catalogue
     */
    String getDescription();

    /**
     * How the setting is written, e.g. {@code DATACACHESIZE n} or
     * {@code CALCCACHE [dbname] TRUE | FALSE}. The square brackets and bars are Essbase's notation,
     * not something to send.
     *
     * @return the syntax, or null
     */
    String getSyntax();

    /**
     * A filled-in example, e.g. {@code DATACACHESIZE 90M}. Worth showing beside the syntax, since the
     * syntax alone rarely settles what a value should look like.
     *
     * @return the example, or null
     */
    String getExample();

    /**
     * Whether this application sets this key.
     *
     * <p>Derived from whether the key appears among the application's configurations, and deliberately
     * not from the server's own {@code configured} field, which is always false - including on the very
     * keys returned by {@code ?configured=true}. A client trusting that field reports an application
     * with settings as having none.
     *
     * @return true if this application gives the key a value
     */
    boolean isConfigured();

    /**
     * Sets this key on the application, adding it if it wasn't there and replacing the value if it was.
     *
     * @param value the value, written as {@link #getSyntax()} describes
     * @return this setting as the server records it afterwards
     */
    EssApplicationConfiguration set(String value);

    /**
     * Removes this key from the application's configuration, leaving the setting at whatever default
     * Essbase applies.
     */
    void delete();

}
