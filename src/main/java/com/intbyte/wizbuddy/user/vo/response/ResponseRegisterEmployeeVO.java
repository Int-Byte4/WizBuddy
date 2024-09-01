package com.intbyte.wizbuddy.user.vo.response;

import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.vo.request.RequestRegisterUserVO;
import com.intbyte.wizbuddy.user.vo.request.RequestRegisterEmployeeVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterEmployeeVO {
    private SignInUserInfo newUser;
    private RegisterEmployeeInfo newEmployee;
}
