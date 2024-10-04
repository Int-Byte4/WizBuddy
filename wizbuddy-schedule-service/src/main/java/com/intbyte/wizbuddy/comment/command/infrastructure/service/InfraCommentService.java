package com.intbyte.wizbuddy.comment.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;

public interface InfraCommentService {

    void handleAdoptProcess(Comment comment);

    SubsBoardDTO findSubsBoardById(int subsCode);
}
