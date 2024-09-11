package com.intbyte.wizbuddy.comment.query.domain.repository;

import com.intbyte.wizbuddy.comment.query.domain.aggregate.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectAllComment();
    Comment selectCommentById(int code);
    List<Comment> selectCommentBySubsCode(int subsCode);
    List<Comment> selectCommentByEmployeeCode(String employeeCode);
}
