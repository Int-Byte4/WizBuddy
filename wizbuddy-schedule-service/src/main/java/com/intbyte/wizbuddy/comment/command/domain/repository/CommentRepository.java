package com.intbyte.wizbuddy.comment.command.domain.repository;


import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commandCommentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findBySubsCodeAndCommentAdoptedState(int subsCode, boolean commentAdoptedState);

}
