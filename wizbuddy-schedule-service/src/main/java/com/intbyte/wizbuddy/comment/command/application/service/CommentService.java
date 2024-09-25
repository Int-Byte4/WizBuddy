package com.intbyte.wizbuddy.comment.command.application.service;

import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseAdoptCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseDeleteCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseInsertCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseModifyCommentVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentService {

    ResponseInsertCommentVO registerComment(CommentDTO comments);

    ResponseModifyCommentVO modifyComment(int commentCode, EditCommentVO modifyCommentInfo);

    ResponseDeleteCommentVO removeComment(CommentDTO deleteComment);

    List<CommentDTO> findCommentsBySubsCode(int subsCode);

    ResponseAdoptCommentVO adoptComment(CommentDTO adoptComment);
}
