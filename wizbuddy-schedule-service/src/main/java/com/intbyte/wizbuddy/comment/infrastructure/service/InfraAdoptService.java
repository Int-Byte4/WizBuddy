package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;

public interface InfraAdoptService {

    void handleAdoptProcess(Comment comment);

}
