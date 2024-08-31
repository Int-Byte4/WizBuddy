package com.intbyte.wizbuddy.user;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    EMPLOYER("Employer"),
    EMPLOYEE("Employee");

    private final String type;

    UserTypeEnum(String type) {
        this.type = type;
    }
}
