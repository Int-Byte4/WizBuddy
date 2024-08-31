package com.intbyte.wizbuddy.comment.service;


import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.exception.comment.CommentNotFoundException;
import com.intbyte.wizbuddy.mapper.CommentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    public CommentService(CommentMapper commentMapper, ModelMapper modelMapper,CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
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

    @Transactional
    public void registerComment(CommentDTO comments) {

        Comment comment = Comment.builder()
                .commentContent(comments.getCommentContent())
                .commentFlag(true)
                .commentAdoptedState(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subsCode(comments.getSubsCode())
                .employeeCode(comments.getEmployeeCode())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void modifyComment(int commentCode, EditCommentInfo modifyCommentInfo) {
        Comment modifycomment = commentRepository.findById(commentCode).orElseThrow(CommentNotFoundException::new);
        modifycomment.toUpdate(modifyCommentInfo);
        commentRepository.save(modifycomment);
    }

    @Transactional
    public void removeComment(CommentDTO deleteComment) {
        Comment comment = commentRepository.findById(deleteComment.getCommentCode()).orElseThrow(CommentNotFoundException::new);
        comment.toDelete();
        commentRepository.save(comment);
    }
    
}
