package com.intbyte.wizbuddy.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class EmployeeWorkingPartDTO {

    private int workingPartCode;

    private String employeeCode;

    private int scheduleCode;

    private LocalDateTime workingDate;

    private String workingPartTime;

}
