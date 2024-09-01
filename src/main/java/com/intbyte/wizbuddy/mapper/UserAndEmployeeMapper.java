package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAndEmployeeMapper {
    void insertUser(SignInUserInfo signInUserInfo);

    void insertEmployee(RegisterEmployeeInfo registerEmployeeInfo);

    Employee getUserByEmployeeEmail(String userEmail);
}
