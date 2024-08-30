package com.intbyte.wizbuddy.schedule.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "weeklySchedule")
@Table(name = "weekly_schedule")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class WeeklySchedule {

    @Id
    @Column
    private int scheduleCode;

    @Column
    private boolean scheduleFlag;

    @Column
    private Date scheduleStartDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;




}
