package com.intbyte.wizbuddy.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterEmployeeRequest {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployeeVO newEmployee;
}