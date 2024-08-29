package com.intbyte.wizbuddy.task.service;

import com.intbyte.wizbuddy.exception.task.TaskNotFoundException;
import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository; // jpa
    private final TaskMapper taskMapper;        // mybatis
    private final ModelMapper modelMapper; // dto <-> entity 변환

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper mapper, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = mapper;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void insertTask(TaskDTO taskInfo) {

        Task task = Task.builder()
                .taskContents(taskInfo.getTaskContents())
                .taskFlag(true)
                .taskFixedState(taskInfo.isTaskFixedState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        taskRepository.save(task);
    }

    @Transactional
    public TaskDTO findTaskById(int taskCode){

        Task findTask = taskMapper.findTaskById(taskCode);
        return modelMapper.map(findTask, TaskDTO.class);
    }

    @Transactional
    public List<TaskDTO> findAllTask(){

        List<Task> findTasks = taskMapper.findAllTask();

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void modifyTask(int taskCode, EditTaskInfo modifyTaskInfo){

        Task task = taskRepository.findById(taskCode).orElseThrow(TaskNotFoundException::new);

        task.modify(modifyTaskInfo);
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(int taskCode){

        Task task = taskMapper.findTaskById(taskCode);
        EditTaskInfo editTaskInfo = new EditTaskInfo(task.getTaskContents(), false, task.isTaskFixedState(), task.getUpdatedAt());

        task.modify(editTaskInfo);

        taskRepository.save(task);
    }
}
