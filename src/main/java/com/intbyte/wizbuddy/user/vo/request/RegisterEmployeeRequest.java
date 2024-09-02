package com.intbyte.wizbuddy.user.vo.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterEmployeeRequest {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployeeVO newEmployee;
}