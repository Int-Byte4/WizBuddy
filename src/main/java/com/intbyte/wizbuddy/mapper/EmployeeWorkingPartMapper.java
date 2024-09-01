package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeWorkingPartMapper {
    EmployeeWorkingPartDTO findEmployeeByEmployeeCode(int employeeCode);

    List<EmployeeWorkingPartDTO> selectSchedule(int scheduleCode);

    List<EmployeeWorkingPartDTO> selectScheduleByEmployeeCode(int scheduleCode);

    int updateScheduleByComment(int subsCode, int commentCode);
}
