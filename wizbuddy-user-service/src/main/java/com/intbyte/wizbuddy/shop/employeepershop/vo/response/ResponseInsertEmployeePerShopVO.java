package com.intbyte.wizbuddy.shop.employeepershop.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInsertEmployeePerShopVO {
    private int shopCode;
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}