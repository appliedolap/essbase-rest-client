package com.appliedolap.essbase;

/**
 * How the server wants large files uploaded.
 *
 * <p>Published by the server rather than guessed at, which matters because the answer decides
 * whether a file can go up in one request at all: past the part size there is a three-call protocol
 * to follow instead.
 */
public final class EssUploadConfig {

    private final int partSizeMegabytes;

    private final int maxParts;

    private final int retryCount;

    private final long maxFileSizeBytes;

    public EssUploadConfig(int partSizeMegabytes, int maxParts, int retryCount, long maxFileSizeBytes) {
        this.partSizeMegabytes = partSizeMegabytes;
        this.maxParts = maxParts;
        this.retryCount = retryCount;
        this.maxFileSizeBytes = maxFileSizeBytes;
    }

    /** How big each part should be, in megabytes - ten, on the servers seen so far. */
    public int getPartSizeMegabytes() {
        return partSizeMegabytes;
    }

    /** The size in bytes, which is what anything measuring a file actually needs. */
    public long getPartSizeBytes() {
        return (long) partSizeMegabytes * 1024L * 1024L;
    }

    /** How many parts one upload may have. Ten thousand parts of ten megabytes is a hundred gigabytes. */
    public int getMaxParts() {
        return maxParts;
    }

    public int getRetryCount() {
        return retryCount;
    }

    /** The largest file the server will take, or -1 where it sets no limit. */
    public long getMaxFileSizeBytes() {
        return maxFileSizeBytes;
    }

    /** Whether a file of this size has to be sent in parts. */
    public boolean needsParts(long fileSizeBytes) {
        return fileSizeBytes > getPartSizeBytes();
    }

    @Override
    public String toString() {
        return partSizeMegabytes + "MB parts, up to " + maxParts;
    }

}
