package com.appliedolap.essbase;

/**
 * What the server says about the machine it is running on - the only thing in the REST API that
 * does.
 */
public final class EssMaintenanceLimits {

    private final EssResourceLimit disk;

    private final EssResourceLimit ram;

    public EssMaintenanceLimits(EssResourceLimit disk, EssResourceLimit ram) {
        this.disk = disk;
        this.ram = ram;
    }

    public EssResourceLimit getDisk() {
        return disk;
    }

    public EssResourceLimit getRam() {
        return ram;
    }

    @Override
    public String toString() {
        return "disk " + disk + ", ram " + ram;
    }

}
