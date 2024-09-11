package com.intbyte.wizbuddy.task.query.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import com.intbyte.wizbuddy.task.query.repository.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    // query 1. 특정 task 조회
    @Override
    @Transactional
    public TaskDTO findTaskById(int taskCode){
        TaskDTO taskDTO = taskMapper.findTaskById(taskCode);
        if(taskDTO == null) throw new CommonException(StatusEnum.TASK_NOT_FOUND);

        return taskDTO;
    }

    // query 2. 특정 shop의 모든 task 조회
    @Override
    @Transactional
    public List<TaskDTO> findAllTaskByShopCode(int shopCode){
        return taskMapper.findAllTaskByShopCode(shopCode);
    }

    // query 3. 특정 shop의 fixed인 모든 task 조회
    @Override
    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode){
        return taskMapper.findAllTaskByShopCodeByFixedState(shopCode);
    }

    // query 4. 특정 shop의 non fixed인 모든 task 조회
    @Override
    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode){
        return taskMapper.findAllTaskByShopCodeByNonFixedState(shopCode);
    }

}
