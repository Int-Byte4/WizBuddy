package com.intbyte.wizbuddy.user.vo.response;

import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.user.vo.request.RequestRegisterUserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterEmployerVO {
    private SignInUserInfo newUser;
    private RegisterEmployerInfo newEmployer;
}
