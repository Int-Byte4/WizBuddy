package com.intbyte.wizbuddy.comment.command.application.controller;

import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.application.service.CommentService;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.request.RequestAdoptCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.request.RequestDeleteCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.request.RequestInsertCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.request.RequestModifyCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseAdoptCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseDeleteCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseInsertCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseModifyCommentVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("commandCommentController")
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "댓글 등록")
    public ResponseEntity<ResponseInsertCommentVO> registerComment(@RequestBody RequestInsertCommentVO request) {
        CommentDTO commentDTO = modelMapper.map(request, CommentDTO.class);
        ResponseInsertCommentVO responseComment = commentService.registerComment(commentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/{commentCode}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<ResponseModifyCommentVO> modifyComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestModifyCommentVO request) {
        EditCommentVO editCommentVO = modelMapper.map(request, EditCommentVO.class);
        ResponseModifyCommentVO responseComment = commentService.modifyComment(commentCode, editCommentVO);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/{commentCode}/status")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<ResponseDeleteCommentVO> removeComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestDeleteCommentVO request) {
        CommentDTO deleteComment = modelMapper.map(request, CommentDTO.class);
        deleteComment.setCommentCode(commentCode);
        ResponseDeleteCommentVO responseComment = commentService.removeComment(deleteComment);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/{commentCode}/adoption")
    @Operation(summary = "댓글 채택")
    public ResponseEntity<ResponseAdoptCommentVO> adoptComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestAdoptCommentVO request) {
        CommentDTO adoptComment = modelMapper.map(request, CommentDTO.class);
        adoptComment.setCommentCode(commentCode);
        ResponseAdoptCommentVO responseComment = commentService.adoptComment(adoptComment);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

}
