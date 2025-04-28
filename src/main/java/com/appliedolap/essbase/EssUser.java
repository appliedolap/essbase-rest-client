package com.appliedolap.essbase;

public interface EssUser extends EssObject {

    /**
     * Returns the user ID. This might seem a little weird given that the user object has a 'name' property on it, but
     * the <code>getName</code> method is meant to always return at least something, and the name field on an Essbase
     * user can be blank, but the ID cannot. So in this case, the value returned here is mapped to the user ID. Use
     * {@link #getDisplayName()} to get the name field from the Essbase user object.
     *
     * @return the user ID
     */
    @Override
    String getName();

    @Override
    Type getType();

    String getDisplayName();

    String getEmail();

    /**
     * Get the owning server of this user.
     *
     * @return the server associated with this user
     */
    EssServer getServer();

}