package com.intbyte.wizbuddy.comment.infrastructure.repository;


import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findBySubsCodeAndCommentAdoptedState(int subsCode, boolean commentAdoptedState);
}
