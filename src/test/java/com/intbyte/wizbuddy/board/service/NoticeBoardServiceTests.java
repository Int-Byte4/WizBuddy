package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import jakarta.transaction.Transactional;
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
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
        int lastNum = noticeBoards.size();

        NoticeBoardDTO noticeBoardInfo = new NoticeBoardDTO(lastNum, "test","test", true, null, LocalDateTime.now(), LocalDateTime.now(), 1);

        noticeBoardService.registerNoticeBoard(noticeBoardInfo);

        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
        int newLastNum = newNoticeBoards.size();

        assertEquals(newLastNum, noticeBoardRepository.findAll().size());

        newNoticeBoards.forEach(System.out::println);
    }
}