package com.appliedolap.essbase.impl;

import java.util.StringJoiner;

public class EssServerConnectionDetailsImpl {

    private String server;

    private String username;

    private String password;

    private boolean stateless;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isStateless() {
        return stateless;
    }

    public void setStateless(boolean stateless) {
        this.stateless = stateless;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EssServerConnectionDetailsImpl.class.getSimpleName() + "[", "]")
                .add("server='" + server + "'")
                .add("username='" + username + "'")
                .add("stateless=" + stateless)
                .toString();
    }

}