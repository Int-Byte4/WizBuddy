package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.info.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import com.intbyte.wizbuddy.user.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.vo.response.ResponseInsertEmployerVO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Transactional
    ResponseInsertEmployerVO signInEmployer(SignInUserInfo signInUserInfo, RegisterEmployerInfo registerEmployerInfo);

    @Transactional
    ResponseInsertEmployeeVO signInEmployee(SignInUserInfo signInUserInfo, RegisterEmployeeInfo registerEmployeeInfo);

}
