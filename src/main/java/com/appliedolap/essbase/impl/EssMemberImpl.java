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

    /**
     * Builds a bean from the outline viewer's untyped rows.
     *
     * <p>A row looks like this, from {@code GET /outline/Sample/Basic}:
     * <pre>
     * "name": "Year", "uniqueName": "Year", "parentName": null,
     * "numberOfChildren": 4, "levelNumber": 2, "descendantsCount": 16,
     * "dimension": true, "dimensionType": "TIME", "dimStorageType": "DENSE",
     * "dataStorageType": "DYNAMICCALC", "memberSolveOrder": 40, "aliases": { ... }
     * </pre>
     */
    EssMemberImpl(ApiContext api, EssCube cube, Map<String, Object> memberProps) {
        this(api, cube, propsToMemberBean(memberProps));
    }

    static MemberBean propsToMemberBean(Map<String, Object> memberProps) {
        MemberBean memberBean = new MemberBean();
        memberBean.setName(Objects.toString(memberProps.get("name"), null));
        memberBean.setUniqueName(Objects.toString(memberProps.get("uniqueName"), null));
        memberBean.setParentName(Objects.toString(memberProps.get("parentName"), null));
        memberBean.setMemberId(Objects.toString(memberProps.get("memberId"), null));
        memberBean.setDataStorageType(Objects.toString(memberProps.get("dataStorageType"), null));
        memberBean.setLevelNumber(optionalInt(memberProps.get("levelNumber"), 0));
        memberBean.setGenerationNumber(optionalInt(memberProps.get("generationNumber"), 0));
        memberBean.setNumberOfChildren(optionalInt(memberProps.get("numberOfChildren"), 0));
        memberBean.setMemberSolveOrder(optionalInt(memberProps.get("memberSolveOrder"), 0));
        memberBean.setDescendantsCount((long) optionalInt(memberProps.get("descendantsCount"), 0));
        memberBean.setDimension(Boolean.TRUE.equals(memberProps.get("dimension")));
        // The server names the dimension's role rather than setting a flag per role, so the two flags
        // the bean carries are derived from it.
        String dimensionType = Objects.toString(memberProps.get("dimensionType"), "");
        memberBean.setAttribute("ATTRIBUTE".equalsIgnoreCase(dimensionType));
        memberBean.setAccount("ACCOUNTS".equalsIgnoreCase(dimensionType));
        return memberBean;
    }

    /**
     * Reads a JSON number out of an untyped row.
     *
     * <p>Accepts any {@link Number}, which it has to: this used to test for {@code Double} alone,
     * which was right when these rows were parsed by Gson - it makes every number a Double - and
     * silently wrong once the client moved to Jackson, which gives an Integer for a whole number. The
     * default then applied to every field, so every member reported zero children, every member was
     * therefore a leaf, and the outline appeared to have no members below its dimensions at all.
     */
    static int optionalInt(Object value, int defaultValue) {
        return value instanceof Number ? ((Number) value).intValue() : defaultValue;
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