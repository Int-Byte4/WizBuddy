package com.intbyte.wizbuddy.board.service;


import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;

import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

}