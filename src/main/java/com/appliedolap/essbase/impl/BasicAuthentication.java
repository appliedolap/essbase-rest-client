package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAuthentication;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

/**
 * HTTP Basic on every request, never establishing a session.
 */
public class BasicAuthentication implements EssAuthentication {

    private final String header;

    private final String username;

    public BasicAuthentication(String username, String password) {
        Objects.requireNonNull(username, "username");
        this.username = username;
        Objects.requireNonNull(password, "password");
        // Encoded once at construction: it never changes, and doing it per request would re-encode the
        // password into a fresh String on every single call.
        this.header = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String authorizationHeader() {
        return header;
    }

    @Override
    public Optional<String> username() {
        return Optional.of(username);
    }

}
