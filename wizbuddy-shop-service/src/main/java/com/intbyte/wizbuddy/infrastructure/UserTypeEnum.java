package com.intbyte.wizbuddy.infrastructure;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    EMPLOYER("EMPLOYER"),
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN");

    private final String userType;

    UserTypeEnum(String userType) {
        this.userType = userType;
    }
}
