package com.intbyte.wizbuddy.employeeworkingpart.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EmployeeWorkingPartVO {
    private int workingPartCode;
    private String employeeCode;
    private int scheduleCode;
    private LocalDateTime workingDate;
    private String workingPartTime;
}
