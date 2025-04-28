package com.appliedolap.essbase;

import java.util.List;

public interface EssMember extends EssObject {

    /**
     * Returns the name of the member.
     *
     * @return the member name
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the parent member (not implemented yet).
     *
     * @return the parent member, null if none (it's a dimension)
     */
    EssMember getParent();

    /**
     * Gets the level of the member as indicated by the REST API
     *
     * @return the member level
     */
    int getLevel();

    /**
     * Check if the member is a normal member or a dimension member.
     *
     * @return true if it's a dimension, false if normal member
     */
    boolean isDimension();

    /**
     * Checks if the member is a leaf node or level 0 (has no children).
     *
     * @return true if level 0, false otherwise
     */
    boolean isLeaf();

    /**
     * Gets the children of this member.
     *
     * @return the children of this member or an empty collection if none.
     */
    List<EssMember> getChildren();

    /**
     * Returns the count of children of this member.
     *
     * @return the number of children
     */
    int getChildCount();

    /**
     * Gets the level-0 descendants of this member. There is currently no REST API to do this in one call, so this
     * method works recursively to fetch children of children and so on until the member has been explored.
     *
     * @return the level-0 descendants of this member, or a collection containing just this member if it has no
     * children.
     */
    List<EssMember> getLeafDescendants();

}