package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssLock;
import com.appliedolap.essbase.client.model.LockObject;
import com.appliedolap.essbase.client.model.LockObject.TypeEnum;

public class EssLockImpl extends AbstractEssObject implements EssLock {

    private final LockObject object;

    EssLockImpl(ApiContext api, LockObject object) {
        super(api);
        this.object = object;
    }

    @Override
    public String getName() {
        return object.getName();
    }

    @Override
    public Type getType() {
        return Type.LOCK;
    }

    @Override
    public TypeEnum getLockObjectType() {
        return object.getType();
    }

    @Override
    public String getUser() {
        return object.getUser();
    }

    @Override
    public Long getTime() {
        return object.getTime();
    }

}