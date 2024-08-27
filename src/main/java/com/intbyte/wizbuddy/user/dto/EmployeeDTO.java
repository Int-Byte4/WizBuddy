package com.intbyte.wizbuddy.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    private int employeeCode;

    private String employeeName;

    private String employeeEmail;

    private String employeePassword;

    private String employeePhone;

    private boolean employeeFlag;

    private boolean employeeBlackState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
