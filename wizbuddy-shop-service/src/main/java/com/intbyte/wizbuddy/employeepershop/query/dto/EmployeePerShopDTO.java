package com.intbyte.wizbuddy.employeepershop.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class EmployeePerShopDTO {
    private int shopCode;
    private String employeeCode;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
