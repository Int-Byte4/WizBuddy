package com.intbyte.wizbuddy.like.query.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ManualBoardServiceLikeTests {

    @Autowired
    private ManualBoardLikedService manualBoardLikedService;

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시글 좋아요 조회")
    public void testFindManualBoardLike() {
        int manualCode = 1;

        List<Map<String, Object>> manualBoardLikedList = manualBoardLikedService.findLikesByManualCode(manualCode);

        int result = (int)(manualBoardLikedList.stream().count());

        assertThat(result).isEqualTo(1);
    }

}
