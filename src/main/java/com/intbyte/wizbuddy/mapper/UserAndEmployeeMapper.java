package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.info.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAndEmployeeMapper {
    void insertUser(SignInUserInfo signInUserInfo);

    void insertEmployee(RegisterEmployeeInfo registerEmployeeInfo);

}
