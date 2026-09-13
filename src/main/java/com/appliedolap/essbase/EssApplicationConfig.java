package com.appliedolap.essbase;

import java.util.List;
import java.util.Optional;

/**
 * An application's configuration, as a small language for reading and changing it.
 *
 * <pre>
 * application.configuration().keys()                        // the whole catalogue, with values
 * application.configuration().configured()                  // only what this application sets
 * application.configuration().keys("CALCCACHE*")            // the catalogue, filtered
 * application.configuration().get("DATACACHESIZE")          // one key
 * application.configuration().set("DATACACHESIZE", "90M")
 * application.configuration().delete("DATACACHESIZE")
 * </pre>
 *
 * <p>Every key comes back as an {@link EssApplicationConfiguration} that can set or delete itself, so
 * a caller that has found a key doesn't have to hold on to the application to act on it.
 */
public interface EssApplicationConfig {

    /**
     * Every configuration key the server publishes, each with its description and syntax, and with the
     * value where this application sets one.
     *
     * <p>Two calls behind one: the catalogue endpoint never returns values - not even for a key the
     * application has set - and the configurations endpoint returns values without descriptions, so
     * neither alone can answer "what can I set, and what is it now".
     *
     * @return the keys, in the order the server lists them
     */
    List<EssApplicationConfiguration> keys();

    /**
     * The catalogue, narrowed by a key pattern.
     *
     * @param pattern a key or a glob, e.g. {@code CALCCACHE*}; null or blank means everything
     * @return the matching keys
     */
    List<EssApplicationConfiguration> keys(String pattern);

    /**
     * Only the keys this application actually sets.
     *
     * @return the configured keys, with their values
     */
    List<EssApplicationConfiguration> configured();

    /**
     * One key by name, whether or not the application sets it.
     *
     * @param key the key name
     * @return the key, or empty if the server's catalogue has no such key
     */
    Optional<EssApplicationConfiguration> get(String key);

    /**
     * Sets a key, adding it if the application didn't have it and replacing the value if it did.
     *
     * @param key   the key name
     * @param value the value
     * @return the setting as the server records it afterwards
     */
    EssApplicationConfiguration set(String key, String value);

    /**
     * Removes a key from the application's configuration.
     *
     * @param key the key name
     */
    void delete(String key);

}
