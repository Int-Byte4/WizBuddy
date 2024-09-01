package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;

import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.mapper.SubsBoardMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubsBoardServiceTests {


    @Autowired
    private SubsBoardService subsBoardService;

    @Autowired
    private SubsBoardRepository subsBoardRepository;
    @Autowired
    private SubsBoardMapper subsBoardMapper;


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
        // given
        int subsCode = 1;

        // when
        SubsBoardDTO foundSubsBoard = subsBoardService.findSubsBoardById(subsCode);

        // then
        assertNotNull(foundSubsBoard);
        assertEquals(subsCode, foundSubsBoard.getSubsCode());

        System.out.println("조회된 게시판 제목: " + foundSubsBoard.getSubsTitle());
        System.out.println("조회된 게시판 내용: " + foundSubsBoard.getSubsContent());
    }


    @Test
    @Transactional
    @DisplayName("대타게시판_등록_테스트")
    public void insertSubsBoardTest() {
        // given
        List<SubsBoard> subsBoardList = subsBoardRepository.findAll();
        SubsBoardDTO newSubsBoard = new SubsBoardDTO(subsBoardList.size()+1,
                "추석 3일 풀알바 뛸사람 이리오셈티비~","급여가 짭쪼롬해요?",
                LocalDateTime.now(),LocalDateTime.now(),true,
                1,1);
        // when
        subsBoardService.registSubsBoard(newSubsBoard);
        // then
        List<SubsBoard> subsBoards = subsBoardRepository.findAll();
        SubsBoard subsBoard = subsBoards.get(subsBoards.size()-1);
        assertNotNull(subsBoard);
        assertEquals("추석 3일 풀알바 뛸사람 이리오셈티비~", subsBoard.getSubsTitle());
        assertEquals("급여가 짭쪼롬해요?", subsBoard.getSubsContent());
        assertEquals(1, subsBoard.getEmployeeWorkingPartCode());
        assertEquals(true, subsBoard.isSubsFlag());
        assertEquals(1, subsBoard.getShopCode());
    }


    @Test
    @Transactional
    @DisplayName("대타게시판_수정_테스트")
    void modifySubsBoardTest() {
        // given
        int subsCode = 1;
        SubsBoard subsBoard = subsBoardMapper.selectSubsBoardById(subsCode);
        EditSubsBoardInfo updateSubsBoard = new EditSubsBoardInfo(
                "9월 1일 내 생일 같이 보낼 직원분 구함", "제목이 곧 내용ㄱㄱ",
                LocalDateTime.now(), 2);
        // when
        subsBoardService.modifySubsBoards(subsBoard.getSubsCode(), updateSubsBoard);
        // then
        SubsBoard foundBoard = subsBoardRepository.findById(subsCode).orElse(null);
        assertEquals("9월 1일 내 생일 같이 보낼 직원분 구함", foundBoard.getSubsTitle());
        assertEquals("제목이 곧 내용ㄱㄱ", foundBoard.getSubsContent());
        assertNotNull(foundBoard.getCreatedAt());
        assertEquals(2, foundBoard.getEmployeeWorkingPartCode());
        assertEquals(1, foundBoard.getShopCode());
    }


    @Test
    @Transactional
    @DisplayName("대타게시판_삭제_테스트")
    void deleteSubsBoardTest() {
        // given
        int subsCode = 1;
        SubsBoardDTO subsBoard = subsBoardService.findSubsBoardById(subsCode);
        // when
        subsBoardService.deleteSubsBoard(subsBoard);
        // then
        SubsBoard updatedSubsBoard = subsBoardRepository.findById(subsCode).orElse(null);
        assertThat(updatedSubsBoard).isNotNull();
        assertThat(updatedSubsBoard.isSubsFlag()).isFalse();
    }

}