package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssUser;
import com.appliedolap.essbase.client.model.UserBean;

/**
 * A user entry on the server.
 */
public class EssUserImpl extends AbstractEssObject implements EssUser {

    private final EssServer server;

    private final UserBean userBean;

    EssUserImpl(ApiContext api, EssServer server, UserBean userBean) {
        super(api);
        this.server = server;
        this.userBean = userBean;
    }

    @Override
    public String getName() {
        return userBean.getId();
    }

    @Override
    public Type getType() {
        return Type.USER;
    }

    @Override
    public String getDisplayName() {
        return userBean.getName();
    }

    @Override
    public String getEmail() {
        return userBean.getEmail();
    }

    @Override
    public EssServer getServer() {
        return server;
    }

}