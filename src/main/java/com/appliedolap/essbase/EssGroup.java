package com.appliedolap.essbase;

import java.util.List;

/**
 * A group on a server that owns its own security.
 *
 * <p>Every method here answers HTTP 400 "This operation is not supported" on a deployment behind an
 * external identity provider, which keeps its groups in the provider - see {@link EssServer#getGroups()}.
 * Groups are only writable where Essbase is the directory.
 */
public interface EssGroup extends EssObject {

    @Override
    String getName();

    @Override
    Type getType();

    EssServer getServer();

    String getDescription();

    /**
     * The service-level role this group grants its members - {@code User}, {@code Power User} or
     * {@code Service Administrator}.
     *
     * @return the role name as the server reports it
     */
    String getRole();

    /**
     * Changes the description, leaving the role alone.
     *
     * @param description the new description
     */
    void setDescription(String description);

    /**
     * Changes the role this group grants.
     *
     * @param role the role name, as {@link #getRole()} reports it
     */
    void setRole(String role);

    /**
     * Removes this group. Its members are not removed - they simply stop being in it.
     */
    void delete();

    /**
     * Gets the user members that are in this group.
     *
     * @return the user members that are in this group.
     */
    List<EssUser> getUsers();

    /**
     * Adds users to this group.
     *
     * <p>A user cannot add or remove themselves: Essbase refuses with "not allowed to perform self
     * provisioning/de-provisioning" even for a service administrator. That is a server rule, not a
     * permission that can be granted.
     *
     * @param usernames the user IDs to add
     */
    void addUsers(String... usernames);

    /**
     * Removes users from this group. They are not deleted, only unassigned.
     *
     * @param usernames the user IDs to remove
     */
    void removeUsers(String... usernames);

    List<EssGroup> getGroups();

    /**
     * Adds groups as members of this group. Groups nest.
     *
     * @param groupNames the group names to add
     */
    void addGroups(String... groupNames);

    /**
     * Removes member groups from this group. They are not deleted, only unassigned.
     *
     * @param groupNames the group names to remove
     */
    void removeGroups(String... groupNames);

}
