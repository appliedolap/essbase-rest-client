package com.appliedolap.essbase;

/**
 * How much of one system resource is free, and what the configured ceiling is.
 *
 * <p>The server states no units anywhere - not in the response, not in the specification. The values
 * only make sense as megabytes on the deployments this was tried against (about a terabyte of disk,
 * about forty gigabytes of memory), so that is how Cessna labels them, but it is a reading rather
 * than a documented fact.
 */
public final class EssResourceLimit {

    private final String id;

    private final Integer available;

    private final Integer limit;

    public EssResourceLimit(String id, Integer available, Integer limit) {
        this.id = id;
        this.available = available;
        this.limit = limit;
    }

    /** {@code DISK} or {@code RAM}. */
    public String getId() {
        return id;
    }

    public Integer getAvailable() {
        return available;
    }

    /** The configured ceiling, where one is set - zero appears to mean no limit. */
    public Integer getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return id + ": " + available + " available, limit " + limit;
    }

}
