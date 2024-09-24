package com.intbyte.wizbuddy.comment.command.infrastructure.service;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;

public interface InfraCommentService {

    void handleAdoptProcess(Comment comment);

    SubsBoard getBySubsCode(int SubsCode);
}
