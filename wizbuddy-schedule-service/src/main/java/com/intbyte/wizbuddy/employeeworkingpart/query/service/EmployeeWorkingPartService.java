package com.intbyte.wizbuddy.employeeworkingpart.query.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.query.vo.EmployeeWorkingPartVO;

import java.util.List;

public interface EmployeeWorkingPartService {
    List<EmployeeWorkingPartVO> findSchedule(int scheduleCode);
    List<EmployeeWorkingPartVO> findScheduleByEmployeeCode(String employeeCode);
    EmployeeWorkingPartDTO findEmployeeWorkingPartCode(int workingPartCode);

    EmployeeWorkingPart validateWriterWorkingPart(SubsBoardDTO subsBoard);

    EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer);
}
