package com.intbyte.wizbuddy.employeepershop.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeePerShopId implements Serializable {
    private Integer shopCode;
    private String employeeCode;
}
