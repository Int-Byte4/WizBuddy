package com.intbyte.wizbuddy.employeeworkingpart.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
@Builder
public class EmployeeWorkingPartDTO {
    private int workingPartCode;
    private String employeeCode;
    private int scheduleCode;
    private LocalDateTime workingDate;
    private String workingPartTime;

}
