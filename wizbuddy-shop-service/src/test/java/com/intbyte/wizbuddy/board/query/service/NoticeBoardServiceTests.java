package com.intbyte.wizbuddy.board.query.service;

import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Test
    @Transactional
    @DisplayName("공지사항 게시글 전체 조회")
    public void testFindAllNoticeBoards() {
        List<NoticeBoardDTO> noticeBoardList = noticeBoardService.findAllNoticeBoards();

        assertNotNull(noticeBoardList);
    }

    @Test
    @Transactional
    @DisplayName("매장별 공지사항 게시글 조회")
    public void testFindNoticeBoardByShopCode() {
        int shopCode = 1;
        List<NoticeBoardDTO> noticeBoardList = noticeBoardService.findNoticeBoardByShopCode(shopCode);

        noticeBoardList.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("공지사항 게시글 단 건 조회")
    public void testFindNoticeBoardByNoticeCode() {
        int noticeCode = 3;

        NoticeBoardDTO noticeBoard = noticeBoardService.findNoticeBoardByNoticeCode(noticeCode);

        assertNotNull(noticeBoard);
    }
}
