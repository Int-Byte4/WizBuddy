package com.intbyte.wizbuddy.schedule.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate scheduleStartDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;




}
