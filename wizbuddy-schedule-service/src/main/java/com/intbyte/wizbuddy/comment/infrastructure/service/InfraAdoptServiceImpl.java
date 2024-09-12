package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.board.command.application.service.SubsBoardService;
import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.application.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("InfraAdoptService")
@RequiredArgsConstructor
public class InfraAdoptServiceImpl implements InfraAdoptService {

    private final SubsBoardService subsBoardService;
    private final EmployeeWorkingPartService employeeWorkingPartService;

    @Override
    public void handleAdoptProcess(Comment comment) {
        SubsBoard subsBoard = subsBoardService.validateSubsBoard(comment.getSubsCode());
        EmployeeWorkingPart writer = employeeWorkingPartService.validateWriterWorkingPart(subsBoard);
        EmployeeWorkingPart matchingCommentAuthor = employeeWorkingPartService.validateCommentAuthorWorkingPart(comment, writer);
        employeeWorkingPartService.updateWorkingPart(writer, matchingCommentAuthor);
    }

}
