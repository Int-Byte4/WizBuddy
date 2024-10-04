package com.intbyte.wizbuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class EmployeeAdditionalDTO {

    @JsonProperty("user_code")
    private String userCode;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("employee_wage")
    private int employeeWage;

    @JsonProperty("employee_health_date")
    private LocalDate employeeHealthDate;
}
