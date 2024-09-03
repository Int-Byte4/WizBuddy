package com.intbyte.wizbuddy.task.service;

import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
import com.intbyte.wizbuddy.task.domain.TaskMybatis;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTests {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;


    // 3번
    @Test
    public void 업무_1개_추가_테스트() {
        // given:
        List<TaskMybatis> currentTaskList = taskMapper.findAllTask();
        TaskDTO taskDTO = new TaskDTO(currentTaskList.size() + 1, "새로운 업무 추가"
                    , true, true, LocalDateTime.now(), LocalDateTime.now(), 2);

        // when:
        taskService.insertTask(taskDTO);

        // then:
        List<TaskMybatis> currentTaskList2 = taskMapper.findAllTask();

        TaskMybatis findTask = taskMapper.findTaskById(taskDTO.getTaskCode());
        Assertions.assertEquals(taskDTO.getTaskCode(), findTask.getTaskCode());
    }

    @Test
    @Transactional // 1번
    public void id로_업무_1개_조회_테스트(){

        // given
        int taskCode = taskMapper.findAllTask().size() - 1;

        // when
        TaskDTO task = taskService.findTaskById(taskCode);

        // then
        assertNotNull(task);
    }

    @Test
    @Transactional
    public void 전체_업무_조회_테스트(){
        List<TaskMybatis> allTask = taskMapper.findAllTask();
        for (int i = 0; i < allTask.size(); i++) {
            assertNotNull(allTask);
            System.out.println(allTask.get(i));
        }
    }


    @Test
    @Transactional
    public void 고정된_전체_업무_조회_테스트(){
        List<TaskMybatis> allTask = taskMapper.findAllTaskByFixedState();
        for (int i = 0; i < allTask.size(); i++) {
            assertNotNull(allTask);
            System.out.println(allTask.get(i));
        }
    }


    @Test
    @Transactional
    public void 삭제되지않은_전체_업무_조회_테스트(){
        List<TaskMybatis> allTask = taskMapper.findAllTasksByTaskFlag();
        for (int i = 0; i < allTask.size(); i++) {
            assertNotNull(allTask);
            System.out.println(allTask.get(i));
        }
    }


    @Test
    public void id로_업무_1개_수정_테스트(){

        // given:
        int taskCode = taskMapper.findAllTask().size();
        EditTaskInfo editTaskInfo = new EditTaskInfo("수정된 내용", true, true,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        // when:
        taskService.modifyTask(taskCode, editTaskInfo);

        // then:
        TaskMybatis findTask = taskMapper.findTaskById(taskCode);
        Assertions.assertEquals(findTask.getTaskContents(), editTaskInfo.getTaskContents());
    }

    // 2-1번
    @Test
    @Transactional
    public void 매장_id로_모든_업무_조회(){

        // given
        int shopCode = 1;

        // when
        List<TaskDTO> allTaskByShopCode = taskService.findAllTaskByShopCode(shopCode);

        // then
        for (int i = 0; i < allTaskByShopCode.size(); i++) {
            System.out.println(allTaskByShopCode.get(i));
            assertNotNull(allTaskByShopCode.get(i));
        }
    }

    // 2-2번
    @Test
    @Transactional
    public void 매장_id로_고정된_모든_업무_조회(){
        // given
        int shopCode = 1;

        // when
        List<TaskDTO> allTaskByShopCode = taskService.findAllTaskByShopCodeByFixedState(shopCode);

        // then
        for (int i = 0; i < allTaskByShopCode.size(); i++) {
            System.out.println(allTaskByShopCode.get(i));
            assertNotNull(allTaskByShopCode.get(i));
        }
    }

    // 2-3번
    @Test
    @Transactional
    public void 매장_id로_고정_안된_모든_업무_조회(){
        // given
        int shopCode = 2;

        // when
        List<TaskDTO> allTaskByShopCode = taskService.findAllTaskByShopCodeByNonFixedState(shopCode);

        // then
        for (int i = 0; i < allTaskByShopCode.size(); i++) {
            System.out.println(allTaskByShopCode.get(i));
            assertNotNull(allTaskByShopCode.get(i));
        }
    }

    @Test
    @DisplayName("task에서 task flag 제외한 나머지 수정 테스트")
    public void modifyTaskTest(){
        taskService.modifyTask(1, new EditTaskInfo("new contents222", true, false, LocalDateTime.now()));
    }

    @Test
    @DisplayName("task flag false로 변환시 taskCode가 동일한 모든 taskPerCheckList 삭제")
    public void deleteTaskAndTaskPerCheckListTest(){
        taskService.deleteTaskPerCheckList(10);
    }
}