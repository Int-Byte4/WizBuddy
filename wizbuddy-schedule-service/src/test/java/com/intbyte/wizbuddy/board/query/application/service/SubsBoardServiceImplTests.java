package com.intbyte.wizbuddy.board.query.application.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubsBoardServiceImplTests {

    @Autowired
    private SubsBoardService subsBoardService;

    @Test
    @DisplayName("대타게시판_전체_조회_테스트")
    public void findAllSubsBoardTest() {

        Assertions.assertDoesNotThrow(
                () -> {
                    List<SubsBoardDTO> subsBoard = subsBoardService.findAllSubsBoards();
                    subsBoard.forEach(System.out::println);
                });

    }

    @Test
    @DisplayName("대타게시판_1개_조회_테스트")
    public void findSubsBoardByIdTest() {

        int subsCode = 1;
        SubsBoardDTO foundSubsBoard = subsBoardService.findSubsBoardById(subsCode);
        assertNotNull(foundSubsBoard);
        assertEquals(subsCode, foundSubsBoard.getSubsCode());
        System.out.println("조회된 게시판 제목: " + foundSubsBoard.getSubsTitle());
        System.out.println("조회된 게시판 내용: " + foundSubsBoard.getSubsContent());

    }

    @Test
    @DisplayName("매장별_조회_테스트")
    public void findSubsBoardByShopCodeTest() {

        int shopCode = 1;
        List<SubsBoardDTO> subsBoards = subsBoardService.getSubsBoardsByShopCode(shopCode);
        assertNotNull(subsBoards);
        assertTrue(!subsBoards.isEmpty());

    }


}