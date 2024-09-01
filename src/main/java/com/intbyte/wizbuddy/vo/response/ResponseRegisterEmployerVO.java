package com.intbyte.wizbuddy.vo.response;

import com.intbyte.wizbuddy.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.vo.request.RequestRegisterUserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterEmployerVO {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployerVO newEmployer;
}
