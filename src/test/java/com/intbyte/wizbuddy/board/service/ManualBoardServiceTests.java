package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManualBoardServiceTests {

    @Autowired
    private ManualBoardService manualBoardService;

    @Autowired
    private ManualBoardRepository manualBoardRepository;

    @Test
    @DisplayName("매뉴얼 게시글 등록 성공")
    @Transactional
    public void testRegisterManualBoard() {
        List<ManualBoard> manualBoards = manualBoardRepository.findAll();
        int lastNum = manualBoards.size();

        ManualBoardDTO manualBoardInfo = new ManualBoardDTO(lastNum, "공지사항", "본사 공지사항 내용", true, null, LocalDateTime.now(), LocalDateTime.now(), 1, 1);

        manualBoardService.registerManualBoard(manualBoardInfo);

        List<ManualBoard> newManualBoards = manualBoardRepository.findAll();
        int newLastNum = newManualBoards.size();

        assertEquals(newLastNum, manualBoardRepository.findAll().size());

        newManualBoards.forEach(System.out::println);
    }
}