package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterKakaoUserVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterUserVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertUserVO;
import com.intbyte.wizbuddy.user.query.dto.KakaoUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Transactional
    ResponseInsertUserVO signInUser(RequestRegisterUserVO requestRegisterUserVO);

    void registerKakaoUser(RequestRegisterKakaoUserVO requestRegisterUserVO);

    @Transactional
    void modifyUser(String userCode, RequestEditUserDTO userDTO);

    @Transactional
    void deleteUser(String userCode);

    KakaoUserDTO processKakaoUser(String code);
}
