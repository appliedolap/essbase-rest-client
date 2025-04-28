package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.MemberBean;
import com.appliedolap.essbase.client.model.RestCollectionResponse;

import java.util.*;

/**
 * A member in an Essbase cube.
 */
public class EssMemberImpl extends AbstractEssObject implements EssMember {

    private final EssCube cube;

    private final MemberBean memberBean;

    EssMemberImpl(ApiContext api, EssCube cube, MemberBean memberBean) {
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
    EssMemberImpl(ApiContext api, EssCube cube, Map<String, Object> memberProps) {
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

    @Override
    public String getName() {
        return memberBean.getName();
    }

    @Override
    public Type getType() {
        return Type.MEMBER;
    }

    @Override
    public EssMember getParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLevel() {
        if (memberBean.getLevelNumber() != null) {
            return memberBean.getLevelNumber();
        }
        throw new IllegalStateException("No level number");
    }

    @Override
    public boolean isDimension() {
        return memberBean.getDimension() != null ? memberBean.getDimension() : false;
    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    @Override
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

    @Override
    public int getChildCount() {
        return memberBean.getNumberOfChildren();
    }

    @Override
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
            EssMember member = new EssMemberImpl(api, cube, memberProps);
            members.add(member);
        }
        return Collections.unmodifiableList(members);
    }

}