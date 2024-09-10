package com.intbyte.wizbuddy.like.command.application.controller;

import com.intbyte.wizbuddy.like.command.application.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.command.application.service.ManualBoardLikedService;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.request.RequestInsertManualBoardLikeVO;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.response.ResponseInsertManualBoardLikeVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manualboards")
@RequiredArgsConstructor
public class ManualBoardLikeController {

    private final ManualBoardLikedService manualBoardLikedService;
    private final ModelMapper modelMapper;

    @PostMapping("/like")
    public ResponseEntity<ResponseInsertManualBoardLikeVO> createSubsBoard(@RequestBody RequestInsertManualBoardLikeVO request) {
        ManualBoardLikedDTO manualBoardLikedEntity = modelMapper.map(request, ManualBoardLikedDTO.class);
        ResponseInsertManualBoardLikeVO responseBoard = manualBoardLikedService.registerManualBoardLike(manualBoardLikedEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }

}
