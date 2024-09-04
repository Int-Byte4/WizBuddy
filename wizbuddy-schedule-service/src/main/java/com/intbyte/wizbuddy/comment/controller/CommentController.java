package com.intbyte.wizbuddy.comment.controller;

import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.service.CommentService;
import com.intbyte.wizbuddy.comment.vo.request.RequestAdoptCommentVO;
import com.intbyte.wizbuddy.comment.vo.request.RequestDeleteCommentVO;
import com.intbyte.wizbuddy.comment.vo.request.RequestInsertCommentVO;
import com.intbyte.wizbuddy.comment.vo.request.RequestModifyCommentVO;
import com.intbyte.wizbuddy.comment.vo.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ResponseFindCommentVO>> getAllComments() {
        List<CommentDTO> commentDTOs = commentService.findAllComment();

        List<ResponseFindCommentVO> commentVOs = commentDTOs.stream()
                .map(dto -> ResponseFindCommentVO.builder()
                        .commentCode(dto.getCommentCode())
                        .commentContent(dto.getCommentContent())
                        .commentFlag(dto.isCommentFlag())
                        .commentAdoptedState(dto.isCommentAdoptedState())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .subsCode(dto.getSubsCode())
                        .employeeCode(dto.getEmployeeCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentVOs);
    }

    @GetMapping("/{commentCode}")
    public ResponseEntity<ResponseFindCommentVO> getCommentById(@PathVariable("commentCode") int commentCode) {
        CommentDTO commentDTO = commentService.findCommentById(commentCode);

        ResponseFindCommentVO commentVO = ResponseFindCommentVO.builder()
                .commentCode(commentDTO.getCommentCode())
                .commentContent(commentDTO.getCommentContent())
                .commentFlag(commentDTO.isCommentFlag())
                .commentAdoptedState(commentDTO.isCommentAdoptedState())
                .createdAt(commentDTO.getCreatedAt())
                .updatedAt(commentDTO.getUpdatedAt())
                .subsCode(commentDTO.getSubsCode())
                .employeeCode(commentDTO.getEmployeeCode())
                .build();

        return ResponseEntity.ok(commentVO);
    }


    @GetMapping("/subs/{subsCode}")
    public ResponseEntity<List<ResponseFindCommentVO>> getCommentsBySubsCode(@PathVariable("subsCode") int subsCode) {
        List<CommentDTO> commentDTOs = commentService.getCommentsBySubsCode(subsCode);

        List<ResponseFindCommentVO> comments = commentDTOs.stream()
                .map(dto -> ResponseFindCommentVO.builder()
                        .commentCode(dto.getCommentCode())
                        .commentContent(dto.getCommentContent())
                        .commentFlag(dto.isCommentFlag())
                        .commentAdoptedState(dto.isCommentAdoptedState())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .subsCode(dto.getSubsCode())
                        .employeeCode(dto.getEmployeeCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/employee/{employeeCode}")
    public ResponseEntity<List<ResponseFindCommentVO>> getCommentsByEmployeeCode(@PathVariable("employeeCode") String employeeCode) {
        List<CommentDTO> commentDTOs = commentService.getCommentsByEmployeeCode(employeeCode);
        List<ResponseFindCommentVO> comments = commentDTOs.stream()
                .map(dto -> ResponseFindCommentVO.builder()
                        .commentCode(dto.getCommentCode())
                        .commentContent(dto.getCommentContent())
                        .commentFlag(dto.isCommentFlag())
                        .commentAdoptedState(dto.isCommentAdoptedState())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .subsCode(dto.getSubsCode())
                        .employeeCode(dto.getEmployeeCode())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<ResponseInsertCommentVO> registerComment(@RequestBody RequestInsertCommentVO request) {
        CommentDTO commentDTO = modelMapper.map(request, CommentDTO.class);
        ResponseInsertCommentVO responseComment = commentService.registerComment(commentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/update/{commentCode}")
    public ResponseEntity<ResponseModifyCommentVO> modifyComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestModifyCommentVO request) {
        EditCommentInfo editCommentInfo = modelMapper.map(request, EditCommentInfo.class);
        ResponseModifyCommentVO responseComment = commentService.modifyComment(commentCode, editCommentInfo);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/delete/{commentCode}")
    public ResponseEntity<ResponseDeleteCommentVO> removeComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestDeleteCommentVO request) {
        CommentDTO deleteComment = modelMapper.map(request, CommentDTO.class);
        deleteComment.setCommentCode(commentCode);
        ResponseDeleteCommentVO responseComment = commentService.removeComment(deleteComment);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

    @PatchMapping("/adopt/{commentCode}")
    public ResponseEntity<ResponseAdoptCommentVO> adoptComment(@PathVariable("commentCode") int commentCode, @RequestBody RequestAdoptCommentVO request) {
        CommentDTO adoptComment = modelMapper.map(request, CommentDTO.class);
        adoptComment.setCommentCode(commentCode);
        ResponseAdoptCommentVO responseComment = commentService.adoptComment(adoptComment);
        return ResponseEntity.status(HttpStatus.OK).body(responseComment);
    }

}
