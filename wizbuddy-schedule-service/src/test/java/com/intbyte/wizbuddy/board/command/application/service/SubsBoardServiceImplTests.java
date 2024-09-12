package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.command.domain.aggregate.vo.EditSubsBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.SubsBoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubsBoardServiceImplTests {

    @Autowired
    private SubsBoardService subsBoardService;

    @Autowired
    private SubsBoardRepository subsBoardRepository;


    @Test
    @Transactional
    @DisplayName("대타게시판_등록_테스트")
    public void insertSubsBoardTest() {

        List<SubsBoard> subsBoardList = subsBoardRepository.findAll();
        SubsBoardDTO newSubsBoard = new SubsBoardDTO(subsBoardList.size()+1,
                "추석 3일 풀알바 뛸사람 이리오셈티비~","급여가 짭쪼롬해요?",
                LocalDateTime.now(),LocalDateTime.now(),true,
                1,1);
        subsBoardService.registSubsBoard(newSubsBoard);
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

        int subsCode = 1;
        EditSubsBoardVO updateSubsBoard = new EditSubsBoardVO(
                "9월 1일 내 생일 같이 보낼 직원분 구함", "제목이 곧 내용ㄱㄱ",
                LocalDateTime.now(), 2);
        subsBoardService.modifySubsBoards(subsCode, updateSubsBoard);
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

        int subsCode = 2;
        SubsBoardDTO modifySubsBoard = new SubsBoardDTO(subsCode,
                "9월 5일 대타 구함 ㅜㅜ","내일은 한가한날 이지만 대청소 도와줄사람",
                LocalDateTime.now(),LocalDateTime.now(),true,
                2,1);
        subsBoardService.deleteSubsBoard(modifySubsBoard);
        SubsBoard updatedSubsBoard = subsBoardRepository.findById(subsCode).orElse(null);
        assertThat(updatedSubsBoard).isNotNull();
        assertThat(updatedSubsBoard.isSubsFlag()).isFalse();

    }

}