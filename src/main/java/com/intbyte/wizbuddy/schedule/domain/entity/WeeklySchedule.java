package com.intbyte.wizbuddy.schedule.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(name="schedule_code")
    private int scheduleCode;

    @Column(nullable = false, name = "schedule_flag")
    private boolean scheduleFlag;

    @Column(nullable = false, name = "schedule_start_date")
    private LocalDate scheduleStartDate;

//    @CreationTimestamp  // 이 어노테이션을 사용하면 Hibernate가 자동으로 현재 시간을 설정합니다.
    @Column(nullable = false, updatable = false, name="created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;




}
