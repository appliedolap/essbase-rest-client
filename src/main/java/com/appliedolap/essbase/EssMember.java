package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.MemberBean;
import com.appliedolap.essbase.client.model.RestCollectionResponse;

import java.util.*;

/**
 * A member in an Essbase cube.
 */
public class EssMember extends EssObject {

    private final EssCube cube;

    private final MemberBean memberBean;

    EssMember(ApiContext api, EssCube cube, MemberBean memberBean) {
        super(api);
        this.cube = cube;
        this.memberBean = memberBean;
        if (memberBean.getNumberOfChildren() == null) {
            memberBean.setNumberOfChildren(0);
        }
    }

    /*
    "name" -> "East"
"dimensionName" -> "Market"
"numberOfChildren" -> {Double@1772} 5.0
"levelNumber" -> {Double@1774} 1.0
"generationNumber" -> {Double@1776} 2.0
"aliases" -> {LinkedTreeMap@1778}  size = 6
"uniqueName" -> "East"
"memberId" -> "id__54"
"descendantsCount" -> {Double@1784} 5.0
"links" -> {ArrayList@1786}  size = 4
"uda" -> {ArrayList@1788}  size = 1
"parentName" -> "Market"
     */
    EssMember(ApiContext api, EssCube cube, Map<String, Object> memberProps) {
        this(api, cube, propsToMemberBean(memberProps));
    }

    private static MemberBean propsToMemberBean(Map<String, Object> memberProps) {
        MemberBean memberBean = new MemberBean();
        memberBean.setName(memberProps.get("name").toString());
        memberBean.setLevelNumber(requiredInt(memberProps.get("levelNumber"), 0));
        memberBean.setNumberOfChildren(requiredInt(memberProps.get("numberOfChildren"), 0));
        return memberBean;
    }

    private static int requiredInt(Object value) {
        if (value instanceof Double) {
            Double doubleVal = (Double) value;
            return doubleVal.intValue();
        }
        throw new IllegalArgumentException("Must be given a Double");
    }

    private static int requiredInt(Object value, int defaultValue) {
        if (value instanceof Double) {
            Double doubleVal = (Double) value;
            return doubleVal.intValue();
        } else {
            return defaultValue;
        }
    }

    /**
     * Returns the name of the member.
     *
     * @return the member name
     */
    @Override
    public String getName() {
        return memberBean.getName();
    }

    @Override
    public Type getType() {
        return Type.MEMBER;
    }

    /**
     * Gets the parent member (not implemented yet).
     *
     * @return the parent member, null if none (it's a dimension)
     */
    public EssMember getParent() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the level of the member as indicated by the REST API
     *
     * @return the member level
     */
    public int getLevel() {
        if (memberBean.getLevelNumber() != null) {
            return memberBean.getLevelNumber();
        }
        throw new IllegalStateException("No level number");
    }

    /**
     * Check if the member is a normal member or a dimension member.
     *
     * @return true if it's a dimension, false if normal member
     */
    public boolean isDimension() {
        return memberBean.getDimension() != null ? memberBean.getDimension() : false;
    }

    /**
     * Checks if the member is a leaf node or level 0 (has no children).
     *
     * @return true if level 0, false otherwise
     */
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    /**
     * Gets the children of this member.
     *
     * @return the children of this member or an empty collection if none.
     */
    public List<EssMember> getChildren() {
        if (getChildCount() > 0) {
            try {
                RestCollectionResponse response = api.getOutlineViewerApi().outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, null, getName(), null, null, null, 0, 0);
                return collectionToMembers(api, cube, response);
            } catch (ApiException apiException) {
                throw new EssApiException(apiException);
            }
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns the count of children of this member.
     *
     * @return the number of children
     */
    public int getChildCount() {
        return memberBean.getNumberOfChildren();
    }

    /**
     * Gets the level-0 descendants of this member. There is currently no REST API to do this in one call, so
     * this method works recursively to fetch children of children and so on until the member has been explored.
     *
     * @return the level-0 descendants of this member, or a collection containing just this member if it has
     * no children.
     */
    public List<EssMember> getLeafDescendants() {
        List<EssMember> leaves = new ArrayList<>();

        Queue<EssMember> queue = new LinkedList<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            EssMember current = queue.remove();
            if (current.isLeaf()) {
                leaves.add(current);
            } else {
                queue.addAll(current.getChildren());
            }
        }
        return leaves;
    }

    static List<EssMember> collectionToMembers(ApiContext api, EssCube cube, RestCollectionResponse response) {
        List<EssMember> members = new ArrayList<>();
        for (Object memberObject : response.getItems()) {
            Map<String, Object> memberProps = (Map<String, Object>) memberObject;
            EssMember member = new EssMember(api, cube, memberProps);
            members.add(member);
        }
        return Collections.unmodifiableList(members);
    }

}