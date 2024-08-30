package com.intbyte.wizbuddy.checklist.service;

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
}