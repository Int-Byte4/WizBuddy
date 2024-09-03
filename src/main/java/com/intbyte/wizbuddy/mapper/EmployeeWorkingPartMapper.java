package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeWorkingPartMapper {
    List<EmployeeWorkingPart> findEmployeeByEmployeeCode(String employeeCode);

    List<EmployeeWorkingPart> selectScheduleByScheduleCode(int scheduleCode);

    EmployeeWorkingPart selectScheduleByWorkingPartCode(int workingPartCode);

    void editSchedule(int workingPartCode, String employeeCode);
}
