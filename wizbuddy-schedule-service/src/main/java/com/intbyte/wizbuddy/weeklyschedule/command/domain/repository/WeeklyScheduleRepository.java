package com.intbyte.wizbuddy.weeklyschedule.command.domain.repository;

import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.entity.WeeklySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Integer> {
}
