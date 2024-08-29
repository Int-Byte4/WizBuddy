package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeWorkingPartMapper {
    List<EmployeeWorkingPartDTO> selectSchedule(int scheduleCode);
}
