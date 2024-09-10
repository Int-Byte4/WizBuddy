package com.intbyte.wizbuddy.task.query.service;

import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {
    // query 1. 특정 task 조회
    TaskDTO findTaskById(int taskCode);

    // query 2. 특정 shop의 모든 task 조회
    List<TaskDTO> findAllTaskByShopCode(int shopCode);

    // query 3. 특정 shop의 fixed인 모든 task 조회
    List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode);

    // query 4. 특정 shop의 non fixed인 모든 task 조회
    List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode);
}
