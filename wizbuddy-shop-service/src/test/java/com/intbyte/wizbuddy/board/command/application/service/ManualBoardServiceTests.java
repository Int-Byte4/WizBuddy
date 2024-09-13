package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditManualBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.ManualBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ManualBoardServiceTests {

    @Autowired
    private ManualBoardService manualBoardCommandService;

    @Autowired
    private ManualBoardRepository manualBoardRepository;

    @Test
    @Transactional
    @DisplayName("매뉴얼 게시글 등록 성공")
    public void testRegisterManualBoard() {
        int initialSize = manualBoardRepository.findAll().size();

        RequestInsertManualBoardVO requestVO = new RequestInsertManualBoardVO(
            "음료 만드는 방법",
            "음료는 잘 만들면 됩니다",
            true,
            null,
            LocalDateTime.now(),
            LocalDateTime.now(),
            1,
            "20240831-3750-4218-9aed-7eabc7c634c2"
        );

        manualBoardCommandService.registerManualBoard(requestVO);
        int newCount = manualBoardRepository.findAll().size();

        assertEquals(initialSize + 1, newCount);
    }

    @Test
    @DisplayName("매뉴얼 게시글 수정 성공")
    @Transactional
    public void testModifyManualBoard() {
        RequestEditManualBoardDTO requestDTO = new RequestEditManualBoardDTO("마늘빵 만드는 법", "다른 빵집에서 사오세요", null, LocalDateTime.now(), "20240831-3750-4218-9aed-7eabc7c634c2");

        manualBoardCommandService.modifyManualBoard(1, requestDTO);

        assertEquals(requestDTO.getManualTitle(), manualBoardRepository.findAll().get(0).getManualTitle());
    }

    @Test
    @DisplayName("매뉴얼 게시글 삭제 성공")
    @Transactional
    public void testDeleteManualBoard() {
        int manualCode = 1;
        String userCode = "20240831-3750-4218-9aed-7eabc7c634c2";

        manualBoardCommandService.deleteManualBoard(manualCode, userCode);

        ManualBoard deleteManualBoard = manualBoardRepository.findById(manualCode).get();

        assertEquals(false, deleteManualBoard.isManualFlag());

    }
}
