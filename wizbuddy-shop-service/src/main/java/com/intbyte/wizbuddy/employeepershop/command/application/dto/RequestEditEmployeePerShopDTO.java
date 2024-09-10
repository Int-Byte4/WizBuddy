package com.intbyte.wizbuddy.employeepershop.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class RequestEditEmployeePerShopDTO {
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
