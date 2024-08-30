package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;

import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubsBoardServiceTests {


    @Autowired
    private SubsBoardService subsBoardService;

    @Autowired
    private SubsBoardRepository subsBoardRepository;



    @Test
    public void 대타게시판_전체_조회_테스트() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<SubsBoardDTO> subsBoard = subsBoardService.findAllSubsBoards();
                    subsBoard.forEach(System.out::println);
                });

    }

    @Test
    public void 매장별_대타게시판_조회_테스트() {
        // given
        int subsCode = 1;

        // when
        SubsBoardDTO foundSubsBoard = subsBoardService.findSubsBoardById(subsCode);

        // then
        assertNotNull(foundSubsBoard, "해당 subsCode에 대한 대타게시판 항목을 찾을 수 없습니다.");
        assertEquals(subsCode, foundSubsBoard.getSubsCode());

        System.out.println("조회된 게시판 제목: " + foundSubsBoard.getSubsTitle());
        System.out.println("조회된 게시판 내용: " + foundSubsBoard.getSubsContent());
    }


}