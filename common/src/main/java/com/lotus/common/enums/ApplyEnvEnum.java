package com.lotus.common.enums;

public enum ApplyEnvEnum {

    LOCAL("local"),
    DEV("dev"),
    TEST("test"),
    QAS("qas"),
    PRE("pre"),
    PROD("prod");

    ApplyEnvEnum(String code) {
        this.code = code;
    };

    private String code;

    public String code() {
        return code;
    }
}
