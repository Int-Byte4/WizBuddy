package com.intbyte.wizbuddy.employeeworkingpart.query.repository;


import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeWorkingPartMapper {

    List<EmployeeWorkingPartDTO> findEmployeeByEmployeeCode(String employeeCode);

    List<EmployeeWorkingPartDTO> selectScheduleByScheduleCode(int scheduleCode);

    EmployeeWorkingPart selectScheduleByWorkingPartCode(int workingPartCode);

    void editSchedule(int workingPartCode, String employeeCode);
}
