package com.intbyte.wizbuddy.like.service;

import com.intbyte.wizbuddy.like.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.like.service.ManualBoardLikedService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManualBoardLikedServiceTests {

    @Autowired
    private ManualBoardLikedService manualBoardLikedService;

    @Autowired
    private ManualBoardLikedRepository manualBoardLikedRepository;

    @Test
    @DisplayName("매뉴얼 게시글 좋아요 추가 성공")
    @Transactional
    public void testRegisterManualBoardLike() {

        List<ManualBoardLiked> manualBoardLiked = manualBoardLikedRepository.findAll();
        int lastNum = manualBoardLiked.size();

        ManualBoardLikedDTO manualBoardLikedInfo = new ManualBoardLikedDTO(lastNum, LocalDateTime.now(), 1, 1, "20240831-187e-452d-88b4-62b7469c1cfa");

        manualBoardLikedService.registerManualBoardLike(manualBoardLikedInfo);

        List<ManualBoardLiked> newManualBoardLiked = manualBoardLikedRepository.findAll();
        int newLastNum = newManualBoardLiked.size();

        assertEquals(newLastNum, manualBoardLikedRepository.findAll().size());

        newManualBoardLiked.forEach(System.out::println);

    }

}