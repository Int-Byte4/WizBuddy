package com.intbyte.wizbuddy.user.vo.request;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertEmployeeVO {
    private SignInUserInfo newUser;
    private RegisterEmployeeInfo newEmployee;
}