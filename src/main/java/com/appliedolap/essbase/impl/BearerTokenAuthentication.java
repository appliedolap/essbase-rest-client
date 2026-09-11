package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssAuthentication;

import java.util.Objects;

/**
 * An OAuth-style bearer token. See {@link EssAuthentication#bearerToken(String)} for the caveat about
 * which deployments actually accept one.
 */
public class BearerTokenAuthentication implements EssAuthentication {

    private final String header;

    public BearerTokenAuthentication(String token) {
        this.header = "Bearer " + Objects.requireNonNull(token, "token");
    }

    @Override
    public String authorizationHeader() {
        return header;
    }

}
