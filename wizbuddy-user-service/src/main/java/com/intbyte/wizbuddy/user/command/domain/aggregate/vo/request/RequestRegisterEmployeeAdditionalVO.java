package com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestRegisterEmployeeAdditionalVO {
    private String userCode;
    private String latitude;
    private String longitude;
    private int employeeWage;
    private LocalDate employeeHealthDate;
}
