package com.intbyte.wizbuddy.user.command.domain.entity.vo.response;

import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestSignInUserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertEmployeeVO {
    private RequestSignInUserVO newUser;
    private RequestRegisterEmployeeVO newEmployee;
}
