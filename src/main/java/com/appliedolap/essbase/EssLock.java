package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.LockObject;
import com.appliedolap.essbase.client.model.LockObject.TypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EssLock extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssGeneration.class);
    private final LockObject object;

    EssLock(ApiContext api, LockObject object) {
        super(api);
        this.object = object;
    }

    /**
     * Gets the name of the lock.
     *
     * @return the name of this lock
     */
    @Override
    public String getName() {
        return object.getName();
    }

    @Override
    public Type getType() {
        return Type.LOCK;
    }

    public TypeEnum getLockObjectType() {
        return object.getType();
    }

    public String getUser() {
        return object.getUser();
    }

    public Long getTime() {
        return object.getTime();
    }
}
