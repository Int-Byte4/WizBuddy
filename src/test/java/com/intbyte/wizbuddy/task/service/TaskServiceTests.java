package com.intbyte.wizbuddy.task.service;

import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
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


    // 1. 업무 1개 insert
    @Test
//    @Transactional // insert 후 select하는 경우 각 메서드가 transactional이라 commit이 되지 않아 찾을수가 없어서 뺐음.
    // insert 테스트의 정의를 다르게 하면 (select를 하지 않는다면) @Transactional을 적어도 됨.
    public void 업무_1개_추가_테스트() {
        // given: 테스트 데이터 생성
        TaskDTO newTaskDTO = new TaskDTO(12, "newTask", true, true,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        // when: 서비스 메서드 호출
        taskService.insertTask(newTaskDTO);
        TaskDTO findTaskDTOById = taskService.findTaskById(12);

        // then: 동등한지 비교
        assertEquals(newTaskDTO, findTaskDTOById);
    }

    // 2-1. id로 업무 1개 조회
    @Test
    @Transactional
    public void id로_업무_1개_조회_테스트(){

        // given
        int taskId = 1;
        Task taskById = taskMapper.findTaskById(taskId);
        System.out.println(taskById);
        // when

        // then
    }

    // 2-2. 전체 업무 조회
    @Test
    @Transactional
    public void 전체_업무_조회_테스트(){
        List<Task> allTask = taskMapper.findAllTask();
        for (int i = 0; i < allTask.size(); i++) {
            System.out.println(allTask.get(i));
        }
    }

    // 3.업무 수정
    @Test
    public void id로_업무_1개_수정_테스트(){

        // given: 수정할 Task 생성하기
        Task modifyTask = new Task(1, "수정된 내용", true, true,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        // when: 수정된 Task 가져오기, db에서 id로 task 가져오기
        Task savedTask = taskRepository.save(modifyTask);
        Task findTaskById = taskMapper.findTaskById(modifyTask.getTaskCode());

        // then: 2개의 task contents가 동일한지 확인
        Assertions.assertEquals(modifyTask.getTaskContents(), findTaskById.getTaskContents());
    }

    // 4. 업무 삭제
    @Test
    public void id_로_업무_1개_삭제_테스트(){

        // given: 삭제하고싶은 task_code 생성
        int deleteTaskId = 12;
        TaskDTO findTaskDTOById = taskService.findTaskById(deleteTaskId);

        // when: task_code가 12인 게시글 삭제(taskFlag가 false)
        taskService.deleteTask(findTaskDTOById);
        TaskDTO afterModifyTaskDTO = taskService.findTaskById(deleteTaskId);

        // then: task_code가 12인 게시글의 state가 false인지 확인
        assertEquals(false, afterModifyTaskDTO.isTaskFlag());
    }
}