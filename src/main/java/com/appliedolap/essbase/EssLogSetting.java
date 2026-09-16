package com.appliedolap.essbase;

/**
 * How one ODL log handler rotates - which is the answer to why a log stops where it does.
 *
 * <p>A companion to reading the logs themselves: a handler keeping ten megabytes per file and five
 * hundred in total has thrown away everything older, and nothing in the log says so.
 */
public final class EssLogSetting {

    private final String handlerName;

    private final String logType;

    private final Integer maxLogSize;

    private final Integer maxAllLogSize;

    public EssLogSetting(String handlerName, String logType, Integer maxLogSize, Integer maxAllLogSize) {
        this.handlerName = handlerName;
        this.logType = logType;
        this.maxLogSize = maxLogSize;
        this.maxAllLogSize = maxAllLogSize;
    }

    /** Which handler, e.g. {@code serverhandler} or {@code jagent-handler-text}. */
    public String getHandlerName() {
        return handlerName;
    }

    /** How it rotates, e.g. {@code SIZEBASED}. */
    public String getLogType() {
        return logType;
    }

    /**
     * The size one file may reach before rotating. The server sends these as strings and takes them
     * back the same way; they are numbers here because that is what they are and what an editor has
     * to validate.
     */
    public Integer getMaxLogSize() {
        return maxLogSize;
    }

    /** The size every file for this handler may reach together, before the oldest are discarded. */
    public Integer getMaxAllLogSize() {
        return maxAllLogSize;
    }

    /** A copy with different sizes, for handing back to {@link EssServer#setLogSettings}. */
    public EssLogSetting withSizes(Integer newMaxLogSize, Integer newMaxAllLogSize) {
        return new EssLogSetting(handlerName, logType, newMaxLogSize, newMaxAllLogSize);
    }

    @Override
    public String toString() {
        return handlerName + " " + logType + " " + maxLogSize + "/" + maxAllLogSize;
    }

}
