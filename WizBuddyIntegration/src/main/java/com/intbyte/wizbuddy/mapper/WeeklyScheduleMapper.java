package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WeeklyScheduleMapper {

    List<WeeklyScheduleDTO> selectAllSchedules();

    WeeklyScheduleDTO findScheduleByStartDate(LocalDate scheduleStartDate);
}
