package com.intbyte.wizbuddy.user.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertEmployeeVO {
    private RequestSignInUserVO newUser;
    private RequestRegisterEmployeeVO newEmployee;
}