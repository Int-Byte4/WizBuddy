package com.intbyte.wizbuddy.user.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertEmployerVO {
    private RequestSignInUserVO newUser;
    private RequestRegisterEmployerVO newEmployer;
}
