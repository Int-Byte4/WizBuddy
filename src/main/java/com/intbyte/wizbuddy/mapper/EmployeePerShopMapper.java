package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.employeepershop.domain.EmployeePerShopMybatis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeePerShopMapper {
    EmployeePerShopMybatis findEmployeePerShopById(Integer shopCode, String employeeCode);

    List<EmployeePerShopMybatis> findAllEmployeePerShop();
}
