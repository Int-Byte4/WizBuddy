package com.intbyte.wizbuddy.user.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseEditEmployeeVO {
    private String employeeCode;
    private String employeeName;
    private String employeePassword;
    private String employeePhone;
    private LocalDate employeeHealthDate;
    private LocalDateTime updatedAt;
}
