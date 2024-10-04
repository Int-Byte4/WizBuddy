package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseRegistEmployeeVO;

public interface EmployeeWorkingPartService {
    ResponseRegistEmployeeVO registSchedulePerEmployee(ResponseRegistEmployeeVO responseRegistEmployeeWorkingPartVO);
    void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO);
    void deleteSchedule(int workingPartCode);
    void updateWorkingPart(EmployeeWorkingPart writer, EmployeeWorkingPart matchingCommentAuthor);

}
