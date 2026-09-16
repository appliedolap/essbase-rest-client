package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssPermission;
import com.appliedolap.essbase.client.model.UserGroupProvisionInfo;
import com.appliedolap.essbase.client.model.UserGroupProvisionInfoList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Turns the generated provisioning model into {@link EssPermission}s, for the service-level and
 * application-level lists alike - they are the same document from two endpoints.
 */
final class EssPermissions {

    private EssPermissions() {
    }

    static List<EssPermission> from(UserGroupProvisionInfoList list) {
        List<EssPermission> permissions = new ArrayList<>();
        if (list != null && list.getItems() != null) {
            for (UserGroupProvisionInfo item : list.getItems()) {
                permissions.add(new EssPermission(item.getId(), item.getName(), item.getRole(),
                        Boolean.TRUE.equals(item.getGroup())));
            }
        }
        return Collections.unmodifiableList(permissions);
    }

}
