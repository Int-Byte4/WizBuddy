package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeInfo {
    private int employeeCode;
    private String employeeName;
    private String employeeEmail;
    private String employeePassword;
    private String employeePhone;
    private boolean employeeFlag;
    private String latitude;
    private String longitude;
    private int employeeWage;
    private LocalDate employeeHealthDate;
    private boolean employeeBlackState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
