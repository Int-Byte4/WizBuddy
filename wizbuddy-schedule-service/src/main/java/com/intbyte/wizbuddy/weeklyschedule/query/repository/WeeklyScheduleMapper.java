package com.intbyte.wizbuddy.weeklyschedule.query.repository;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WeeklyScheduleMapper {

    List<WeeklyScheduleDTO> selectAllSchedules();

    WeeklyScheduleDTO findScheduleByStartDate(LocalDate scheduleStartDate);
    WeeklyScheduleDTO selectScheduleByScheduleCode(int scheduleCode);
}
