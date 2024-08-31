package com.intbyte.wizbuddy.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    private String employeeCode;

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
