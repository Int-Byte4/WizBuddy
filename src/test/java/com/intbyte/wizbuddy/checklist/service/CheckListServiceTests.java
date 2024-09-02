package com.intbyte.wizbuddy.checklist.service;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckListServiceTests {

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private CheckListMapper checkListMapper;

    @Test
    @DisplayName("체크리스트 등록 성공") // 3번 - 1
    public void insertCheckListTest(){
        // given:
        int shopCode = 1;
        List<CheckListMybatis> currentCheckListList = checkListMapper.findAllCheckList();
        CheckListDTO checkListDTO = new CheckListDTO(currentCheckListList.size() + 1,
                "새로운 체크리스트 추가", true, LocalDateTime.now(), LocalDateTime.now(), shopCode);

        // when:
        checkListService.insertCheckList(checkListDTO);

        // then:
        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListDTO.getCheckListCode());
        Assertions.assertEquals(checkListDTO.getCheckListCode(),checkList.getCheckListCode());
    }

    @Test
    @DisplayName("체크리스트 1개 조회 성공") // 1번
    public void findCheckListByIdTest(){

        // given
        int checkListCode = checkListMapper.findAllCheckList().size();
        System.out.println("checkListCode = " + checkListCode);

        // when
        CheckListDTO checkList = checkListService.findCheckListById(checkListCode);

        // then
        Assertions.assertNotNull(checkList);
    }

    @Test
    @DisplayName("체크리스트 전체 조회 성공")
    @Transactional
    public void findAllCheckListTest(){
        List<CheckListMybatis> allCheckList = checkListMapper.findAllCheckList();
        for(CheckListMybatis checklist: allCheckList){
            assertNotNull(checklist);
        }
    }

//    @Test
//    @DisplayName("flag가 true인 체크리스트 전체 조회 성공")
//    @Transactional
//    public void findAllCheckListsByFlag(){
//        List<CheckListMybatis> allCheckList = checkListMapper.findAllCheckListsByFlag();
//        for(CheckListMybatis checklist: allCheckList){
//            assertNotNull(checklist);
//        }
//    }

    @Test
    @DisplayName("체크리스트 수정 성공")
    public void modifyCheckListTest(){

        // given
        int checkListCode = checkListMapper.findAllCheckList().size();
        EditCheckListInfo info = new EditCheckListInfo("수정된 체크리스트 내용"
                             , true, LocalDateTime.now());

        // when
        checkListService.modifyCheckList(checkListCode, info);

        // then
        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListCode);
        assertEquals(info.getCheckListTitle(), checkList.getCheckListTitle());
    }

    @Test
    @DisplayName("체크리스트 업무 삭제 성공")
    public void deleteCheckListTest(){

        // given
        int checkListCode = checkListMapper.findAllCheckList().size();

        // when
        checkListService.deleteCheckList(checkListCode);

        // then
        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListCode);
        assertEquals(false, checkList.getCheckListFlag());
    }


    @Test
    @DisplayName("CheckList 생성시 fixed task 추가되는지 테스트") // 3-2
    public void insertCheckListFixedStateTask(){

        CheckListDTO dto = new CheckListDTO();
        dto.setCheckListTitle("4번 체크리스트에는 2번 매장");
        dto.setShopCode(2);

        checkListService.insertCheckList(dto);
    }

    @Test
    @DisplayName("CheckList 수정 테스트") // 4번
    public void modifyCheckList(){
        checkListService.modifyCheckList(1, new EditCheckListInfo("새로운 checkList 제목", true, LocalDateTime.now()));
    }


    @Test
    @DisplayName("CheckList 삭제시 같은 chedkListCode를 가진 TaskPerCheckList 삭제 테스트") // 5번
    public void deleteCheckListAndTaskPerCheckListTest(){
        checkListService.deleteTaskPerCheckList(7);
    }
}