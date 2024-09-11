package com.intbyte.wizbuddy.taskperchecklist.command.application.service;

import com.intbyte.wizbuddy.taskperchecklist.command.application.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.query.service.TaskPerCheckListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class TaskPerCheckListTests {

    @Autowired
    private AppTaskPerCheckListService taskPerCheckListService;

    // 4. 특정 매장(특정 체크리스트)의 특정 업무 finished 테스트
    @Test
    @DisplayName("특정 매장(특정 체크리스트)의 특정 업무 finished 테스트")
    public void modifyTaskPerCheckList(){

        int checkListCode = 1;
        int taskCode = 2;

        TaskPerCheckListDTO dto = new TaskPerCheckListDTO();
        dto.setCheckListCode(checkListCode);
        dto.setTaskCode(taskCode);
        dto.setTaskFinishedState(false);
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setEmployeeCode("20240831-1859-4c43-b692-b6cb5891c24a");

        taskPerCheckListService.modifyTaskPerCheckList(dto);
    }
}
