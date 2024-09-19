package com.intbyte.wizbuddy.user.command.domain.aggregate;

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
