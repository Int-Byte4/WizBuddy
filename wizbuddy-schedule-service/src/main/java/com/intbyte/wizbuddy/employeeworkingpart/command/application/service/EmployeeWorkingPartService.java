package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseRegistEmployeeVO;

public interface EmployeeWorkingPartService {
    ResponseRegistEmployeeVO registSchedulePerEmployee(
            ResponseRegistEmployeeVO responseRegistEmployeeWorkingPartVO);
    void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO);
    void deleteSchedule(int workingPartCode);

    EmployeeWorkingPart validateWriterWorkingPart(SubsBoard subsBoard);

    EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer);

    void updateWorkingPart(EmployeeWorkingPart writer, EmployeeWorkingPart matchingCommentAuthor);

    EmployeeWorkingPart findEmployeeWorkingPartCode(int workingPartCode);
}
