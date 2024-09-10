package com.intbyte.wizbuddy.board.query.controller;

import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.query.service.NoticeBoardService;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindNoticeBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController("noticeBoardQueryService")
@RequestMapping("/noticeboard")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    @Operation(summary = "공지사항 게시판 전체 조회")
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

    @GetMapping("/shop/{shopCode}")
    @Operation(summary = "매장별 공지사항 게시판 조회")
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

    @GetMapping("/{noticecode}")
    @Operation(summary = "공지사항 게시판 단 건 조회")
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
}
