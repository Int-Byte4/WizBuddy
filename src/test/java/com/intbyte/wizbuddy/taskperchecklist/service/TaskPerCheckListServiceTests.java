package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.mapper.TaskPerCheckListMapper;
import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.domain.TaskPerCheckListMybatis;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskPerCheckListServiceTests {

    @Autowired
    private TaskPerCheckListService taskPerCheckListService;
    @Autowired
    private TaskPerCheckListMapper taskPerCheckListMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("체크리스트에 업무추가 테스트 성공")
    public void insertTaskPerCheckListTest(){

//        UUID employeeCode = UUID.randomUUID(); -> 존재하지 않는 유저로 insert 하려고 해서 생긴 오류
        int size = employeeRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(size-1).getEmployeeCode();

        TaskPerCheckListDTO taskPerCheckListDTO = new TaskPerCheckListDTO(2, 5, true,
                LocalDateTime.now(), LocalDateTime.now(), employeeCode);

        taskPerCheckListService.insertTaskPerCheckList(taskPerCheckListDTO);
    }

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
    @DisplayName("특정 체크리스트의 완료된 업무만 조회")
    @Transactional
    public void findAllTaskPerCheckListFinished(){

        Integer size = employeeRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(size-1).getEmployeeCode();

        System.out.println("employeeCode = " + employeeCode);
        List<TaskPerCheckListDTO> finishedTask = taskPerCheckListService.findAllTaskPerCheckListFinished(employeeCode);
        System.out.println(finishedTask.size());
        for (int i = 0; i < finishedTask.size(); i++) {
            System.out.println(finishedTask.get(i));
            assertNotNull(finishedTask.get(i));
        }
    }

    @Test
    @DisplayName("체크리스트 업무 수정 테스트 성공")
    public void modifyTaskPerCheckListTest(){

        int taskCode = 1;
        int checkListCode = 1;

        int size = employeeRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(size-1).getEmployeeCode();

        // given
        taskPerCheckListService.insertTaskPerCheckList(
                new TaskPerCheckListDTO(checkListCode, taskCode, false,
                        LocalDateTime.now(), LocalDateTime.now(), employeeCode)
        );

        EditTaskPerCheckListInfo info = new EditTaskPerCheckListInfo(false, LocalDateTime.now(), employeeCode);

        // when
        taskPerCheckListService.modifyTaskPerCheckList(taskCode, checkListCode, info);

        // then
        TaskPerCheckListMybatis tpcMybatis = taskPerCheckListMapper.findTaskPerCheckListById(taskCode, checkListCode);
        assertEquals(false, tpcMybatis.getTaskFinishedState());
    }

}