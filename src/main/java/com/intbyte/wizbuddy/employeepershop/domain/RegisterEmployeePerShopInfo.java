package com.intbyte.wizbuddy.employeepershop.domain;

import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeePerShopInfo {
    private Shop shop;
    private Employee employee;
    private int shopHourlyWage;
    private int shopMonthlyWage;
}
