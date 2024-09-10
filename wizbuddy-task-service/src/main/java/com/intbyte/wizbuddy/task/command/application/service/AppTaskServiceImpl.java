package com.intbyte.wizbuddy.task.command.application.service;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;
import com.intbyte.wizbuddy.task.command.domain.aggregate.entity.Task;
import com.intbyte.wizbuddy.task.command.domain.repository.TaskRepository;
import com.intbyte.wizbuddy.task.command.infrastructure.service.InfraTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AppTaskServiceImpl implements AppTaskService {

    private final TaskRepository taskRepository;
    private final InfraTaskService infraTaskService;

    @Autowired
    public AppTaskServiceImpl(TaskRepository taskRepository, InfraTaskService infraTaskService) {
        this.taskRepository = taskRepository;
        this.infraTaskService = infraTaskService;
    }

    // command 1. task 최초 추가
    @Override
    @Transactional
    public void insertTask(TaskDTO taskInfo) {

        Task task = Task.builder()
                .taskContents(taskInfo.getTaskContents())
                .taskFlag(true)
                .taskFixedState(taskInfo.isTaskFixedState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(taskInfo.getShopCode())
                .build();

        taskRepository.save(task);
    }

    // command 2. task 자체 수정
    @Override
    @Transactional
    public void modifyTask(int taskCode, TaskDTO taskDTO){

        Task task = taskRepository.findById(taskCode).get();//.orElseThrow(TaskNotFoundException::new);

        task.modify(taskDTO);

        taskRepository.save(task);
    }

    // command 3. task 삭제 -> task를 삭제한 순간 infra를 호출하고 infra에서 tpcs에서 taskcode와 관련된 모든거 없애기
    @Override
    @Transactional
    public void deleteTask(int taskCode, TaskDTO taskDTO){

        Task task = taskRepository.findById(taskCode).get();
        task.modify(taskDTO);

        try{
            // 1. task flag가 false로 된거 저장하기
            taskRepository.save(task);

            // 2. tpcs에서 없애야 하므로 없애기 infra 호출하기
            infraTaskService.deleteTaskPerCheckListByTaskCode(taskCode);
        }catch (Exception e){
            e.printStackTrace(); // 추후 수정 필요
        }
    }
}
