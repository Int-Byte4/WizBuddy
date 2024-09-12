package com.intbyte.wizbuddy.taskperchecklist.query.service;

import com.intbyte.wizbuddy.taskperchecklist.query.dto.TaskPerCheckListQueryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskPerCheckListTests {

    @Autowired
    private TaskPerCheckListService taskPerCheckListService;

    // 1-1.
    @Test
    @DisplayName("특정 체크리스트속 업무 조회")
    public void findAllTaskPerCheckListByCheckListCodeTest(){

        int checkListCode = 1;
        List<TaskPerCheckListQueryDTO> tpcDTOs = taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(checkListCode);

        for (int i = 0; i < tpcDTOs.size(); i++)
            assertEquals(checkListCode, tpcDTOs.get(i).getCheckListCode());
    }

    // 1-2.
    @Test
    @DisplayName("특정 체크리스트속 완료된 업무 조회")
    public void findAllTaskPerCheckListByCheckListCodeFinishedTest(){

        int checkListCode = 1;
        List<TaskPerCheckListQueryDTO> finishedDTOs = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);

        for (int i = 0; i < finishedDTOs.size(); i++)
            assertEquals(true, finishedDTOs.get(i).getTaskFinishedState());
    }

    // 1-3.
    @Test
    @DisplayName("특정 체크리스트속 미완료된 업무 조회")
    public void findAllTaskPerCheckListByCheckListCodeNotFinishedTest() {

        int checkListCode = 1;
        List<TaskPerCheckListQueryDTO> nonFinishedDTOs = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByNotFinished(checkListCode);

        for (int i = 0; i < nonFinishedDTOs.size(); i++)
            assertEquals(false, nonFinishedDTOs.get(i).getTaskFinishedState());
    }
}
