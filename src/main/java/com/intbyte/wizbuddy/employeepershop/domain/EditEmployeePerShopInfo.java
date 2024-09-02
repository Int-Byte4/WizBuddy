package com.intbyte.wizbuddy.employeepershop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditEmployeePerShopInfo {
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
