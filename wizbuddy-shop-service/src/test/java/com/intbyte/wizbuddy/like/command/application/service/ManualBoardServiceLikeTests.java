package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.command.domain.repository.ManualBoardLikedRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ManualBoardServiceLikeTests {

    @Autowired
    private ManualBoardLikedService manualBoardLikedService;

    @Autowired
    private ManualBoardLikedRepository manualBoardLikedRepository;

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시글 좋아요 등록 성공")
    public void testRegisterManualBoardLike() {
        int manualCode = 1;
        String employeeCode = "20240831-6d8e-4b0a-907e-f0e56b179f88";

        List<ManualBoardLiked> manualBoardLiked = manualBoardLikedRepository.findLikesByManualCode(manualCode);
        int initialCount = manualBoardLiked.size();

        manualBoardLikedService.registerManualBoardLike(manualCode, employeeCode);

        List<ManualBoardLiked> newManualBoardLiked = manualBoardLikedRepository.findLikesByManualCode(manualCode);
        int newCount = newManualBoardLiked.size();

        assertEquals(initialCount + 1, newCount);
    }
}
