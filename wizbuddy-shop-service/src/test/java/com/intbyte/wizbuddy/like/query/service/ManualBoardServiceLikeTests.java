package com.intbyte.wizbuddy.like.query.service;

import com.intbyte.wizbuddy.like.query.dto.ManualBoardLikedDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ManualBoardServiceLikeTests {

    @Autowired
    private ManualBoardLikedService manualBoardLikedService;

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시글 좋아요 조회")
    public void testFindManualBoardLike() {
        int manualCode = 1;

        List<ManualBoardLikedDTO> manualBoardLikedList = manualBoardLikedService.findLikesByManualCode(manualCode);

        manualBoardLikedList.forEach(System.out::println);
    }

}
