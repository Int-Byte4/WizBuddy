package com.intbyte.wizbuddy.employeepershop.domain.entity;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employeepershop")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
public class EmployeePerShop {

    @EmbeddedId
    EmployeePerShopId employeePerShopId;

    @ManyToOne
    @MapsId("shopCode")
    @JoinColumn(name = "shop_code", insertable = false, updatable = false)
    private Shop shop;

    @ManyToOne
    @MapsId("employeeCode")
    @JoinColumn(name = "employee_code", insertable = false, updatable = false)
    private Employee employee;

    @Column
    private int shopHourlyWage;

    @Column
    private int shopMonthlyWage; // 월급일

    public void modify(EditEmployeePerShopInfo info, Employee employee) {
        this.shopHourlyWage = info.getShopHourlyWage();
        this.shopMonthlyWage = info.getShopMonthlyWage();
        this.employee = employee;
    }
}
