package com.intbyte.wizbuddy.board.command.infrastructure.service;


import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.query.application.service.SubsBoardService;
import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.application.service.CommentService;
import com.intbyte.wizbuddy.comment.command.infrastructure.service.InfraCommentService;
import com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service.ScheduleInfraService;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InfraSubsBoardService")
@RequiredArgsConstructor
public class InfraSubsBoardServiceImpl implements InfraSubsBoardService {
    private final CommentService commentService;
    private final ScheduleInfraService scheduleInfraService;

    @Override
    public void SubsBoardByCommentFlag(int subsCode) {
        List<CommentDTO> comments = commentService.findCommentsBySubsCode(subsCode);
        for (CommentDTO commentDTO : comments) {
            commentService.removeComment(commentDTO);
        }
    }

    @Override
    public EmployeeWorkingPartDTO getEmployeeWorkingPartCode(int workingPartCode) {
        return scheduleInfraService.getEmployeeWorkingPartCode(workingPartCode);
    }


}
