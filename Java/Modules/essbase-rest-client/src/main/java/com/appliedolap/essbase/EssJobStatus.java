package com.appliedolap.essbase;

public enum EssJobStatus {

    IN_PROGRESS(100),
    COMPLETED(200),
    COMPLETED_WITH_WARNINGS(300),
    FAILED(400);

    private int code;

    EssJobStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EssJobStatus fromCode(int code) {
        for (EssJobStatus jobStatus : EssJobStatus.values()) {
            if (jobStatus.getCode() == code) {
                return jobStatus;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

}