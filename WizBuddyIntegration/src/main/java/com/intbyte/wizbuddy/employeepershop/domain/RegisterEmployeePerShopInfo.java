package com.intbyte.wizbuddy.employeepershop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeePerShopInfo {
    private int shopCode;
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
