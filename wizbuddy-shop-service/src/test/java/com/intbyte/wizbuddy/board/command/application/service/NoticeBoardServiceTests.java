package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditNoticeBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.NoticeBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;
    @Autowired
    private NoticeBoardService noticeBoardCommandService;

    @Test
    @Transactional
    @DisplayName("공지사항 게시물 등록 성공")
    public void testRegisterNoticeBoard() {
        int initialSize = noticeBoardRepository.findAll().size();

        RequestInsertNoticeBoardVO requestVO = new RequestInsertNoticeBoardVO(
                "매장 폐업합니다",
                "ㅈㄱㄴ",
                true,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1,
                "20240831-3750-4218-9aed-7eabc7c634c2"
        );

        noticeBoardCommandService.registerNoticeBoard(requestVO);
        int newCount = noticeBoardRepository.findAll().size();

        assertEquals(initialSize + 1, newCount);
    }

    @Test
    @Transactional
    @DisplayName("공지사항 게시글 수정 성공")
    public void testModifyNoticeBoard() {
        RequestEditNoticeBoardDTO requestDTO = new RequestEditNoticeBoardDTO("알바생들에게 알립니다.", "사실 딱히 공지사항은 없습니다.", null, LocalDateTime.now(), "20240831-3750-4218-9aed-7eabc7c634c2");

        noticeBoardCommandService.modifyNoticeBoard(1, requestDTO);

        assertEquals(requestDTO.getNoticeTitle(), noticeBoardRepository.findAll().get(0).getNoticeTitle());
    }

    @Test
    @Transactional
    @DisplayName("공지사항 게시물 삭제 성공")
    public void testDeleteNoticeBoard() {
        int noticeCode = 1;
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";

        noticeBoardCommandService.deleteNoticeBoard(noticeCode, employerCode);

        NoticeBoard deleteNoticeBoard = noticeBoardRepository.findById(noticeCode).get();

        assertEquals(false, deleteNoticeBoard.isNoticeFlag());

    }
}
