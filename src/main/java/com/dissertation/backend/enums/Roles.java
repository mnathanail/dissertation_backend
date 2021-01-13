package com.dissertation.backend.enums;

public enum Roles {

    CANDIDATE(1, "CANDIDATE"),
    RECRUITER(2,"RECRUITER");


    private final int key;
    private final String value;

    Roles(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
