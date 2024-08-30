package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskPerCheckListServiceTests {

    @Autowired
    private TaskPerCheckListService taskPerCheckListService;

    @Test
    @DisplayName("체크리스트에 업무추가 테스트 성공")
    public void insertTaskPerCheckListTest(){
        TaskPerCheckListDTO taskPerCheckListDTO = new TaskPerCheckListDTO(2, 5, true,
                LocalDateTime.now(), LocalDateTime.now(), 14);

        taskPerCheckListService.insertTaskPerCheckList(taskPerCheckListDTO);


    }
}