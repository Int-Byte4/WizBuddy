package com.intbyte.wizbuddy.schedule.domain.entity;


import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
