package com.intbyte.wizbuddy.user.command.domain.entity.vo.response;

import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestSignInUserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertEmployerVO {
    private RequestSignInUserVO newUser;
    private RequestRegisterEmployerVO newEmployer;
}
