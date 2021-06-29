package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.MemberBean;

/**
 * A special type of member that represents a dimension (as well as a member).
 */
public class EssDimension extends EssMember {

    EssDimension(ApiContext api, EssCube cube, MemberBean memberBean) {
        super(api, cube, memberBean);
    }

}