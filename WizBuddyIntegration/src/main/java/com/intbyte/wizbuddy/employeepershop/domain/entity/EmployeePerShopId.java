package com.intbyte.wizbuddy.employeepershop.domain.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeePerShopId implements Serializable {
    private Integer shopCode;
    private String employeeCode;
}
