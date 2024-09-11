package com.intbyte.wizbuddy.checklist.command.application.service;


import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CheckListServiceTests {

    @Autowired
    private AppCheckListService checkListService;


    @Test
    @DisplayName("체크리스트 수정 성공")
    public void modifyCheckListTest(){

        int checkListCode = 1;

        CheckListDTO checkListDTO = new CheckListDTO();
        checkListDTO.setCheckListTitle("수정된 체크리스트 제목");
        checkListDTO.setCheckListFlag(true);
        checkListDTO.setUpdatedAt(LocalDateTime.now());

        checkListService.modifyCheckList(checkListCode,checkListDTO);
    }

    @Test
    @DisplayName("체크리스트 삭제 성공")
    public void deleteCheckListTest(){

        int checkListCode = 1;
        checkListService.deleteCheckList(checkListCode);
    }



    // 3-1.특정 매장에 체크리스트 1개 등록
    @Test
    @DisplayName("특정 매장에 체크리스트 1개 등록 성공")
    public void insertCheckListTest(){
        // given:
        int checkListCode = 1;
        int shopCode = 1;

        checkListService.insertTaskToCheckList(checkListCode, shopCode);
    }

    // 3-2. 특정 매장에 체크리스트 1개 등록시 해당 매장의 fixed task 자동 추가 테스트
    @Test
    @DisplayName("특정 매장에 체크리스트 생성시 해당 매장의 fixed task 자동 추가 테스트")
    public void insertCheckListFixedStateTask(){

        int shopCode = 1;

        CheckListDTO dto = new CheckListDTO();
        dto.setCheckListTitle("3번 체크리스트에는 2번 매장");
        dto.setCheckListFlag(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setShopCode(shopCode);

        checkListService.insertCheckList(dto);
    }
}