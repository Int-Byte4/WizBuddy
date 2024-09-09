package com.intbyte.wizbuddy.user.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class RequestEditEmployeeDTO {
    private String employeePassword;
    private String employeePhone;
    private LocalDate employeeHealthDate;
    private LocalDateTime updatedAt;
}
