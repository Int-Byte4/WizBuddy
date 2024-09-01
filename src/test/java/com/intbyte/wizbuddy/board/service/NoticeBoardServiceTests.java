package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Test
    @DisplayName("공지사항 게시글 등록 성공")
    @Transactional
    public void testRegisterNoticeBoard() {
        // given
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
        int lastNum = noticeBoards.size();

        NoticeBoardDTO noticeBoardInfo = new NoticeBoardDTO(lastNum, "test","test", true, null, LocalDateTime.now(), LocalDateTime.now(), 1);

        // when
        noticeBoardService.registerNoticeBoard(noticeBoardInfo);

        // then
        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
        int newLastNum = newNoticeBoards.size();

        assertEquals(newLastNum, noticeBoardRepository.findAll().size());

        newNoticeBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("공지사항 게시물 수정 성공")
    @Transactional
    public void testModifyNoticeBoardSuccess() {
        // given
        int employerCode = 1;
        EditNoticeBoardInfo editNoticeBoardInfo = new EditNoticeBoardInfo(1, "제목 수정", "내용 수정", null, LocalDateTime.now(), 1, 1);

        // when
        noticeBoardService.modifyNoticeBoard(employerCode, editNoticeBoardInfo.getEmployerCode(), editNoticeBoardInfo);

        // then
        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
        assertEquals(newNoticeBoards.get(0).getNoticeTitle(), editNoticeBoardInfo.getNoticeTitle());

        newNoticeBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("공지사항 게시물 삭제 성공")
    @Transactional
    public void testDeleteNoticeBoardSuccess() {
        // given
        DeleteNoticeBoardInfo deleteNoticeBoardInfo = new DeleteNoticeBoardInfo(1, false, LocalDateTime.now());

        // when
        noticeBoardService.deleteNoticeBoard(1, 1, deleteNoticeBoardInfo);

        // then
        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
        Assertions.assertEquals(false, newNoticeBoards.get(0).equals(deleteNoticeBoardInfo));
        newNoticeBoards.forEach(System.out::println);
    }
}