package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.GroupBean;
import com.appliedolap.essbase.client.model.Groups;
import com.appliedolap.essbase.client.model.UserBean;
import com.appliedolap.essbase.client.model.Users;
import com.appliedolap.essbase.util.WrapperUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

public class EssGroupImpl extends AbstractEssObject implements EssGroup {

    private final EssServer server;

    private GroupBean groupBean;

    public EssGroupImpl(ApiContext api, EssServer server, GroupBean groupBean) {
        super(api);
        this.server = server;
        this.groupBean = groupBean;
    }

    @Override
    public String getName() {
        return groupBean.getName();
    }

    @Override
    public Type getType() {
        return Type.GROUP;
    }

    @Override
    public EssServer getServer() {
        return server;
    }

    @Override
    public String getDescription() {
        return groupBean.getDescription();
    }

    @Override
    public String getRole() {
        return groupBean.getRole();
    }

    @Override
    public void setDescription(String description) {
        edit(description, groupBean.getRole());
    }

    @Override
    public void setRole(String role) {
        edit(groupBean.getDescription(), role);
    }

    /**
     * Sends the whole group back, because the endpoint will not take a partial one.
     *
     * <p>Editing only the description still has to carry the role: omit it and the server answers 400
     * "Role cannot be empty" rather than leaving the role as it was. The listing endpoint does return
     * the role, so there is always one to send.
     */
    private void edit(String description, String role) {
        GroupBean edited = new GroupBean();
        edited.setName(groupBean.getName());
        edited.setDescription(description);
        edited.setRole(role);
        // Kept, so a subsequent read of this object reflects the change without a round trip.
        groupBean = WrapperUtil.doWithWrap(
                () -> api.getGroupsApi().groupsEdit(groupBean.getName(), edited));
    }

    @Override
    public void delete() {
        WrapperUtil.wrap(() -> api.getGroupsApi().groupsDelete(groupBean.getName()));
    }

    @Override
    public List<EssUser> getUsers() {
        try {
            Users users = api.getGroupsApi().groupsGetUserMembersOfGroup(groupBean.getName());
            List<EssUser> essUsers = new ArrayList<>();
            for (UserBean user : wrap(users.getItems())) {
                EssUser essUser = new EssUserImpl(api, server, user);
                essUsers.add(essUser);
            }
            return Collections.unmodifiableList(essUsers);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void addUsers(String... usernames) {
        List<String> ids = Arrays.asList(usernames);
        WrapperUtil.wrap(() -> api.getGroupsApi().groupsAddUserMembersToGroup(getName(), ids));
    }

    /**
     * Removal is a DELETE carrying a body - the array of user IDs - which is unusual enough to be
     * worth naming: every query parameter form of this answers HTTP 500 "Request failed."
     */
    @Override
    public void removeUsers(String... usernames) {
        List<String> ids = Arrays.asList(usernames);
        WrapperUtil.wrap(() -> api.getGroupsApi().groupsRemoveUserMembersFromGroup(getName(), ids));
    }

    @Override
    public List<EssGroup> getGroups() {
        try {
            Groups groups = api.getGroupsApi().groupsGetGroupMembersOfGroup(groupBean.getName());
            List<EssGroup> essGroups = new ArrayList<>();
            for (GroupBean currentGroupBean : wrap(groups.getItems())) {
                EssGroup essGroup = new EssGroupImpl(api, server, currentGroupBean);
                essGroups.add(essGroup);
            }
            return Collections.unmodifiableList(essGroups);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void addGroups(String... groupNames) {
        List<String> names = Arrays.asList(groupNames);
        WrapperUtil.wrap(() -> api.getGroupsApi().groupsAddGroupMembersToGroup(getName(), names));
    }

    @Override
    public void removeGroups(String... groupNames) {
        List<String> names = Arrays.asList(groupNames);
        WrapperUtil.wrap(() -> api.getGroupsApi().groupsRemoveGroupMembersFromGroup(getName(), names));
    }

}
