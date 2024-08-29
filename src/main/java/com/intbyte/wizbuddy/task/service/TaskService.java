package com.intbyte.wizbuddy.task.service;

import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository; // jpa
    private final TaskMapper mapper;        // mybatis
    private final ModelMapper modelMapper; // dto <-> entity 변환

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper mapper, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.modelMapper = modelMapper;
    }

    // task는 mapper 사용하기
    // checklist는 mapper 사용 x로 진행.

    // 1. 업무 등록을 해야함. -> 사용자에게서 TaskDTO가 넘어올거임. -> 리턴도 DTO로 해야함. -> 등록은 void?
    // void로 하지말고 dto에 등록된 업무를 보내주는게 맞을듯? dto 리턴이 맞는거같다.
    @Transactional
    public TaskDTO insertTask(TaskDTO taskDTO) {
        Task insertTask = new Task(taskDTO.getTaskCode(), taskDTO.getTaskContents(), taskDTO.isTaskFlag()
                            , taskDTO.isTaskFixedState(), taskDTO.getCreatedAt(), taskDTO.getUpdatedAt());

        //        modelMapper.map(taskDTO, insertTask);
        //        Task insertTask1 = modelMapper.map(taskDTO, Task.class); //ModelMapper는 필드명이 같다면 자동으로 매핑해주며, 복잡한 객체 매핑도 가능합니다.

        taskRepository.save(insertTask);

        return modelMapper.map(insertTask, TaskDTO.class);
    }

    // 2. 업무 조회 (id로 task 조회, 모든 업무 조회)
    // 2-1. id로 1개의 task 조회
    @Transactional
    public TaskDTO findTaskById(int taskCode){ // by mybatis

        Task findTask = mapper.findTaskById(taskCode);
        return modelMapper.map(findTask, TaskDTO.class);
}

    // 2-2. 전체 task 조회
    @Transactional
    public List<TaskDTO> findAllTask(){

        List<Task> findTasks = mapper.findAllTask();

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    // 3. 업무 수정(id값에 해당하는 업무 수정)
    @Transactional
    public TaskDTO modifyTask(TaskDTO taskDTO){

        int taskCode = taskDTO.getTaskCode();
        Task task = taskRepository.findById(taskCode).orElseThrow(IllegalArgumentException::new);
        // task를 taskdto로 수정

        modelMapper.map(taskDTO, task); // task를 taskdto로 수정

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDTO.class);
    }

    // 4. 업무 삭제 (id값에 해당하는 task 삭제)
    @Transactional
    public TaskDTO deleteTask(TaskDTO taskDTO){

        int taskCode = taskDTO.getTaskCode();
        Task deleteTask = taskRepository.findById(taskCode).orElseThrow(IllegalArgumentException::new);

        // 1. taskDTO로 바꿔주기
        TaskDTO deletedTaskDTO = modelMapper.map(deleteTask, TaskDTO.class);
        deletedTaskDTO.setTaskFlag(false);

        // 2. task로 바꿔주기
        Task deleteTask2 = new Task(deletedTaskDTO.getTaskCode(), deletedTaskDTO.getTaskContents(), deletedTaskDTO.isTaskFlag(),
                deletedTaskDTO.isTaskFixedState(), deletedTaskDTO.getCreatedAt(), deletedTaskDTO.getUpdatedAt());

        taskRepository.save(deleteTask2);

        return deletedTaskDTO;
    }

    // 일단 반환값과 파라미터값을 어떻게 할지 안정한거같아서 모두 통일함. (return = TaskDTO / parameter = TaskDTO)
    // ------------
    // 5. 체크리스트
}
