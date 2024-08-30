package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.SignInEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAndEmployerMapper {
    void insertUser(SignInUserInfo signInUserInfo);

    void insertEmployer(SignInEmployerInfo signInEmployerInfo);
}
