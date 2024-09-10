package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.application.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;

public interface EmployeeWorkingPartService {
    EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart);
    void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO);
    void deleteSchedule(int workingPartCode);
}
