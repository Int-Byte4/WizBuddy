package com.intbyte.wizbuddy.user.vo.request;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertEmployerVO {
    private SignInUserInfo newUser;
    private RegisterEmployerInfo newEmployer;
}
