package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @DisplayName("매뉴얼 게시글 수정 성공")
    @Transactional
    public void testModifyManualBoardSuccess() {
        // given
        int userCode = 1;
        EditManualBoardInfo editManualBoardInfo = new EditManualBoardInfo(1, "제목 수정", "내용 수정", null, LocalDateTime.now(), 1);

        // when
        manualBoardService.modifyManualBoard(userCode, editManualBoardInfo.getUserCode(), editManualBoardInfo);

        // then
        List<ManualBoard> newManualBoards = manualBoardRepository.findAll();
        assertEquals(newManualBoards.get(0).getManualTitle(), editManualBoardInfo.getManualTitle());

        newManualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 삭제 성공")
    @Transactional
    public void testDeleteManualBoardSuccess() {
        // given
        DeleteManualBoardInfo deleteManualBoardInfo = new DeleteManualBoardInfo(1, false, LocalDateTime.now(), 1);

        // when
        manualBoardService.deleteManualBoard(1, 1, deleteManualBoardInfo);

        // then
        List<ManualBoard> newManualBoards = manualBoardRepository.findAll();
        Assertions.assertEquals(false, newManualBoards.get(0).equals(deleteManualBoardInfo));
        newManualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 전체 조회")
    @Transactional
    public void testFindAllManualBoards() {

        // given, when
        int shopCode = 1;

        // then
        List<ManualBoardDTO> manualBoards = manualBoardService.findAllManualBoards(shopCode);

        manualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 단 건 조회")
    @Transactional
    public void testFindManualBoard() {

        // given, when
        int manualCode = 3;

        // then
        ManualBoardDTO manualBoard = manualBoardService.findManualBoard(manualCode);

        System.out.println(manualBoard);
    }
}