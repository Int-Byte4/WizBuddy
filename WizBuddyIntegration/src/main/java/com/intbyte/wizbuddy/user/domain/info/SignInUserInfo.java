package com.intbyte.wizbuddy.user.domain.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserInfo {
    private String userCode;
    private String userType;
    private String userEmail;
    private String userPassword;
}