package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class WeeklyScheduleDTO {
    private int scheduleCode;
    private boolean scheduleFlag;
    private LocalDate scheduleStartDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}