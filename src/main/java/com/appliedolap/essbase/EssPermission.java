package com.appliedolap.essbase;

/**
 * One role granted to one user or group, at the service or at an application.
 *
 * <p>The half of security that survives an external identity provider. A deployment reporting
 * {@code idcs} keeps its <em>directory</em> there and refuses {@link EssServer#getUsers()} and
 * {@link EssServer#getGroups()} - but the role assignments themselves are Essbase's, and it reports
 * them perfectly well. So "who can do what here" is answerable even where "who exists" is not.
 */
public final class EssPermission {

    private final String id;

    private final String name;

    private final String role;

    private final boolean group;

    public EssPermission(String id, String name, String role, boolean group) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.group = group;
    }

    /** Who holds it - a user id or a group name, and the only field guaranteed to be there. */
    public String getId() {
        return id;
    }

    /** Their display name, which is frequently absent. */
    public String getName() {
        return name;
    }

    /**
     * The role, in the server's own spelling - {@code service_administrator}, {@code Database
     * Manager}. Left as sent rather than tidied, because it is what the provisioning calls take back.
     */
    public String getRole() {
        return role;
    }

    /** Whether the holder is a group rather than a user. */
    public boolean isGroup() {
        return group;
    }

    @Override
    public String toString() {
        return id + " = " + role;
    }

}
