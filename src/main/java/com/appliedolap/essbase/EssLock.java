package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.LockObject;

public interface EssLock extends EssObject {

    /**
     * Gets the name of the lock.
     *
     * @return the name of this lock
     */
    @Override
    String getName();

    @Override
    Type getType();

    LockObject.TypeEnum getLockObjectType();

    String getUser();

    Long getTime();

}