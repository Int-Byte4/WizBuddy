package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.query.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

import com.intbyte.wizbuddy.weeklyschedule.query.service.WeeklyScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleInfraServiceImpl implements ScheduleInfraService{

    private final WeeklyScheduleService weeklyScheduleService;
    private final EmployeeWorkingPartService employeeWorkingPartService;


    @Override
    public WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode){
        return weeklyScheduleService.findScheduleByScheduleCode(scheduleCode);
    }

    @Override
    public EmployeeWorkingPartDTO getEmployeeWorkingPartCode(int workingPartCode) { // 워킹파트코드 조회
        return employeeWorkingPartService.findEmployeeWorkingPartCode(workingPartCode);
    }

    @Override
    public EmployeeWorkingPart validateWriterWorkingPart(SubsBoardDTO subsBoard) {
        return employeeWorkingPartService.validateWriterWorkingPart(subsBoard);
    }

    @Override
    public EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer) {
        return employeeWorkingPartService.validateCommentAuthorWorkingPart(comment, writer);
    }
}
