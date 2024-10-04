package com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegistWeeklyScheduleVO {
    private boolean scheduleFlag;
    private LocalDate scheduleStartDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
