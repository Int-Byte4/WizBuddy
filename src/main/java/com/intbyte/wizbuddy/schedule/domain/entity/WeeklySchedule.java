package com.intbyte.wizbuddy.schedule.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "weeklySchedule")
@Table(name = "weeklySchedule")
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
