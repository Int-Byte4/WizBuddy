package com.intbyte.wizbuddy.board.query.service;

import com.intbyte.wizbuddy.board.query.dto.ManualBoardDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ManualBoardServiceTests {

    @Autowired
    private ManualBoardService manualBoardService;

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시판 전체 게시글 조회")
    public void testFindAllManualBoards() {
        List<ManualBoardDTO> manualBoardList = manualBoardService.findAllManualBoards();

        assertNotNull(manualBoardList);
    }

    @Test
    @Transactional
    @DisplayName("매장별 매뉴얼 게시글 조회")
    public void testFindManualBoardByShopCode() {
        int shopCode = 1;
        List<ManualBoardDTO> manualBoardList = manualBoardService.findManualBoardByShopCode(shopCode);

        manualBoardList.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시글 단 건 조회")
    public void testFindManualBoard() {
        int manualCode = 3;

        ManualBoardDTO manualBoard = manualBoardService.findManualBoardByManualCode(manualCode);

        assertNotNull(manualBoard);
    }
}
