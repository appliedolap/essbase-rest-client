package com.appliedolap.essbase.util;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public class Utils {

    private Utils() {}

    /**
     * Utility method that provides some null-safety against sets that may not be initialized.
     *
     * @param collection the collection
     * @param <T> the type
     * @return the unmodified collection if it exists, an empty collection otherwise
     */
    public static <T> Collection<T> wrap(Collection<T> collection) {
        return collection != null ? collection : Collections.emptyList();
    }

    /**
     * Null-safe instant creator, defaults to epoch if input is null
     *
     * @param millis the time millis
     * @return an instant for the millis
     */
    public static Instant instant(Long millis) {
        if (millis != null) {
            return Instant.ofEpochMilli(millis);
        }
        return Instant.EPOCH;
    }

}