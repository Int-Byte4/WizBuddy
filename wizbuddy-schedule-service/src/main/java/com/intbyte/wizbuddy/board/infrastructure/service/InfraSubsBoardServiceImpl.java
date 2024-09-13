package com.intbyte.wizbuddy.board.infrastructure.service;

import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InfraSubsBoardService")
@RequiredArgsConstructor
public class InfraSubsBoardServiceImpl implements InfraSubsBoardService {
    private final CommentService commentService;

    @Override
    public void SubsBoardByCommentFlag(int subsCode) {
        List<CommentDTO> comments = commentService.findCommentsBySubsCode(subsCode);
        for (CommentDTO commentDTO : comments) {
            commentService.removeComment(commentDTO);
        }
    }
}
