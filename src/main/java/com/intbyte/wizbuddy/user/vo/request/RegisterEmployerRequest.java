package com.intbyte.wizbuddy.user.vo.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterEmployerRequest {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployerVO newEmployer;
}
