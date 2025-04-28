package com.appliedolap.essbase;

import java.util.List;

public interface EssGroup extends EssObject {

    @Override
    String getName();

    @Override
    Type getType();

    EssServer getServer();

    String getDescription();

    /**
     * Gets the user members that are in this group.
     *
     * @return the user members that are in this group.
     */
    List<EssUser> getUsers();

    List<EssGroup> getGroups();

}