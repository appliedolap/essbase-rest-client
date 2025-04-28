package com.appliedolap.essbase.util;

import com.appliedolap.essbase.EssObject;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;

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
     * Checks if the given collection is not null and has at least one element.
     *
     * @param collection the collection
     * @return true if it's not empty, false otherwise
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
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

    /**
     * Helper function to get a single object with a particular name from a list of "Ess" objects. For example,
     * used to narrow down a list of cubes from an application. Most if not all callers of this method have a paradigm
     * where the list of general objects is fetched from the server, which contains some but not all details of that object,
     * and the caller just wants a single one of those objects, which will simply be created by filtering for it from the
     * full list.
     *
     * <p>Fetching the whole list isn't necessarily the most efficient operation, but it fits in to this top-down object
     * model very nicely so that's what we have for now.</p>
     *
     * @param items the list of items to iterate
     * @param name the name of the item to look for (e.g. the <code>getName()</code> of the EssObject will be compared to this
     * @param type the type of the object, not used to filter but used if an exception is thrown
     * @param <E> type filter, the list must extend from EssObject
     * @return a single item with the given name, exception thrown otherwise
     */
    public static <E extends EssObject> E getWithName(Iterable<E> items, String name, EssObject.Type type) {
        for (E item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new NoSuchEssbaseObjectException(name, type);
    }

}