package com.intbyte.wizbuddy.like.command.application.controller;

import com.intbyte.wizbuddy.like.command.application.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.command.application.service.NoticeBoardLikedService;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.request.RequestInsertNoticeBoardLikeVO;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.response.ResponseInsertNoticeBoardLikeVO;
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
    public ResponseEntity<ResponseInsertNoticeBoardLikeVO> insertNoticeboardLike(@RequestBody RequestInsertNoticeBoardLikeVO request) {
        NoticeBoardLikedDTO noticeBoardLikedEntity = modelMapper.map(request, NoticeBoardLikedDTO.class);
        ResponseInsertNoticeBoardLikeVO responseBoard = noticeBoardLikedService.registerNoticeBoardLike(noticeBoardLikedEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }
}
