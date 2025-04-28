package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.GroupBean;
import com.appliedolap.essbase.client.model.Groups;
import com.appliedolap.essbase.client.model.UserBean;
import com.appliedolap.essbase.client.model.Users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

public class EssGroupImpl extends AbstractEssObject implements EssGroup {

    private final EssServer server;

    private final GroupBean groupBean;

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

}