package com.intbyte.wizbuddy.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "employee")
@Table(name = "employee")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @Column
    private int employeeCode;

    @Column
    private String employeeName;

    @Column
    private String employeeEmail;

    @Column
    private String employeePassword;

    @Column
    private String employeePhone;

    @Column
    private boolean employeeFlag;

    @Column
    private boolean employeeBlackState;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
