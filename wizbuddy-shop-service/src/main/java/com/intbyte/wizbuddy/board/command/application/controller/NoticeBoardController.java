package com.intbyte.wizbuddy.board.command.application.controller;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditNoticeBoardDTO;
import com.intbyte.wizbuddy.board.command.application.service.NoticeBoardService;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseUpdateNoticeBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("noticeBoardCommandController")
@RequestMapping("/noticeboard")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @PostMapping("/register")
    @Operation(summary = "공지사항 게시글 등록")
    public ResponseEntity<ResponseInsertNoticeBoardVO> insertNoticeBoard (@RequestBody RequestInsertNoticeBoardVO requestInsertNoticeBoardVO) {
        ResponseInsertNoticeBoardVO response = noticeBoardService.registerNoticeBoard(requestInsertNoticeBoardVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("update/{noticeCode}")
    @Operation(summary = "공지사항 게시글 수정")
    public ResponseEntity<ResponseUpdateNoticeBoardVO> updateNoticeBoard(@PathVariable("noticeCode") int noticeCode, @RequestBody RequestEditNoticeBoardDTO requestEditNoticeBoardDTO) {
        ResponseUpdateNoticeBoardVO response = noticeBoardService.modifyNoticeBoard(noticeCode, requestEditNoticeBoardDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("delete/{noticeCode}")
    @Operation(summary = "공지사항 게시글 삭제")
    public ResponseEntity<ResponseUpdateNoticeBoardVO> deleteNoticeBoard(@PathVariable("noticeCode") int noticeCode, @RequestParam String employeeCode) {
        noticeBoardService.deleteNoticeBoard(noticeCode, employeeCode);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}