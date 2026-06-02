package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ApiClientFactory {

    public static final String ESSBASE_NETWORK_LOGGING = "essbase.network.logging";

    private static final Logger logger = LoggerFactory.getLogger(ApiClientFactory.class);

    private static final String JSESSION = "JSESSIONID";

    private static final String WL_JSESSION = "_WL_AUTHCOOKIE_JSESSIONID";

    private static final String LINKS_PARAM = "links=none";

    private final String path;

    private final String username;

    private final String password;

    private final boolean stateless;

    private volatile boolean hasAuthenticated;

    private volatile String sessionId;

    private volatile String wlSessionId;

    public ApiClientFactory(String path, String username, String password) {
        this(path, username, password, false);
    }

    public ApiClientFactory(String path, String username, String password, boolean stateless) {
        this.path = path;
        this.username = username;
        this.password = password;
        this.stateless = stateless;
    }

    public ApiClient create() {
        HttpClient.Builder baseBuilder = HttpClient.newBuilder();

        // SSL trust-all
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllManager()}, new java.security.SecureRandom());
            baseBuilder.sslContext(sslContext);
            SSLParameters sslParameters = new SSLParameters();
            sslParameters.setEndpointIdentificationAlgorithm(null);
            baseBuilder.sslParameters(sslParameters);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException("Unable to initialize trust-all SSL context", e);
        }

        // Wrap the resulting HttpClient so every request URI gets links=none appended.
        HttpClient.Builder wrappingBuilder = new LinksInjectingHttpClientBuilder(baseBuilder);

        ApiClient client = new ApiClient()
                .setHttpClientBuilder(wrappingBuilder);

        // updateBaseUri parses scheme/host/port/basePath from the supplied full URI
        client.updateBaseUri(path);

        installRequestInterceptor(client);
        installResponseInterceptor(client);

        return client;
    }

    private void installRequestInterceptor(ApiClient client) {
        client.setRequestInterceptor(builder -> {
            if (stateless || !hasAuthenticated) {
                builder.header("Authorization", "Basic " + basicCredentials());
            } else {
                builder.header("Authorization", "Session Session");
                builder.header("Cookie", buildSessionCookie());
            }
        });
    }

    private void installResponseInterceptor(ApiClient client) {
        // TODO: BODY-level network logging (the previous OkHttp HttpLoggingInterceptor BODY behavior)
        //  is not implemented here yet. The response interceptor only sees the status line + headers,
        //  not the body. If detailed request/response logging is required, consider wrapping the
        //  HttpClient further (LinksInjectingHttpClient) to log on send/sendAsync.
        boolean networkLogging = Boolean.getBoolean(ESSBASE_NETWORK_LOGGING);

        client.setResponseInterceptor(response -> {
            if (networkLogging) {
                logger.info("[essbase-network] {} {} -> {}",
                        response.request().method(), response.uri(), response.statusCode());
            }
            if (stateless) {
                return;
            }
            List<String> setCookieHeaders = response.headers().allValues("Set-Cookie");
            for (String header : setCookieHeaders) {
                List<HttpCookie> cookies;
                try {
                    cookies = HttpCookie.parse(header);
                } catch (IllegalArgumentException e) {
                    logger.debug("Skipping unparseable Set-Cookie header '{}': {}", header, e.getMessage());
                    continue;
                }
                for (HttpCookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    if ("sessionExpiry".equals(name)) {
                        try {
                            long sessionExpiry = Long.parseLong(value);
                            logger.debug("Session expire is in: {}s",
                                    ((sessionExpiry - System.currentTimeMillis()) / 1000.0f));
                        } catch (NumberFormatException nfe) {
                            logger.debug("Could not parse sessionExpiry='{}'", value);
                        }
                    } else if (JSESSION.equals(name)) {
                        if (!hasAuthenticated) {
                            sessionId = value;
                            logger.debug("Setting session ID: {}", sessionId);
                            hasAuthenticated = true;
                        }
                    } else if (WL_JSESSION.equals(name)) {
                        wlSessionId = value;
                        logger.debug("Have WL session: {}", value);
                    }
                }
            }
        });
    }

    private String basicCredentials() {
        String auth = username + ":" + password;
        return Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    private String buildSessionCookie() {
        StringBuilder sb = new StringBuilder("$Version=1;").append(JSESSION).append('=').append(sessionId);
        if (wlSessionId != null) {
            sb.append(",$Version=1;").append(WL_JSESSION).append('=').append(wlSessionId);
        }
        return sb.toString();
    }

    /**
     * Returns the current Authorization header value, matching what the request interceptor would
     * apply. Useful for download-bypass code that constructs raw {@link HttpRequest}s.
     */
    public String currentAuthorizationHeader() {
        if (stateless || !hasAuthenticated) {
            return "Basic " + basicCredentials();
        }
        return "Session Session";
    }

    /**
     * Returns the current Cookie header value (or {@code null} when not yet authenticated / in
     * stateless mode). Pairs with {@link #currentAuthorizationHeader()} for download bypass paths.
     */
    public String currentCookieHeader() {
        if (stateless || !hasAuthenticated) {
            return null;
        }
        return buildSessionCookie();
    }

    /**
     * Appends {@code links=none} to the given URI's query string, preserving any existing query.
     */
    static URI appendLinksNone(URI uri) {
        String existingQuery = uri.getRawQuery();
        if (existingQuery != null && (existingQuery.equals(LINKS_PARAM)
                || existingQuery.startsWith(LINKS_PARAM + "&")
                || existingQuery.endsWith("&" + LINKS_PARAM)
                || existingQuery.contains("&" + LINKS_PARAM + "&"))) {
            return uri;
        }
        String newQuery = (existingQuery == null || existingQuery.isEmpty())
                ? LINKS_PARAM
                : existingQuery + "&" + LINKS_PARAM;
        try {
            // Use the raw constructor to avoid double-encoding of existing query components.
            return new URI(
                    uri.getScheme(),
                    uri.getRawAuthority(),
                    uri.getRawPath(),
                    null,
                    uri.getRawFragment()
            ).resolve(buildPathAndQuery(uri.getRawPath(), newQuery, uri.getRawFragment()));
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to append links=none to URI " + uri, e);
        }
    }

    private static String buildPathAndQuery(String rawPath, String rawQuery, String rawFragment) {
        StringBuilder sb = new StringBuilder();
        if (rawPath != null) sb.append(rawPath);
        if (rawQuery != null && !rawQuery.isEmpty()) sb.append('?').append(rawQuery);
        if (rawFragment != null) sb.append('#').append(rawFragment);
        return sb.toString();
    }

    /**
     * Trust-all X509 trust manager (mirrors legacy {@code setVerifyingSsl(false)} behavior).
     */
    private static final class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * Hostname verifier that accepts every hostname. Unused directly but kept for symmetry; the
     * HttpClient skips hostname verification when {@link SSLParameters#setEndpointIdentificationAlgorithm(String)}
     * is set to {@code null}.
     */
    @SuppressWarnings("unused")
    private static final class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    /**
     * Builder wrapper whose {@link #build()} returns a delegating {@link HttpClient} that appends
     * {@code links=none} to every outbound request URI. Necessary because the native
     * {@link ApiClient#setRequestInterceptor(java.util.function.Consumer) request interceptor}
     * cannot read the in-flight URI from {@link HttpRequest.Builder}.
     */
    private static final class LinksInjectingHttpClientBuilder implements HttpClient.Builder {

        private final HttpClient.Builder delegate;

        LinksInjectingHttpClientBuilder(HttpClient.Builder delegate) {
            this.delegate = delegate;
        }

        @Override public HttpClient.Builder cookieHandler(CookieHandler cookieHandler) { delegate.cookieHandler(cookieHandler); return this; }
        @Override public HttpClient.Builder connectTimeout(Duration duration) { delegate.connectTimeout(duration); return this; }
        @Override public HttpClient.Builder sslContext(SSLContext sslContext) { delegate.sslContext(sslContext); return this; }
        @Override public HttpClient.Builder sslParameters(SSLParameters sslParameters) { delegate.sslParameters(sslParameters); return this; }
        @Override public HttpClient.Builder executor(Executor executor) { delegate.executor(executor); return this; }
        @Override public HttpClient.Builder followRedirects(HttpClient.Redirect policy) { delegate.followRedirects(policy); return this; }
        @Override public HttpClient.Builder version(HttpClient.Version version) { delegate.version(version); return this; }
        @Override public HttpClient.Builder priority(int priority) { delegate.priority(priority); return this; }
        @Override public HttpClient.Builder proxy(ProxySelector proxySelector) { delegate.proxy(proxySelector); return this; }
        @Override public HttpClient.Builder authenticator(Authenticator authenticator) { delegate.authenticator(authenticator); return this; }

        @Override
        public HttpClient build() {
            return new LinksInjectingHttpClient(delegate.build());
        }
    }

    /**
     * Delegating HttpClient that rewrites every request URI so {@code links=none} is present in the
     * query string.
     */
    private static final class LinksInjectingHttpClient extends HttpClient {

        private final HttpClient delegate;

        LinksInjectingHttpClient(HttpClient delegate) {
            this.delegate = delegate;
        }

        private HttpRequest rewrite(HttpRequest original) {
            URI rewritten = appendLinksNone(original.uri());
            if (rewritten.equals(original.uri())) {
                return original;
            }
            HttpRequest.Builder builder = HttpRequest.newBuilder(rewritten);
            original.headers().map().forEach((name, values) -> {
                for (String v : values) {
                    builder.header(name, v);
                }
            });
            // Preserve method + body
            HttpRequest.BodyPublisher publisher = original.bodyPublisher().orElse(HttpRequest.BodyPublishers.noBody());
            builder.method(original.method(), publisher);
            original.timeout().ifPresent(builder::timeout);
            original.version().ifPresent(builder::version);
            if (original.expectContinue()) builder.expectContinue(true);
            return builder.build();
        }

        @Override public Optional<CookieHandler> cookieHandler() { return delegate.cookieHandler(); }
        @Override public Optional<Duration> connectTimeout() { return delegate.connectTimeout(); }
        @Override public Redirect followRedirects() { return delegate.followRedirects(); }
        @Override public Optional<ProxySelector> proxy() { return delegate.proxy(); }
        @Override public SSLContext sslContext() { return delegate.sslContext(); }
        @Override public SSLParameters sslParameters() { return delegate.sslParameters(); }
        @Override public Optional<Authenticator> authenticator() { return delegate.authenticator(); }
        @Override public Version version() { return delegate.version(); }
        @Override public Optional<Executor> executor() { return delegate.executor(); }

        @Override
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
                throws IOException, InterruptedException {
            return delegate.send(rewrite(request), responseBodyHandler);
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                                                                HttpResponse.BodyHandler<T> responseBodyHandler) {
            return delegate.sendAsync(rewrite(request), responseBodyHandler);
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                                                                HttpResponse.BodyHandler<T> responseBodyHandler,
                                                                HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return delegate.sendAsync(rewrite(request), responseBodyHandler, pushPromiseHandler);
        }
    }

}
