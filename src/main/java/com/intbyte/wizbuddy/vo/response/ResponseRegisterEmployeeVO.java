package com.intbyte.wizbuddy.vo.response;

import com.intbyte.wizbuddy.vo.request.RequestRegisterEmployeeVO;
import com.intbyte.wizbuddy.vo.request.RequestRegisterUserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterEmployeeVO {
    private RequestRegisterUserVO newUser;
    private RequestRegisterEmployeeVO newEmployee;
}
