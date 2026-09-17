package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssGroup;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssUser;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.client.model.UserBean;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The group lifecycle, against a server that owns its own security.
 *
 * <p>Skipped where it does not: a deployment behind an external identity provider keeps its groups in
 * the provider and answers 400 to all of this, so the test is meaningless rather than failing. Run it
 * against the local container - see docker/essbase in the Cessna repository.
 *
 * <p>Two things here are only knowable by trying them, and are the reason this exists. Editing a group
 * must send the role even when only the description changes, or the server answers "Role cannot be
 * empty" - so a partial edit silently needs a field the caller never mentioned. And removing a member
 * is a DELETE carrying a body; every query-parameter spelling of it answers 500 "Request failed."
 */
@Category(DestructiveIntegrationTest.class)
public class EssGroupIT {

    /** Distinctive enough that a leftover is obviously ours if a run dies mid-way. */
    private static final String GROUP = "cessna_group_it";

    private static final String CHILD = "cessna_group_it_child";

    private static final String MEMBER = "cessna_group_it_user";

    private final EssServer server = ConnectionUtils.server();

    @Before
    public void requireLocalSecurity() {
        try {
            server.getGroups();
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server delegates its directory; groups are not writable", e);
        }
        removeWhateverSurvived();
    }

    @After
    public void removeWhateverSurvived() {
        for (String name : List.of(GROUP, CHILD)) {
            try {
                server.getGroup(name).delete();
            } catch (NoSuchEssbaseObjectException expected) {
                // Nothing to clean up, which is the normal case.
            }
        }
    }

    @Test
    public void createsReadsAndDeletesAGroup() {
        EssGroup created = server.createGroup(GROUP, "made by the integration test", "User");
        assertEquals(GROUP, created.getName());
        assertEquals("made by the integration test", created.getDescription());
        assertEquals("User", created.getRole());

        assertTrue("the new group should be in the listing",
                names(server.getGroups()).contains(GROUP));
        assertEquals("made by the integration test", server.getGroup(GROUP).getDescription());

        server.getGroup(GROUP).delete();
        try {
            server.getGroup(GROUP);
            fail("the group should be gone");
        } catch (NoSuchEssbaseObjectException expected) {
            // what a deleted group looks like
        }
    }

    /** The role has to survive an edit that never mentions it. */
    @Test
    public void editingTheDescriptionKeepsTheRole() {
        server.createGroup(GROUP, "before", "Power User");
        EssGroup group = server.getGroup(GROUP);
        group.setDescription("after");

        EssGroup reread = server.getGroup(GROUP);
        assertEquals("after", reread.getDescription());
        assertEquals("the role must survive an edit that only changed the description",
                "Power User", reread.getRole());
    }

    /**
     * The listing leaves the description out, so a group taken from it must fetch its own before
     * reporting one - otherwise an edit that only meant to change the role erases the description it
     * never saw.
     */
    @Test
    public void aGroupFromTheListingStillKnowsItsDescription() {
        server.createGroup(GROUP, "described", "User");

        EssGroup fromListing = server.getGroups().stream()
                .filter(group -> GROUP.equals(group.getName())).findFirst().orElseThrow();
        assertEquals("described", fromListing.getDescription());

        fromListing.setRole("Power User");
        EssGroup reread = server.getGroup(GROUP);
        assertEquals("the description must survive an edit that only changed the role",
                "described", reread.getDescription());
        assertEquals("Power User", reread.getRole());
    }

    @Test
    public void groupsHoldGroups() {
        server.createGroup(GROUP, "parent", "User");
        server.createGroup(CHILD, "child", "User");

        EssGroup parent = server.getGroup(GROUP);
        parent.addGroups(CHILD);
        assertEquals(List.of(CHILD), names(parent.getGroups()));

        parent.removeGroups(CHILD);
        assertTrue("removing a member should empty the group", parent.getGroups().isEmpty());
        assertTrue("and should not delete the group itself", names(server.getGroups()).contains(CHILD));
    }

    /**
     * Membership needs somebody other than the caller: Essbase refuses to let a user add or remove
     * themselves, service administrator or not, so testing this with {@code admin} proves nothing.
     *
     * <p>The fixture user is made with the generated api rather than the library, which has no user
     * CRUD - creating users is not what this test is about.
     */
    @Test
    public void groupsHoldUsers() throws Exception {
        ApiContext api = ConnectionUtils.api();
        UserBean fixture = new UserBean();
        fixture.setId(MEMBER);
        fixture.setName(MEMBER);
        fixture.setPassword("Welcome1_");
        fixture.setRole("user");
        api.getUsersApi().usersAdd(fixture);
        try {
            server.createGroup(GROUP, "holds a user", "User");
            EssGroup group = server.getGroup(GROUP);

            group.addUsers(MEMBER);
            assertEquals(List.of(MEMBER), userNames(group.getUsers()));

            group.removeUsers(MEMBER);
            assertTrue("removing a member should empty the group", group.getUsers().isEmpty());
            assertEquals("and should not delete the user", MEMBER,
                    api.getUsersApi().usersGet(MEMBER).getId());
        } finally {
            api.getUsersApi().usersDelete(MEMBER);
        }
    }

    private static List<String> names(List<? extends com.appliedolap.essbase.EssObject> objects) {
        return objects.stream().map(com.appliedolap.essbase.EssObject::getName).collect(Collectors.toList());
    }

    private static List<String> userNames(List<EssUser> users) {
        return users.stream().map(EssUser::getName).collect(Collectors.toList());
    }

}
