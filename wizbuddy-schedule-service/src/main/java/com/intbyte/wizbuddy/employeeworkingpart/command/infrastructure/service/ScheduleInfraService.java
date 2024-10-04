package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

public interface ScheduleInfraService {

    WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode);

    EmployeeWorkingPartDTO getEmployeeWorkingPartCode(int workingPartCode);

    EmployeeWorkingPart validateWriterWorkingPart(SubsBoardDTO subsBoard);

    EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer);
}
