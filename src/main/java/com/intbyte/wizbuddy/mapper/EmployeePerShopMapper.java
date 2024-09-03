package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeePerShopMapper {
    EmployeePerShopDTO findEmployeePerShopById(String employeeCode);

    List<EmployeePerShopDTO> findAllEmployeePerShop();

    int findShopCodeByEmployeeCode(String employeeCode);

    EmployeePerShopDTO findByShopCode(Map<String, Object> param);
}
