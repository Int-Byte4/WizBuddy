package com.intbyte.wizbuddy.board.controller;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.service.NoticeBoardService;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateNoticeBoardVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/noticeboard")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    // 1. 공지사항 게시판 전체 조회
    @GetMapping
    public ResponseEntity<List<ResponseFindNoticeBoardVO>> getAllNoticeBoards() {
        List<NoticeBoardDTO> noticeBoardDTOs = noticeBoardService.findAllNoticeBoards();

        List<ResponseFindNoticeBoardVO> noticeBoardVOs = noticeBoardDTOs.stream()
                .map(dto -> ResponseFindNoticeBoardVO.builder()
                        .noticeCode(dto.getNoticeCode())
                        .noticeTitle(dto.getNoticeTitle())
                        .noticeContent(dto.getNoticeContent())
                        .noticeFlag(dto.isNoticeFlag())
                        .imageUrl(dto.getImageUrl())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .shopCode(dto.getShopCode())
                        .employerCode(dto.getEmployerCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(noticeBoardVOs);
    }

    // 2. 매장별 공지사항 게시글 조회
    @GetMapping("/shop/{shopCode}")
    public ResponseEntity<List<ResponseFindNoticeBoardVO>> getNoticeBoardByShopCode(@PathVariable("shopCode") int shopCode) {
        List<NoticeBoardDTO> noticeBoardDTOs = noticeBoardService.findNoticeBoardByShopCode(shopCode);

        List<ResponseFindNoticeBoardVO> noticeBoardVOs = noticeBoardDTOs.stream()
                .map(dto -> ResponseFindNoticeBoardVO.builder()
                        .noticeCode(dto.getNoticeCode())
                        .noticeTitle(dto.getNoticeTitle())
                        .noticeContent(dto.getNoticeContent())
                        .noticeFlag(dto.isNoticeFlag())
                        .imageUrl(dto.getImageUrl())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .shopCode(dto.getShopCode())
                        .employerCode(dto.getEmployerCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(noticeBoardVOs);
    }

    // 3. 공지사항 게시글 단 건 조회
    @GetMapping("/{noticecode}")
    public ResponseEntity<ResponseFindNoticeBoardVO> findNoticeBoardByNoticeCode(@PathVariable("noticecode") int noticeCode) {
        NoticeBoardDTO noticeBoardDTO = noticeBoardService.findNoticeBoardByNoticeCode(noticeCode);

        ResponseFindNoticeBoardVO noticeBoardVO = ResponseFindNoticeBoardVO.builder()
                .noticeCode(noticeBoardDTO.getNoticeCode())
                .noticeTitle(noticeBoardDTO.getNoticeTitle())
                .noticeContent(noticeBoardDTO.getNoticeContent())
                .noticeFlag(noticeBoardDTO.isNoticeFlag())
                .imageUrl(noticeBoardDTO.getImageUrl())
                .createdAt(noticeBoardDTO.getCreatedAt())
                .updatedAt(noticeBoardDTO.getUpdatedAt())
                .shopCode(noticeBoardDTO.getShopCode())
                .employerCode(noticeBoardDTO.getEmployerCode())
                .build();

        return ResponseEntity.ok(noticeBoardVO);
    }
    // 4. 새로운 공지사항 게시글 등록
    @PostMapping
    public ResponseEntity<ResponseInsertNoticeBoardVO> insertNoticeBoard (
            @RequestBody RequestInsertNoticeBoardVO requestInsertNoticeBoardVO) {

        ResponseInsertNoticeBoardVO response = noticeBoardService.registerNoticeBoard(requestInsertNoticeBoardVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 5. 공지사항 게시글 수정, 삭제
    @PatchMapping("update/{noticecode}")
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
