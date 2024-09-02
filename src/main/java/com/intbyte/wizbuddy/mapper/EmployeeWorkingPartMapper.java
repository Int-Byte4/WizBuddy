package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeWorkingPartMapper {
    EmployeeWorkingPart findEmployeeByEmployeeCode(String employeeCode);

    List<EmployeeWorkingPart> selectScheduleByScheduleCode(int scheduleCode);

    List<EmployeeWorkingPart> selectAllScheduleByEmployeeCode(String employeeCode);

    EmployeeWorkingPart selectScheduleByWorkingPartCode(int workingPartCode);

    int updateScheduleByComment(int subsCode, int commentCode);
}
