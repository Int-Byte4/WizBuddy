package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.vo.response.ResponseRegisterEmployeeVO;
import com.intbyte.wizbuddy.user.vo.response.ResponseRegisterEmployerVO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Transactional
    ResponseRegisterEmployerVO signInEmployer(SignInUserInfo signInUserInfo, RegisterEmployerInfo registerEmployerInfo);

    @Transactional
    ResponseRegisterEmployeeVO signInEmployee(SignInUserInfo signInUserInfo, RegisterEmployeeInfo registerEmployeeInfo);

}
