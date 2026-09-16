package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssUploadConfig;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Sending a file to the catalogue, in one request or in parts.
 *
 * <p>The server publishes which it wants, at {@code /files/uploadconfig}: past the part size there
 * is a three-call protocol - register the upload, send each part, commit - and a fourth call to
 * abandon it. None of it is in the generated client, which is built from a 21.7 specification that
 * predates the whole thing.
 *
 * <p>Neither path reads the file into memory. The version this replaced did
 * {@code Files.readAllBytes} before a single-request PUT, which is fine for a rules file and an
 * OutOfMemoryError for the backup somebody actually needed to upload.
 */
final class EssUploads {

    private static final Logger logger = LoggerFactory.getLogger(EssUploads.class);

    private EssUploads() {
    }

    static EssUploadConfig config(ApiContext api) {
        JsonNode root = getJson(api, "/files/uploadconfig");
        return new EssUploadConfig(
                root.path("uploadPartSizeInMB").asInt(10),
                root.path("maxNumAllowedParts").asInt(10_000),
                root.path("uploadeRetryCount").asInt(3),
                root.path("maxFileSize").asLong(-1));
    }

    /**
     * Uploads a file to the given catalogue path, choosing whichever way the server wants.
     *
     * @param api the context
     * @param catalogPath where it should land, with a leading slash
     * @param file the local file
     */
    static void upload(ApiContext api, String catalogPath, java.io.File file) {
        long size = file.length();
        EssUploadConfig config = config(api);
        if (config.getMaxFileSizeBytes() > 0 && size > config.getMaxFileSizeBytes()) {
            throw new EssApiException("This server will not accept a file of " + size
                    + " bytes; its limit is " + config.getMaxFileSizeBytes() + ".");
        }
        if (config.needsParts(size)) {
            uploadInParts(api, catalogPath, file, config);
        } else {
            uploadWhole(api, catalogPath, file);
        }
    }

