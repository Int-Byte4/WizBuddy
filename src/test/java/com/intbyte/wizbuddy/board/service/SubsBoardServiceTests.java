package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;

import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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


    @Test
    @Transactional
    public void 대타게시판_등록_테스트() {
        // given
        SubsBoardDTO newSubsBoard = new SubsBoardDTO();
        newSubsBoard.setSubsCode(3);
        newSubsBoard.setSubsTitle("새로운 게시판 제목");
        newSubsBoard.setSubsContent("게시판 내용");
        newSubsBoard.setCreatedAt(LocalDateTime.now());
        newSubsBoard.setUpdatedAt(LocalDateTime.now());
        newSubsBoard.setEmployeeWorkingPartCode(1);
        newSubsBoard.setSubsFlag(true);
        newSubsBoard.setShopCode(1);

        // when
        SubsBoard savedSubsBoard = subsBoardService.registSubsBoard(newSubsBoard);

        // then
        assertNotNull(savedSubsBoard, "게시판 등록이 되어야 합니다.");
        assertEquals("새로운 게시판 제목", savedSubsBoard.getSubsTitle());

        SubsBoard foundSubsBoard = subsBoardRepository.findById(3)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시판이 존재하지 않습니다."));

        assertEquals("새로운 게시판 제목", foundSubsBoard.getSubsTitle());
        assertEquals("게시판 내용", foundSubsBoard.getSubsContent());
        assertEquals(1, foundSubsBoard.getEmployeeWorkingPartCode());
        assertEquals(true, foundSubsBoard.isSubsFlag());
        assertEquals(1, foundSubsBoard.getShopCode());
    }



}