package com.intbyte.wizbuddy.employeepershop.query.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class EmployeePerShopDTO {
    private int shopCode;
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