    /** One PUT, streamed from disk rather than buffered. */
    private static void uploadWhole(ApiContext api, String catalogPath, java.io.File file) {
        String path = NativeHttp.withQuery("/files/" + NativeHttp.encodePathKeepingSlashes(catalogPath),
                "overwrite", true);
        try {
            NativeHttp.sendAndDiscard(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/json, application/xml")
                    .header("Content-Type", "application/octet-stream")
                    .PUT(HttpRequest.BodyPublishers.ofFile(file.toPath())), "filesAddFile");
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Register, send each part, commit - and abandon the whole thing if any part fails.
     *
     * <p>The abort matters more than it looks: a registered upload that is never committed or
     * abandoned leaves its parts on the server for {@code purgePartsAfterDays}, a week on the servers
     * seen here. Failing without cleaning up would quietly consume space for days.
     */
    private static void uploadInParts(ApiContext api, String catalogPath, java.io.File file,
            EssUploadConfig config) {
        long partSize = config.getPartSizeBytes();
        long parts = (file.length() + partSize - 1) / partSize;
        if (parts > config.getMaxParts()) {
            throw new EssApiException("This file needs " + parts + " parts of "
                    + config.getPartSizeMegabytes() + "MB, and this server allows at most "
                    + config.getMaxParts() + ".");
        }

        String encoded = NativeHttp.encodePathKeepingSlashes(catalogPath);
        String uploadId = createUpload(api, encoded);
        logger.info("Uploading {} in {} parts as {}", file.getName(), parts, uploadId);
        try {
            commit(api, encoded, uploadId, sendParts(api, encoded, file, partSize, parts, uploadId));
        } catch (RuntimeException e) {
            abortQuietly(api, encoded, uploadId);
            throw e;
        }
    }

    private static String createUpload(ApiContext api, String encodedPath) {
        String path = NativeHttp.withQuery("/files/upload-create/" + encodedPath, "overwrite", true);
        try {
            String body = NativeHttp.sendForString(api.getClient(),
                    NativeHttp.request(api.getClient(), path)
                            .header("Accept", "application/json")
                            // Required, and a header rather than a query parameter, unlike overwrite.
                            .header("append", "false")
                            .POST(HttpRequest.BodyPublishers.noBody()),
                    "filesCreateUpload");
            String uploadId = api.getClient().getObjectMapper().readTree(body).path("uploadId").asText(null);
            if (uploadId == null || uploadId.isBlank()) {
                throw new EssApiException("The server registered the upload but returned no id: " + body);
            }
            return uploadId;
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Sends each part and collects the etag the server answers with.
     *
     * <p>Reads each part straight off the file rather than holding the whole thing, so memory stays
     * flat however big the upload is - one part at a time, never the file.
     *
     * @return part number to etag, in order, which is what the commit has to hand back
     */
    private static Map<String, String> sendParts(ApiContext api, String encodedPath, java.io.File file,
            long partSize, long parts, String uploadId) {
        Map<String, String> etags = new LinkedHashMap<>();
        Path source = file.toPath();
        for (long part = 0; part < parts; part++) {
            long offset = part * partSize;
            int length = (int) Math.min(partSize, file.length() - offset);
            byte[] chunk = read(source, offset, length);
            long partNumber = part + 1;
            // Parts are numbered from one; a zero-based first part is rejected.
            String path = NativeHttp.withQuery(NativeHttp.withQuery(
                    "/files/upload-part/" + encodedPath, "partNum", partNumber), "uploadId", uploadId);
            try {
                String body = NativeHttp.sendForString(api.getClient(),
                        NativeHttp.request(api.getClient(), path)
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/octet-stream")
                                .PUT(HttpRequest.BodyPublishers.ofByteArray(chunk)), "filesUploadPart");
                String etag = api.getClient().getObjectMapper().readTree(body)
                        .path("response").path("etag").asText(null);
                if (etag == null || etag.isBlank()) {
                    throw new EssApiException("The server accepted part " + partNumber
                            + " but returned no etag, which the commit needs: " + body);
                }
                etags.put(String.valueOf(partNumber), etag);
            } catch (ApiException | IOException e) {
                throw new EssApiException("Part " + partNumber + " of " + parts + " failed", e);
            }
        }
        return etags;
    }

    private static byte[] read(Path source, long offset, int length) {
        try (RandomAccessFile reader = new RandomAccessFile(source.toFile(), "r")) {
            byte[] chunk = new byte[length];
            reader.seek(offset);
            reader.readFully(chunk);
            return chunk;
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Finishes the upload by handing back every part's etag.
     *
     * <p>The body is a map of part number to etag - {@code {"1":"ea3d...","2":"666b..."}} - which the
     * specification does not mention at all: it declares no request body for this operation, and
     * committing without one answers 500 "Request failed." with nothing to go on. The shape came from
     * the server's own complaint when handed a list instead, naming
     * {@code LinkedHashMap<Integer, String>}.
     */
    private static void commit(ApiContext api, String encodedPath, String uploadId,
            Map<String, String> etags) {
        String path = NativeHttp.withQuery("/files/upload-commit/" + encodedPath, "uploadId", uploadId);
        try {
            NativeHttp.sendAndDiscard(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(NativeHttp.jsonBody(api.getClient(), etags)), "filesUploadCommit");
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /** Best effort: the upload has already failed, and saying so twice helps nobody. */
    private static void abortQuietly(ApiContext api, String encodedPath, String uploadId) {
        String path = NativeHttp.withQuery("/files/abort/" + encodedPath, "uploadId", uploadId);
        try {
            NativeHttp.sendAndDiscard(api.getClient(),
                    NativeHttp.request(api.getClient(), path).DELETE(), "filesAbortUpload");
        } catch (ApiException | IOException | RuntimeException e) {
            logger.warn("Couldn't abandon upload {}; its parts will be purged by the server", uploadId, e);
        }
    }

    private static JsonNode getJson(ApiContext api, String path) {
        try {
            String body = NativeHttp.sendForString(api.getClient(),
                    NativeHttp.request(api.getClient(), path).header("Accept", "application/json").GET(),
                    "filesGetUploadConfig");
            return api.getClient().getObjectMapper().readTree(body);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

}
