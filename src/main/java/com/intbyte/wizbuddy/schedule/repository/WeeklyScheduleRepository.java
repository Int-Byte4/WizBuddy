package com.intbyte.wizbuddy.schedule.repository;

import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Integer> {
}
