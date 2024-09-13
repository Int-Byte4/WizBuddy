package com.intbyte.wizbuddy.task.command.application.service;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class TaskServiceTests {

    @Autowired
    private AppTaskService taskService;

    // 특정 매장에 1개 task 추가
    @Test
    public void 업무_1개_추가_테스트() {

        int shopCode = 1;

        TaskDTO dto = new TaskDTO();
        dto.setTaskContents("task 추가 내용");
        dto.setTaskFlag(true);
        dto.setTaskFixedState(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setShopCode(shopCode);

        Assertions.assertDoesNotThrow(() -> {
            taskService.insertTask(dto);
        });
    }

    // 특정 매장의 특정 task 수정
    @Test
    public void id로_업무_1개_수정_테스트() {

        int taskCode = 1;

        TaskDTO dto = new TaskDTO();
        dto.setTaskContents("task 수정 내용");
        dto.setTaskFlag(true);
        dto.setTaskFixedState(true);
        dto.setUpdatedAt(LocalDateTime.now());

        Assertions.assertDoesNotThrow(() -> {
            taskService.modifyTask(taskCode, dto);
        });
    }


    @Test
    @DisplayName("task flag false로 변환시 taskCode가 동일한 모든 taskPerCheckList 삭제")
    public void deleteTaskAndTaskPerCheckListTest() {

        int taskCode = 1;

        TaskDTO dto = new TaskDTO();
        dto.setTaskContents("task 수정 내용");
        dto.setTaskFlag(true);
        dto.setTaskFixedState(true);
        dto.setUpdatedAt(LocalDateTime.now());

        Assertions.assertDoesNotThrow(() -> {
            taskService.deleteTask(taskCode, dto);
        });
    }
}