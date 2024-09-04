package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckListId;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
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
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("체크리스트에 업무추가 테스트 성공")
    public void insertTaskPerCheckListTest(){

        int size = employeeRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(size-1).getEmployeeCode();

        TaskPerCheckListDTO taskPerCheckListDTO = new TaskPerCheckListDTO(1, 5, true,
                LocalDateTime.now(), LocalDateTime.now(), employeeCode);

        taskPerCheckListService.insertTaskPerCheckList(taskPerCheckListDTO);
    }

    @Test
    @DisplayName("체크리스트의 업무 조회 테스트 성공")
    public void findTaskPerCheckListByIdTest(){

        // given
        TaskPerCheckListId taskPerChecklistId = new TaskPerCheckListId(1, 1);

        // when
        TaskPerCheckListDTO taskPerCheckListDTO =
                taskPerCheckListService.findTaskPerCheckListById(taskPerChecklistId);

        // then
        assertNotNull(taskPerCheckListDTO);
    }

    @Test
    @DisplayName("체크리스트별 업무 전체 조회 테스트 성공")
    public void findAllTaskPerCheckListTest() {
        List<TaskPerCheckListDTO> allTaskPerCheckList = taskPerCheckListService.findAllTaskPerCheckList();

        for (int i = 0; i < allTaskPerCheckList.size(); i++) assertNotNull(allTaskPerCheckList.get(i));
    }

    @Test
    @DisplayName("특정 체크리스트의 완료된 업무만 조회")
    @Transactional
    public void findAllTaskPerCheckListFinished(){

        Integer size = employeeRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(size-1).getEmployeeCode();

        List<TaskPerCheckListDTO> finishedTask = taskPerCheckListService.findAllTaskPerCheckListFinished(employeeCode);

        for (int i = 0; i < finishedTask.size(); i++) assertNotNull(finishedTask.get(i));
    }

    // 1-1.
    @Test
    @DisplayName("특정 매장의 특정 체크리스트속 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeTest(){
        assertDoesNotThrow(() -> {
            taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(1);
        });
    }

    // 1-2.
    @Test
    @DisplayName("특정 매장의 특정 체크리스트속 완료된 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeFinishedTest(){
        assertDoesNotThrow(() -> {
            taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(1);
        });
    }

    // 1-3.
    @Test
    @DisplayName("특정 매장의 특정 체크리스트속 완료된 업무 조회")
    @Transactional
    public void findAllTaskPerCheckListByCheckListCodeNotFinishedTest() {
        assertDoesNotThrow(() -> {
            taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByNotFinished(1);
        });
    }

    // 2. 특정 체크리스트에 특정 업무 추가
    @Test
    @DisplayName("특정 매장의 특정 체크리스트에 특정 업무 추가")
    public void insertTaskPerCheckListByCheckList(){
        TaskPerCheckListDTO dto = new TaskPerCheckListDTO();
        dto.setCheckListCode(1);
        dto.setTaskCode(1);
        dto.setTaskFinishedState(true);
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setEmployeeCode("20240831-07de-4b18-95c6-564cd86a5af2");

        assertDoesNotThrow(() -> {
            taskPerCheckListService.insertTaskPerCheckList(dto);
        });
    }

    // 3. 특정 매장의 특정 체크리스트의 특정 업무 삭제
    @Test
    @DisplayName("특정 매장의 특정 체크리스트의 특정 업무 삭제")
    public void deleteTaskPerCheckListByCheckListCodeAndTaskCode() {
        assertDoesNotThrow(() -> {
            taskPerCheckListService.deleteTaskPerCheckListByCheckListCodeAndTaskCode(7, 9);
        });
    }

    // 4. 특정 매장(특정 체크리스트)의 특정 업무 finished 테스트
    @Test
    @DisplayName("특정 매장(특정 체크리스트)의 특정 업무 finished 테스트")
    public void modifyTaskPerCheckList(){
        EditTaskPerCheckListInfo info = new EditTaskPerCheckListInfo();
        info.setCheckListCode(1);
        info.setTaskCode(1);
        info.setTaskFinishedState(false);
        info.setUpdatedAt(LocalDateTime.now());
        info.setEmployeeCode("20240831-1859-4c43-b692-b6cb5891c24a");

        assertDoesNotThrow(() -> {
            taskPerCheckListService.modifyTaskPerCheckList(info);
        });
    }

}