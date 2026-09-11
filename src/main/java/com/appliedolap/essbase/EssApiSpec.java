package com.appliedolap.essbase;

/**
 * The server's own machine-readable description of its REST API - what its Swagger UI renders.
 *
 * @param format {@code openapi} or {@code swagger}, reflecting which specification the document follows;
 *               Essbase moved from Swagger 2.0 to OpenAPI 3.0 between 21.7 and 26.1
 * @param version the specification version the document declares, e.g. {@code 3.0.1}
 * @param json the document exactly as the server served it, unparsed - the point of fetching it is usually
 *             to keep or feed it to something else, and reserialising would gratuitously reformat it
 */
public record EssApiSpec(String format, String version, String json) {

    /** e.g. {@code OpenAPI 3.0.1}, for telling someone what they just got. */
    public String describe() {
        return ("openapi".equals(format) ? "OpenAPI" : "Swagger") + " " + version;
    }

}
