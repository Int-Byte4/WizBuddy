package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.command.domain.repository.NoticeBoardLikedRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoticeBoardServiceLikeTests {

    @Autowired
    private NoticeBoardLikedService noticeBoardLikedService;

    @Autowired
    private NoticeBoardLikedRepository noticeBoardLikedRepository;

    @Test
    @Transactional
    @DisplayName("공지사항 게시글 좋아요 등록 성공")
    public void testRegisterNoticeBoardLike() {
        int noticeCode = 1;
        String employeeCode = "20240831-6d8e-4b0a-907e-f0e56b179f88";

        List<NoticeBoardLiked> noticeBoardLiked = noticeBoardLikedRepository.findLikesByNoticeCode(noticeCode);
        int initialCount = noticeBoardLiked.size();

        noticeBoardLikedService.registerNoticeBoardLike(noticeCode, employeeCode);

        List<NoticeBoardLiked> newNoticeBoardLiked = noticeBoardLikedRepository.findLikesByNoticeCode(noticeCode);
        int newCount = newNoticeBoardLiked.size();

        assertEquals(initialCount + 1, newCount);
    }
}