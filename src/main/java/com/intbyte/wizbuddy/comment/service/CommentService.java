package com.intbyte.wizbuddy.comment.service;


import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.exception.comment.CommentNotFoundException;
import com.intbyte.wizbuddy.mapper.CommentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final ModelMapper modelMapper;

    public CommentService(CommentMapper commentMapper, ModelMapper modelMapper) {
        this.commentMapper = commentMapper;
        this.modelMapper = modelMapper;
    }

    public List<CommentDTO> findAllComment() {
        List<Comment> commentList = commentMapper.selectAllComment();
        return  commentList.stream()
                .map(comment-> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO findCommentById(int code) {
        Comment comment = commentMapper.selectCommentById(code);

        if(comment == null) {
            throw new CommentNotFoundException();

        }
        return modelMapper.map(comment, CommentDTO.class);
    }


}
