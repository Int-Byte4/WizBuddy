package com.intbyte.wizbuddy.task.query.repository;

import com.intbyte.wizbuddy.task.query.dto.TaskQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<TaskQueryDTO> findAllTask();

    TaskQueryDTO findTaskById(int taskCode);

    List<TaskQueryDTO> findAllTasksByTaskFlag();

    List<TaskQueryDTO> findAllTaskByFixedState();

    List<TaskQueryDTO> findAllTaskByShopCode(int shopCode);
    List<TaskQueryDTO> findAllTaskByShopCodeByFixedState(int shopCode);
    List<TaskQueryDTO> findAllTaskByShopCodeByNonFixedState(int shopCode);
}
