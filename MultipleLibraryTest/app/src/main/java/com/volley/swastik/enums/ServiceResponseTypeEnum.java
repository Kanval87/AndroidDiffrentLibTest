package com.volley.swastik.enums;

public enum ServiceResponseTypeEnum {
    NONE(0),
    DATABASE(1),
    SERVICE(2);

    private final int ordinal;

    ServiceResponseTypeEnum(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
