package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.mapper.TaskPerCheckListMapper;
import com.intbyte.wizbuddy.task.service.TaskService;
import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.domain.TaskPerCheckListMybatis;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskPerCheckListServiceTests {

    @Autowired
    private TaskPerCheckListService taskPerCheckListService;
    @Autowired
    private TaskPerCheckListMapper taskPerCheckListMapper;

    @Test
    @DisplayName("체크리스트에 업무추가 테스트 성공")
    public void insertTaskPerCheckListTest(){
        TaskPerCheckListDTO taskPerCheckListDTO = new TaskPerCheckListDTO(2, 5, true,
                LocalDateTime.now(), LocalDateTime.now(), 14);

        taskPerCheckListService.insertTaskPerCheckList(taskPerCheckListDTO);
    }
//
    @Test
    @DisplayName("체크리스트의 업무 조회 테스트 성공")
    public void findTaskPerCheckListByIdTest(){

        // given
        TaskPerChecklistId taskPerChecklistId = new TaskPerChecklistId(1, 1);

        // when
        TaskPerCheckListDTO taskPerCheckListDTO =
                taskPerCheckListService.findTaskPerCheckListById(taskPerChecklistId);
        System.out.println("taskPerCheckListDTO = " + taskPerCheckListDTO);

        // then
        assertNotNull(taskPerCheckListDTO);
    }

    @Test
    @DisplayName("체크리스트의 업무 전체 조회 테스트 성공")
    public void findAllTaskPerCheckListTest() {
        List<TaskPerCheckListDTO> allTaskPerCheckList = taskPerCheckListService.findAllTaskPerCheckList();
        for (int i = 0; i < allTaskPerCheckList.size(); i++) {
            System.out.println(allTaskPerCheckList.get(i));
        }
    }

    @Test
    @DisplayName("체크리스트 업무 수정 테스트 성공")
    public void modifyTaskPerCheckListTest(){

        // given
        int taskCode = 1;
        int checkListCode = 1;
        EditTaskPerCheckListInfo info = new EditTaskPerCheckListInfo(false, LocalDateTime.now(), 5);

        // when
        taskPerCheckListService.modifyTaskPerCheckList(taskCode, checkListCode, info);

        // then
        TaskPerCheckListMybatis tpcMybatis = taskPerCheckListMapper.findTaskPerCheckListById(taskCode, checkListCode);
        assertEquals(false, tpcMybatis.getTaskFinishedState());
    }

}