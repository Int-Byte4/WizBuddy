package com.intbyte.wizbuddy.comment.command.application.service;

import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseAdoptCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseDeleteCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseInsertCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseModifyCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.infrastructure.service.InfraAdoptServiceImpl;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("commandCommentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final InfraAdoptServiceImpl infraAdoptService;

    @Transactional
    @Override
    public ResponseInsertCommentVO registerComment(CommentDTO comments) {

        Comment comment = Comment.builder()
                .commentContent(comments.getCommentContent())
                .commentFlag(true)
                .commentAdoptedState(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subsCode(comments.getSubsCode())
                .employeeCode(comments.getEmployeeCode())
                .build();

        if(false) {
            throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        }

        commentRepository.save(comment);
        return modelMapper.map(comments, ResponseInsertCommentVO.class);
    }

    @Transactional
    @Override
    public ResponseModifyCommentVO modifyComment(int commentCode, EditCommentVO modifyCommentInfo) {
        Comment modifycomment = commentRepository.findById(commentCode).orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));
        modifycomment.toUpdate(modifyCommentInfo);
        commentRepository.save(modifycomment);
        return modelMapper.map(modifycomment, ResponseModifyCommentVO.class);
    }

    @Transactional
    @Override
    public ResponseDeleteCommentVO removeComment(CommentDTO deleteComment) {
        Comment comment = commentRepository.findById(deleteComment.getCommentCode()).orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));
        comment.toDelete();
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseDeleteCommentVO.class);
    }

    @Transactional
    @Override
    public ResponseAdoptCommentVO adoptComment(CommentDTO adoptComment) {
        Comment comment = commentRepository.findById(adoptComment.getCommentCode())
                .orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));

        Comment existingComment = commentRepository.findBySubsCodeAndCommentAdoptedState(comment.getSubsCode(), true);
        if (existingComment != null) {
            throw new CommonException(StatusEnum.ALREADY_ADOPTED);
        }
        comment.toAdopt();
        commentRepository.save(comment);
        infraAdoptService.handleAdoptProcess(comment);
        return modelMapper.map(comment, ResponseAdoptCommentVO.class);
    }

}
