package com.intbyte.wizbuddy.comment.query.application.service;

import com.intbyte.wizbuddy.comment.common.exception.CommentNotFoundException;
import com.intbyte.wizbuddy.comment.query.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.query.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.query.domain.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("queryCommentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<CommentDTO> findAllComment() {
        List<Comment> commentList = commentMapper.selectAllComment();
        if (commentList == null || commentList.isEmpty()) {
            throw new CommentNotFoundException();
        }
        return commentList.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findCommentById(int code) {
        Comment comment = commentMapper.selectCommentById(code);
        if (comment == null) {
            throw new CommentNotFoundException();
        }
        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsBySubsCode(int subsCode) {
        List<Comment> comments = commentMapper.selectCommentBySubsCode(subsCode);
        if (comments == null || comments.isEmpty()) {
            throw new CommentNotFoundException();
        }
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentsByEmployeeCode(String employeeCode) {
        List<Comment> comments = commentMapper.selectCommentByEmployeeCode(employeeCode);
        if (comments == null || comments.isEmpty()) {
            throw new CommentNotFoundException();
        }
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }
}
