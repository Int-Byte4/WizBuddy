package com.intbyte.wizbuddy.task.command.application.service;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class TaskServiceTests {

    @Autowired
    private AppTaskService taskService;


//    @Test
//    @Transactional
//    public void 전체_업무_조회_테스트(){
//        List<TaskMybatis> allTask = taskMapper.findAllTask();
//        for (int i = 0; i < allTask.size(); i++) {
//            assertNotNull(allTask);
//            System.out.println(allTask.get(i));
//        }
//    }
//
//
//    @Test
//    @Transactional
//    public void 고정된_전체_업무_조회_테스트(){
//        List<TaskMybatis> allTask = taskMapper.findAllTaskByFixedState();
//        for (int i = 0; i < allTask.size(); i++) {
//            assertNotNull(allTask);
//            System.out.println(allTask.get(i));
//        }
//    }
//
//
//    @Test
//    @Transactional
//    public void 삭제되지않은_전체_업무_조회_테스트(){
//        List<TaskMybatis> allTask = taskMapper.findAllTasksByTaskFlag();
//        for (int i = 0; i < allTask.size(); i++) {
//            assertNotNull(allTask);
//            System.out.println(allTask.get(i));
//        }
//    }
//
//    @Test
//    @Transactional // 1번
//    public void id로_업무_1개_조회_테스트(){
//
//        // given
//        int taskCode = taskMapper.findAllTask().size() - 1;
//
//        // when
//        TaskQueryDTO task = taskService.findTaskById(taskCode);
//
//        // then
//        assertNotNull(task);
//    }
//
//    // 2-1번
//    @Test
//    @Transactional
//    public void 매장_id로_모든_업무_조회(){
//
//        // given
//        int shopCode = 1;
//
//        // when
//        List<TaskQueryDTO> allTaskByShopCode = taskService.findAllTaskByShopCode(shopCode);
//
//        // then
//        for (int i = 0; i < allTaskByShopCode.size(); i++) {
//            System.out.println(allTaskByShopCode.get(i));
//            assertNotNull(allTaskByShopCode.get(i));
//        }
//    }
//
//    // 2-2번
//    @Test
//    @Transactional
//    public void 매장_id로_고정된_모든_업무_조회(){
//        // given
//        int shopCode = 1;
//
//        // when
//        List<TaskQueryDTO> allTaskByShopCode = taskService.findAllTaskByShopCodeByFixedState(shopCode);
//
//        // then
//        for (int i = 0; i < allTaskByShopCode.size(); i++) {
//            System.out.println(allTaskByShopCode.get(i));
//            assertNotNull(allTaskByShopCode.get(i));
//        }
//    }
//
//    // 2-3번
//    @Test
//    @Transactional
//    public void 매장_id로_고정_안된_모든_업무_조회(){
//        // given
//        int shopCode = 2;
//
//        // when
//        List<TaskQueryDTO> allTaskByShopCode = taskService.findAllTaskByShopCodeByNonFixedState(shopCode);
//
//        // then
//        for (int i = 0; i < allTaskByShopCode.size(); i++) {
//            System.out.println(allTaskByShopCode.get(i));
//            assertNotNull(allTaskByShopCode.get(i));
//        }
//    }


    // 3. 특정 매장에 1개 task 추가
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

        taskService.insertTask(dto);
    }

    // 4. 특정 매장의 특정 task 수정
    @Test
    public void id로_업무_1개_수정_테스트(){

        int taskCode = 1;

        TaskDTO dto = new TaskDTO();
        dto.setTaskContents("task 수정 내용");
        dto.setTaskFlag(true);
        dto.setTaskFixedState(true);
        dto.setUpdatedAt(LocalDateTime.now());

        taskService.modifyTask(taskCode, dto);
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

        taskService.deleteTask(taskCode, dto);
    }
}