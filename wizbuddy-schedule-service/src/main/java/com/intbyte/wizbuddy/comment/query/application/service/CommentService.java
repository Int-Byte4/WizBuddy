package com.intbyte.wizbuddy.comment.query.application.service;

import com.intbyte.wizbuddy.comment.query.application.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> findAllComment();

    CommentDTO findCommentById(int code);

    List<CommentDTO> getCommentsBySubsCode(int subsCode);

    List<CommentDTO> getCommentsByEmployeeCode(String employeeCode);
}
