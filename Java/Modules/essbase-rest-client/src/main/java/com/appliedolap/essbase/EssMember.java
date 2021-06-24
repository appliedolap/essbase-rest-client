package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.OutlineViewerApi;
import com.appliedolap.essbase.client.model.MemberBean;
import com.appliedolap.essbase.client.model.RestCollectionResponse;

import java.util.*;

public class EssMember extends EssObject {

    private final EssCube cube;

    private final OutlineViewerApi outlineViewerApi;

    private final MemberBean memberBean;

    public EssMember(ApiClient client, EssCube cube, MemberBean memberBean) {
        super(client);
        this.cube = cube;
        this.outlineViewerApi = new OutlineViewerApi(client);
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
    public EssMember(ApiClient client, EssCube cube, Map<String, Object> memberProps) {
        this(client, cube, propsToMemberBean(memberProps));
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

    public EssMember getParent() {
        throw new UnsupportedOperationException();
    }

    public int getLevel() {
        if (memberBean.getLevelNumber() != null) {
            return memberBean.getLevelNumber();
        }
        throw new IllegalStateException("No level number");
    }

    public boolean isDimension() {
        return memberBean.getDimension() != null ? memberBean.getDimension() : false;
    }

    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    public List<EssMember> getChildren() {
        if (getChildCount() > 0) {
            try {
                RestCollectionResponse response = outlineViewerApi.outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, null, getName(), null, null, null, 0, 0);
                List<EssMember> members = new ArrayList<>();
                for (Object memberObject : response.getItems()) {
                    Map<String, Object> memberProps = (Map<String, Object>) memberObject;
                    EssMember member = new EssMember(client, cube, memberProps);
                    members.add(member);
                }
                return members;
            } catch (ApiException apiException) {
                throw new EssApiException(apiException);
            }
        } else {
            return Collections.emptyList();
        }
    }

    public int getChildCount() {
        return memberBean.getNumberOfChildren();
    }

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

}