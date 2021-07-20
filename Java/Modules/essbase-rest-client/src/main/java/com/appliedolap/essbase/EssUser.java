package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.UserBean;

/**
 * A user entry on the server.
 */
public class EssUser extends EssObject {

    private final EssServer server;

    private final UserBean userBean;

    EssUser(ApiContext api, EssServer server, UserBean userBean) {
        super(api);
        this.server = server;
        this.userBean = userBean;
    }

    /**
     * Returns the user ID. This might seem a little weird given that the user object has a 'name' property on it, but
     * the <code>getName</code> method is meant to always return at least something, and the name field on an Essbase
     * user can be blank, but the ID cannot. So in this case, the value returned here is mapped to the user ID. Use
     * {@link #getDisplayName()} to get the name field from the Essbase user object.
     *
     * @return the user ID
     */
    @Override
    public String getName() {
        return userBean.getId();
    }

    public String getDisplayName() {
        return userBean.getName();
    }

    public String getEmail() {
        return userBean.getEmail();
    }

}