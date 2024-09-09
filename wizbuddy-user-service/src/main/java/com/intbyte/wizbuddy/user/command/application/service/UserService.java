package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestSignInUserVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.response.ResponseInsertEmployerVO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Transactional
    ResponseInsertEmployerVO signInEmployer(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployerVO requestRegisterEmployerVO);

    @Transactional
    ResponseInsertEmployeeVO signInEmployee(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployeeVO requestRegisterEmployeeVO);
}
