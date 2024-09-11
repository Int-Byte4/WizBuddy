package com.intbyte.wizbuddy.comment.query.application.controller;


import com.intbyte.wizbuddy.comment.query.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.query.application.service.CommentService;
import com.intbyte.wizbuddy.comment.query.domain.aggregate.vo.response.ResponseFindCommentVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("queryCommentController")
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "댓글 전체 조회")
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
    @Operation(summary = "댓글 1개 조회")
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
    @Operation(summary = "게시글별 댓글 전체 조회")
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
    @Operation(summary = "직원별 댓글 전체 조회")
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

}
