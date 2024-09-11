package com.intbyte.wizbuddy.board.query.controller;

import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.query.service.NoticeBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("noticeBoardQueryController")
@RequestMapping("/noticeBoard")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @Autowired
    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @Operation(summary = "공지사항 게시판 전체 조회")
    @GetMapping
    public ResponseEntity<List<NoticeBoardDTO>> getAllNoticeBoards() {

        List<NoticeBoardDTO> response = noticeBoardService.findAllNoticeBoards();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "매장별 공지사항 게시판 조회")
    @GetMapping("/shop/{shopCode}")
    public ResponseEntity<List<NoticeBoardDTO>> getNoticeBoardByShopCode(@PathVariable("shopCode") int shopCode) {

        List<NoticeBoardDTO> response = noticeBoardService.findNoticeBoardByShopCode(shopCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "공지사항 게시판 단 건 조회")
    @GetMapping("/{noticeCode}")
    public ResponseEntity<NoticeBoardDTO> findNoticeBoardByNoticeCode(@PathVariable("noticeCode") int noticeCode) {
        NoticeBoardDTO noticeBoardDTO = noticeBoardService.findNoticeBoardByNoticeCode(noticeCode);

        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDTO);
    }
}
