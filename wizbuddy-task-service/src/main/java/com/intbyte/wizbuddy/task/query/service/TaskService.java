package com.intbyte.wizbuddy.task.query.service;

import com.intbyte.wizbuddy.task.query.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO findTaskById(int taskCode);

    List<TaskDTO> findAllTaskByShopCode(int shopCode);

    List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode);

    List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode);
}
