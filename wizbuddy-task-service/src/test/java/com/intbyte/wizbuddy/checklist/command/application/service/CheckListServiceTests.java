package com.intbyte.wizbuddy.checklist.command.application.service;


import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import com.intbyte.wizbuddy.common.exception.CommonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class CheckListServiceTests {

    @Autowired
    private AppCheckListService checkListService;

    // 특정 매장에 체크리스트 1개 등록시 해당 매장의 fixed task 자동 추가 테스트
    @Test
    @DisplayName("체크리스트 생성시 해당 매장의 fixed task 자동 추가 테스트")
    public void insertCheckListTest(){

        int shopCode = 1;

        CheckListDTO dto = new CheckListDTO();
        dto.setCheckListTitle("새로운 체크리스트가 등록되고 있어용");
        dto.setCheckListFlag(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setShopCode(shopCode);

        Assertions.assertDoesNotThrow(() -> {
            checkListService.insertCheckList(dto);
        });
    }

    // 특정 매장에 체크리스트 1개 등록
    @Test
    @DisplayName("체크리스트에 업무 등록 테스트")
    public void insertTaskToCheckListTest(){

        int checkListCode = 1;
        int taskCode = 1;

        Assertions.assertThrows(CommonException.class, () -> {
            checkListService.insertTaskToCheckList(checkListCode, taskCode);
        });
    }

    @Test
    @DisplayName("체크리스트 업무 삭제 테스트")
    public void deleteTaskFromCheckListTest(){
        int checkListCode = 1;
        int taskCode = 1;

        Assertions.assertDoesNotThrow(() -> {
            checkListService.deleteTaskFromCheckList(checkListCode, taskCode);
        });
    }

    @Test
    @DisplayName("체크리스트 자체 수정 테스트")
    public void modifyCheckListTest(){

        int checkListCode = 1;

        CheckListDTO checkListDTO = new CheckListDTO();
        checkListDTO.setCheckListTitle("수정된 체크리스트 제목");
        checkListDTO.setCheckListFlag(true);
        checkListDTO.setUpdatedAt(LocalDateTime.now());

        Assertions.assertDoesNotThrow(() -> {
            checkListService.modifyCheckList(checkListCode, checkListDTO);
        });
    }

    @Test
    @DisplayName("체크리스트 삭제 테스트")
    public void deleteCheckListTest() {

        int checkListCode = 1;

        Assertions.assertDoesNotThrow(() -> {
            checkListService.deleteCheckList(checkListCode);
        });
    }
}