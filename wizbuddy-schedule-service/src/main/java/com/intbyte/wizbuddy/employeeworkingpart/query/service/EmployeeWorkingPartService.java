package com.intbyte.wizbuddy.employeeworkingpart.query.service;

import com.intbyte.wizbuddy.employeeworkingpart.query.vo.EmployeeWorkingPartVO;

import java.util.List;

public interface EmployeeWorkingPartService {
    List<EmployeeWorkingPartVO> findSchedule(int scheduleCode);
    List<EmployeeWorkingPartVO> findScheduleByEmployeeCode(String employeeCode);
}
