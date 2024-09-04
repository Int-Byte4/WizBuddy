package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.exception.manualboard.ManualBoardNotFoundException;
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

        RequestInsertManualBoardVO manualBoardInfo = new RequestInsertManualBoardVO("공지사항", "본사 공지사항 내용", true, null, LocalDateTime.now(), LocalDateTime.now(), 1, "20240831-3750-4218-9aed-7eabc7c634c2");

        manualBoardService.registerManualBoard(manualBoardInfo);

        List<ManualBoard> newManualBoards = manualBoardRepository.findAll();
        int newLastNum = newManualBoards.size();

        assertEquals(newLastNum, manualBoardRepository.findAll().size());

        newManualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 수정 성공")
    @Transactional
    public void testModifyManualBoard() {
        String userCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        EditManualBoardInfo editManualBoardInfo = new EditManualBoardInfo(1, "제목 수정", "내용 수정", null, true, LocalDateTime.now(), userCode);

        manualBoardService.modifyManualBoard(1, editManualBoardInfo);

        System.out.println(userCode);
        System.out.println(editManualBoardInfo.getUserCode());

        List<ManualBoard> newManualBoards = manualBoardRepository.findAll();
        assertEquals(newManualBoards.get(0).getManualTitle(), editManualBoardInfo.getManualTitle());

        newManualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 삭제 성공")
    @Transactional
    public void testDeleteManualBoard() {
        String userCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        DeleteManualBoardInfo deleteManualBoardInfo = new DeleteManualBoardInfo(false, LocalDateTime.now(),userCode);

        manualBoardService.deleteManualBoard(1, deleteManualBoardInfo);

        ManualBoard manualBoard = manualBoardRepository.findById(1).orElseThrow(ManualBoardNotFoundException::new);

        assertFalse(manualBoard.isManualFlag());
    }

    @Test
    @DisplayName("매뉴얼 게시판 전체 게시글 조회")
    public void testFindAllManualBoards() {
        List<ManualBoardDTO> manualBoards = manualBoardService.findAllManualBoards();

        manualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매장별 매뉴얼 게시글 조회")
    @Transactional
    public void testFindManualBoardByShopCode() {

        int shopCode = 1;

        List<ManualBoardDTO> manualBoards = manualBoardService.findManualBoardByShopCode(shopCode);

        manualBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매뉴얼 게시글 단 건 조회")
    @Transactional
    public void testFindManualBoard() {

        int manualCode = 3;

        ManualBoardDTO manualBoard = manualBoardService.findManualBoardByManualCode(manualCode);

        System.out.println(manualBoard);
    }
}