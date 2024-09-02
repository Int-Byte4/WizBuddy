package com.intbyte.wizbuddy.user.domain.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployeeInfo {
    private String employeeCode;
    private String employeeName;
    private String employeePassword;
    private String employeePhone;
    private LocalDate employeeHealthDate;
    private LocalDateTime updatedAt;
}
