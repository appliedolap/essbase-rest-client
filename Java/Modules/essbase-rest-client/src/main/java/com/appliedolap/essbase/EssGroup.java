package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.GroupBean;
import com.appliedolap.essbase.client.model.Groups;
import com.appliedolap.essbase.client.model.UserBean;
import com.appliedolap.essbase.client.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

public class EssGroup extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssGroup.class);

    private final EssServer server;

    private final GroupBean groupBean;

    EssGroup(ApiContext api, EssServer server, GroupBean groupBean) {
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

    public EssServer getServer() {
        return server;
    }

    public String getDescription() {
        return groupBean.getDescription();
    }

    /**
     * Gets the user members that are in this group.
     *
     * @return the user members that are in this group.
     */
    public List<EssUser> getUsers() {
        try {
            Users users = api.getGroupsApi().groupsGetUserMembersOfGroup(groupBean.getName());
            List<EssUser> essUsers = new ArrayList<>();
            for (UserBean user : wrap(users.getItems())) {
                EssUser essUser = new EssUser(api, server, user);
                essUsers.add(essUser);
            }
            return Collections.unmodifiableList(essUsers);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    public List<EssGroup> getGroups() {
        try {
            Groups groups = api.getGroupsApi().groupsGetGroupMembersOfGroup(groupBean.getName());
            List<EssGroup> essGroups = new ArrayList<>();
            for (GroupBean groupBean : wrap(groups.getItems())) {
                EssGroup essGroup = new EssGroup(api, server, groupBean);
                essGroups.add(essGroup);
            }
            return Collections.unmodifiableList(essGroups);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

}