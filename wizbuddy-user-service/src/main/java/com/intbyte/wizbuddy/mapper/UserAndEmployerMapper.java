package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAndEmployerMapper {
    void insertUser(SignInUserInfo signInUserInfo);

    void insertEmployer(RegisterEmployerInfo registerEmployerInfo);

}
