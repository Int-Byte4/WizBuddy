package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployeeInfo {
    private int employeeCode;
    private String employeeName;
    private String employeePassword;
    private String employeePhone;
    private LocalDate employeeHealthDate;
    private LocalDateTime updatedAt;
}
