package com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseFindEmployeeWorkingPartVO {
    private String employeeCode;
    private int scheduleCode;
    private LocalDateTime workingDate;
    private String workingPartTime;
}
