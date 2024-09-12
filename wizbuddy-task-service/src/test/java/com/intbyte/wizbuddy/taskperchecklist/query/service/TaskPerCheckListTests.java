package com.intbyte.wizbuddy.taskperchecklist.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TaskPerCheckListTests {

    @Autowired
    private TaskPerCheckListService taskPerCheckListService;

    // 1-1.
    @Test
    @DisplayName("특정 체크리스트속 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeTest(){

        int checkListCode = 1;

        taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(checkListCode);
    }

    // 1-2.
    @Test
    @DisplayName("특정 체크리스트속 완료된 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeFinishedTest(){

        int checkListCode = 1;

        taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);
    }

    // 1-3.
    @Test
    @DisplayName("특정 체크리스트속 미완료된 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeNotFinishedTest() {

        int checkListCode = 1;

        taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByNotFinished(checkListCode);
    }
}
