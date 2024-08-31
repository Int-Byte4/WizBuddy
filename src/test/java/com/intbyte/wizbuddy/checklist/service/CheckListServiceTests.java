package com.intbyte.wizbuddy.checklist.service;

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
    @DisplayName("체크리스트 등록 성공")
    public void insertCheckListTest(){
        // given:
        int shopCode = 1;
        List<CheckList> currentCheckListList = checkListMapper.findAllCheckList();
        CheckListDTO checkListDTO = new CheckListDTO(currentCheckListList.size() + 1,
                "새로운 체크리스트 추가", true, LocalDateTime.now(), LocalDateTime.now(), shopCode);

        // when:
        checkListService.insertCheckList(checkListDTO);

        // then:
        CheckList checkList = checkListMapper.findCheckListById(checkListDTO.getCheckListCode());
        Assertions.assertEquals(checkListDTO.getCheckListCode(),checkList.getCheckListCode());
    }

    @Test
    @DisplayName("체크리스트 1개 조회 성공")
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
        List<CheckList> allCheckList = checkListMapper.findAllCheckList();
        for(CheckList checklist: allCheckList){
            assertNotNull(checklist);
        }
    }

    @Test
    @DisplayName("flag가 true인 체크리스트 전체 조회 성공")
    @Transactional
    public void findAllCheckListsByFlag(){
        List<CheckList> allCheckList = checkListMapper.findAllCheckListsByFlag();
        for(CheckList checklist: allCheckList){
            assertNotNull(checklist);
        }
    }

    @Test
    @DisplayName("체크리스트 수정 성공")
    @Transactional
    public void modifyCheckListTest(){

        // given
        int checkListCode = checkListMapper.findAllCheckList().size();
        EditCheckListInfo info = new EditCheckListInfo("수정된 체크리스트 내용"
                             , true, LocalDateTime.now());

        // when
        checkListService.modifyCheckList(checkListCode, info);

        // then
        CheckList checkList = checkListMapper.findCheckListById(checkListCode);
        assertEquals(info.getCheckListTitle(), checkList.getCheckListTitle());
    }

    @Test
    @DisplayName("체크리스트 업무 삭제 성공")
    @Transactional
    public void deleteCheckListTest(){

        // given
        int checkListCode = checkListMapper.findAllCheckList().size();

        // when
        checkListService.deleteCheckList(checkListCode);

        // then
        CheckList checkList = checkListMapper.findCheckListById(checkListCode);
        assertEquals(false, checkList.getCheckListFlag());
    }
}