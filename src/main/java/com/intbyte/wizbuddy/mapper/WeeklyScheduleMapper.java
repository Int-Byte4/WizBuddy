package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeeklyScheduleMapper {
    List<WeeklyScheduleDTO> selectAllSchedules();
    WeeklySchedule selectLastScheduleCode();
}
