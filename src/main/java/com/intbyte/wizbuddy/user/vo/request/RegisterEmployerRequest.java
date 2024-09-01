package com.intbyte.wizbuddy.user.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterEmployerRequest {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployerVO newEmployer;
}
