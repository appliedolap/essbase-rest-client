package com.appliedolap.essbase.util;

import com.appliedolap.essbase.client.ApiClient;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class ApiClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(ApiClientFactory.class);

    private static final String JSESSION = "JSESSIONID";

    private static final String WL_JSESSION = "_WL_AUTHCOOKIE_JSESSIONID";

    private boolean hasAuthenticated;

    private String sessionId;

    private String wlSessionId;

    private String path;

    private String username;

    private String password;

    private boolean stateless;

    private HttpLoggingInterceptor loggingInterceptor;

    private CredentialInterceptor credentialInterceptor;

    private LinksInterceptor linksInterceptor;

    public ApiClientFactory(String path, String username, String password) {
        this(path, username, password, false);
    }

    public ApiClientFactory(String path, String username, String password, boolean stateless) {
        this.path = path;
        this.username = username;
        this.password = password;
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        credentialInterceptor = new CredentialInterceptor();
        linksInterceptor = new LinksInterceptor();
        this.stateless = stateless;
    }

    public ApiClient create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(credentialInterceptor)
                .addInterceptor(linksInterceptor);

        OkHttpClient httpClient = builder.build();

        ApiClient client = new ApiClient(httpClient)
                .setVerifyingSsl(false)
                .setBasePath(path);
        return client;
    }

    private class CredentialInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {

            Request.Builder builder = chain.request()
                    .newBuilder();

            if (stateless) {
                builder.header("Authorization", "Basic " + basicCredentials());
            } else {
                if (!hasAuthenticated) {
                    builder.header("Authorization", "Basic " + basicCredentials());
                } else {
                    builder.header("Authorization", "Session Session");
                    String sessionCookie = "$Version=1;JSESSIONID=" + sessionId;
                    if (wlSessionId != null) {
                        sessionCookie += ",$Version=1;" + WL_JSESSION + "=" + wlSessionId;
                    }
                    builder.header("Cookie", sessionCookie);
                }
            }

            Request request = builder.build();

            Response response = chain.proceed(request);

            List<Cookie> cookies = Cookie.parseAll(request.url(), response.headers());

            if (!stateless) {
                for (Cookie cookie : cookies) {
                    System.out.println("---> COOKIE: " + cookie);
                    if (cookie.name().equals("sessionExpiry")) {
                        long sessionExpiry = Long.parseLong(cookie.value());
                        System.out.println("Session expiry is in: " + ((sessionExpiry - System.currentTimeMillis()) / 1000.0f));
                        System.out.println();
                    } else if (cookie.name().equals(JSESSION)) {
                        if (!hasAuthenticated) {
                            sessionId = cookie.value();
                            System.out.println("Setting session ID: " + sessionId);
                            hasAuthenticated = true;
                        }
                    } else if (cookie.name().equals(WL_JSESSION)) {
                        wlSessionId = cookie.value();
                        System.out.println("have WL session: " + cookie.value());
                    }
                }
            }
            return response;
        }

    }

    private String basicCredentials() {
        String auth = username + ":"+ password;
        return Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    private class LinksInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("links", "none")
                    .build();
            Request request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build();
            Response response = chain.proceed(request);
            return response;
        }
    }

}