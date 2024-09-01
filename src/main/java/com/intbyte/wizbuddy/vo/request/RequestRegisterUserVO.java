package com.intbyte.wizbuddy.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegisterUserVO {
    private String userCode;
    private String userType;
    private String userEmail;
    private String userPassword;
}
