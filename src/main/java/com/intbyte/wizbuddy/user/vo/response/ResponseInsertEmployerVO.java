package com.intbyte.wizbuddy.user.vo.response;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertEmployerVO {
    private SignInUserInfo newUser;
    private RegisterEmployerInfo newEmployer;
}
