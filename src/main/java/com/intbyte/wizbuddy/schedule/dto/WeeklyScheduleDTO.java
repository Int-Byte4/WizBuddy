package com.intbyte.wizbuddy.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WeeklyScheduleDTO {

    private int scheduleCode;

    private boolean scheduleFlag;

    private Date scheduleStartDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}