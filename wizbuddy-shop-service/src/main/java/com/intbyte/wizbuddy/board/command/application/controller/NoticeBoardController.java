package com.intbyte.wizbuddy.board.command.application.controller;

import com.intbyte.wizbuddy.board.command.application.service.NoticeBoardService;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateNoticeBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController("noticeBoardCommandController")
@RequestMapping("/noticeboard")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @PostMapping("/register")
    @Operation(summary = "공지사항 게시글 등록")
    public ResponseEntity<ResponseInsertNoticeBoardVO> insertNoticeBoard (
            @RequestBody RequestInsertNoticeBoardVO requestInsertNoticeBoardVO) {

        ResponseInsertNoticeBoardVO response = noticeBoardService.registerNoticeBoard(requestInsertNoticeBoardVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("update/{noticecode}")
    @Operation(summary = "공지사항 게시글 수정 및 삭제")
    public ResponseEntity<ResponseUpdateNoticeBoardVO> updateNoticeBoard(
            @PathVariable("noticecode") int noticeCode,
            @RequestBody RequestUpdateNoticeBoardVO requestUpdateNoticeBoardVO) {

        if(requestUpdateNoticeBoardVO.isNoticeFlag()) {

            EditNoticeBoardInfo editNoticeBoardInfo = new EditNoticeBoardInfo(
                    noticeCode,
                    requestUpdateNoticeBoardVO.getNoticeTitle(),
                    requestUpdateNoticeBoardVO.getNoticeContent(),
                    requestUpdateNoticeBoardVO.getImageUrl(),
                    requestUpdateNoticeBoardVO.isNoticeFlag(),
                    LocalDateTime.now(),
                    requestUpdateNoticeBoardVO.getEmployerCode()
            );

            ResponseUpdateNoticeBoardVO response = noticeBoardService.modifyNoticeBoard(noticeCode, editNoticeBoardInfo);

            return ResponseEntity.ok(response);

        } else {
            DeleteNoticeBoardInfo deleteNoticeBoardInfo = new DeleteNoticeBoardInfo(
                    requestUpdateNoticeBoardVO.isNoticeFlag(),
                    LocalDateTime.now(),
                    requestUpdateNoticeBoardVO.getEmployerCode()
            );
            noticeBoardService.deleteNoticeBoard(noticeCode, deleteNoticeBoardInfo);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}