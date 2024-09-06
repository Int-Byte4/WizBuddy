package com.intbyte.wizbuddy.like.controller;

import com.intbyte.wizbuddy.like.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.service.NoticeBoardLikedService;
import com.intbyte.wizbuddy.like.vo.request.RequestInsertNoticeBoardLikeVO;
import com.intbyte.wizbuddy.like.vo.response.ResponseInsertNoticeBoardLikeVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noticeboards")
@RequiredArgsConstructor
public class NoticeBoardLikeController {

    private final NoticeBoardLikedService noticeBoardLikedService;
    private final ModelMapper modelMapper;


    @PostMapping("/like")
    @Operation(summary = "공지사항 게시글 좋아요 등록")
    public ResponseEntity<ResponseInsertNoticeBoardLikeVO> insertNoticeboardLike(@RequestBody RequestInsertNoticeBoardLikeVO request) {
        NoticeBoardLikedDTO noticeBoardLikedEntity = modelMapper.map(request, NoticeBoardLikedDTO.class);
        ResponseInsertNoticeBoardLikeVO responseBoard = noticeBoardLikedService.registerNoticeBoardLike(noticeBoardLikedEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }
}
