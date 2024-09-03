package com.intbyte.wizbuddy.comment.controller;

import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.service.CommentService;
import com.intbyte.wizbuddy.comment.vo.response.ResponseFindCommentVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subsboards/comment")
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

//    @GetMapping("/subsBoard/{subsBoardCode}")
//    public ResponseEntity<List<ResponseFindCommentVO>> getCommentsByCommentCode(@PathVariable("commentCode") int commentCode) {
//        List<CommentDTO> commentDTOs = commentService.findCommentById(commentCode);
//
//        List<ResponseFindCommentVO> commentVOs = commentDTOs.stream()
//                .map(dto -> ResponseFindCommentVO.builder()
//                        .commentCode(dto.getCommentCode())
//                        .commentContent(dto.getCommentContent())
//                        .commentFlag(dto.isCommentFlag())
//                        .commentAdoptedState(dto.isCommentAdoptedState())
//                        .createdAt(dto.getCreatedAt())
//                        .updatedAt(dto.getUpdatedAt())
//                        .subsCode(dto.getSubsCode())
//                        .employeeCode(dto.getEmployeeCode())
//                        .build())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(commentVOs);
//    }




}
