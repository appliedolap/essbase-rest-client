/**
 * Internal helpers shared across {@code impl} classes - not part of the public API.
 *
 * <p>{@link com.appliedolap.essbase.util.NativeHttp} builds and sends requests through
 * {@code java.net.http.HttpClient} for the handful of endpoints the generated client doesn't cover
 * directly (streaming downloads, MDX execution). {@link com.appliedolap.essbase.util.WrapperUtil}
 * wraps calls into the generated client so any {@code ApiException} it throws comes back out as
 * {@link com.appliedolap.essbase.EssApiException} instead. {@link com.appliedolap.essbase.util.GenericDownload}
 * streams a file-download response to disk. {@link com.appliedolap.essbase.util.DataSourceQueryBuilder}
 * builds data source query strings and column mappings. {@link com.appliedolap.essbase.util.Utils}
 * is a small grab bag of null-safety helpers.
 */
package com.appliedolap.essbase.util;
