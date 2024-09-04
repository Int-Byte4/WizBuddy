package com.intbyte.wizbuddy.like;

import com.intbyte.wizbuddy.board.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.board.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardLikedRepository;
import com.intbyte.wizbuddy.board.service.NoticeBoardLikedService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardLikedServiceTests {

    @Autowired
    private NoticeBoardLikedService noticeBoardLikedService;

    @Autowired
    private NoticeBoardLikedRepository noticeBoardLikedRepository;

    @Test
    @DisplayName("공지사항 게시글 좋아요 추가 성공")
    @Transactional
    public void testRegisterNoticeBoardLike() {

        List<NoticeBoardLiked> noticeBoardLiked = noticeBoardLikedRepository.findAll();
        int lastNum = noticeBoardLiked.size();

        NoticeBoardLikedDTO noticeBoardLikedInfo = new NoticeBoardLikedDTO(lastNum, LocalDateTime.now(), 1, 1, "20240831-07de-4b18-95c6-564cd86a5af2");

        noticeBoardLikedService.registerNoticeBoardLike(noticeBoardLikedInfo);

        List<NoticeBoardLiked> newNoticeBoardLiked = noticeBoardLikedRepository.findAll();
        int newLastNum = newNoticeBoardLiked.size();

        assertEquals(newLastNum, noticeBoardLikedRepository.findAll().size());

        newNoticeBoardLiked.forEach(System.out::println);
    }

}