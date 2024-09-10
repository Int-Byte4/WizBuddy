package com.intbyte.wizbuddy.employeepershop.query.repository;

import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeePerShopMapper {
    List<EmployeePerShopDTO> findEmployeePerShopById(String employeeCode);

    List<EmployeePerShopDTO> findAllEmployeePerShop();

    String findEmployeeCodeByEmployeeCode(String employeeCode);

    EmployeePerShopDTO findEmployeePerShopByShopCodeAndEmployeeCode(Map<String, Object> params);

    int findShopCodeByEmployeeCode(String employeeCode);
}
