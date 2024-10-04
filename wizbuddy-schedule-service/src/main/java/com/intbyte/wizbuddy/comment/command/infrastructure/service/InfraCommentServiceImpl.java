package com.intbyte.wizbuddy.comment.command.infrastructure.service;


import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.query.application.service.SubsBoardService;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.application.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service.ScheduleInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("InfraCommentService")
@RequiredArgsConstructor
public class InfraCommentServiceImpl implements InfraCommentService {

    private final SubsBoardService subsBoardService;
    private final EmployeeWorkingPartService employeeWorkingPartService;
    private final ScheduleInfraService scheduleInfraService;

    @Override
    public void handleAdoptProcess(Comment comment) {
        SubsBoardDTO subsBoard = subsBoardService.findSubsBoardById(comment.getSubsCode());
        EmployeeWorkingPart writer = scheduleInfraService.validateWriterWorkingPart(subsBoard);
        EmployeeWorkingPart matchingCommentAuthor = scheduleInfraService.validateCommentAuthorWorkingPart(comment, writer);
        employeeWorkingPartService.updateWorkingPart(writer, matchingCommentAuthor);
    }

    @Override
    public SubsBoardDTO findSubsBoardById(int subsCode) {
        return subsBoardService.findSubsBoardById(subsCode);
    }

}
