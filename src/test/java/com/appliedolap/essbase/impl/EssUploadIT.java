package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssFile;
import com.appliedolap.essbase.EssFolder;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssUploadConfig;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Pins the part-upload protocol, which is worth pinning because almost none of it is written down.
 *
 * <p>The specification declares no request body for the commit, and committing without one answers
 * HTTP 500 "Request failed." with nothing further. The body is in fact a map of part number to the
 * etag each part returned, a shape discovered only by sending a list and reading the server's
 * complaint - {@code Cannot deserialize value of type LinkedHashMap<Integer, String>}. Nothing
 * guards that but this.
 */
@Category(DestructiveIntegrationTest.class)
public class EssUploadIT {

    /** Distinctive enough that a leftover is obviously ours if a run dies mid-way. */
    private static final String SMALL = "cessna_upload_it_small.txt";

    private static final String LARGE = "cessna_upload_it_large.txt";

    private final EssServer server = ConnectionUtils.server();

    private final EssFolder home = server.getHomePath();

    @After
    public void removeWhateverSurvived() {
        find(SMALL).ifPresent(EssFile::delete);
        find(LARGE).ifPresent(EssFile::delete);
    }

    @Test
    public void configSaysHowLargeAPartIs() {
        EssUploadConfig config = server.getUploadConfig();
        assertTrue("a part size of zero would make every file need infinite parts",
                config.getPartSizeMegabytes() > 0);
        assertTrue(config.getMaxParts() > 0);
        assertTrue("a file of one byte cannot need splitting", !config.needsParts(1));
        assertTrue("a file past the part size must", config.needsParts(config.getPartSizeBytes() + 1));
    }

    /** Below the part size: one request, and the file arrives whole. */
    @Test
    public void uploadsASmallFileInOneRequest() throws IOException {
        File local = write(SMALL, 1024);
        home.uploadFile(local);
        assertTrue("the small file should be in the catalogue", find(SMALL).isPresent());
    }

    /**
     * Past the part size, which is the path that did not exist before: register, send each part with
     * its etag collected, commit with the map of them.
     */
    @Test
    public void uploadsALargeFileInParts() throws IOException {
        EssUploadConfig config = server.getUploadConfig();
        Assume.assumeTrue("this server wants everything in one request", config.getPartSizeBytes() > 0);

        long size = config.getPartSizeBytes() + (config.getPartSizeBytes() / 2);
        File local = write(LARGE, size);
        assertTrue("the test file has to exceed the part size to exercise the protocol",
                config.needsParts(local.length()));

        home.uploadFile(local);

        Optional<EssFile> uploaded = find(LARGE);
        assertTrue("the large file should be in the catalogue", uploaded.isPresent());
        assertEquals("and it should come back the size it went up", size, downloadedSize(uploaded.get()));
    }

    private long downloadedSize(EssFile file) throws IOException {
        File downloaded = File.createTempFile("cessna-upload-it", ".txt");
        downloaded.deleteOnExit();
        return file.downloadTo(downloaded).length();
    }

    /** Text rather than random bytes: the catalogue refuses extensions it doesn't recognise. */
    private static File write(String name, long bytes) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir"), name);
        try (RandomAccessFile writer = new RandomAccessFile(file, "rw")) {
            writer.setLength(0);
            byte[] line = "cessna upload integration test padding line\n".getBytes(StandardCharsets.UTF_8);
            while (writer.length() < bytes) {
                writer.write(line, 0, (int) Math.min(line.length, bytes - writer.length()));
            }
        }
        file.deleteOnExit();
        return file;
    }

    private Optional<EssFile> find(String name) {
        return home.getFiles().stream().filter(file -> name.equals(file.getName())).findFirst();
    }

}
