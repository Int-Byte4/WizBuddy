package com.intbyte.wizbuddy.employeepershop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePerShopMybatis {
    private Integer shopCode;
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}