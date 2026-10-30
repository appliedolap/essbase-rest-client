package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssLock;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.client.model.LockObject;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Locking and unlocking an object in Sample.Basic - its CalcAll script, locked by the test itself rather
 * than whatever lock somebody happened to leave behind, which is usually none.
 *
 * <p>Lives in this package because {@link EssCube#lockObject(EssLock)} wants an {@link EssLock}, and
 * outside {@link EssCube#getLockedObjects} only {@link EssLockImpl}'s package-private constructor makes
 * one.
 */
@Category(DestructiveIntegrationTest.class)
public class EssLockIT {

    private static final String SCRIPT = "CalcAll";

    private final EssServer server = ConnectionUtils.server();

    private EssCube cube;

    /** Whether this test took the lock, so the cleanup never releases someone else's. */
    private boolean locked;

    @Before
    public void findCalcAll() {
        try {
            cube = server.getApplication("Sample").getCube("Basic");
        } catch (RuntimeException e) {
            Assume.assumeNoException("this server has no Sample.Basic", e);
        }
        Assume.assumeTrue("Sample.Basic has no " + SCRIPT + " script",
                cube.getCalcScripts().stream().anyMatch(script -> SCRIPT.equals(script.getName())));
        Assume.assumeFalse(SCRIPT + " is already locked", lockOnScript().isPresent());
    }

    @After
    public void unlockScript() {
        if (locked) {
            lockOnScript().ifPresent(cube::unlockObject);
        }
    }

    @Test
    public void anObjectLockedCanBeUnlocked() {
        cube.lockObject(new EssLockImpl(ConnectionUtils.api(),
                new LockObject().name(SCRIPT).type(LockObject.TypeEnum.CALCSCRIPT)));
        locked = true;

        EssLock lock = lockOnScript().orElseThrow(() -> new AssertionError(SCRIPT + " is not listed as locked"));
        assertEquals(LockObject.TypeEnum.CALCSCRIPT, lock.getLockObjectType());

        cube.unlockObject(lock);
        assertFalse(SCRIPT + " is still locked", lockOnScript().isPresent());
    }

    private Optional<EssLock> lockOnScript() {
        return cube.getLockedObjects(0, 50).stream().filter(lock -> SCRIPT.equals(lock.getName())).findFirst();
    }

}
