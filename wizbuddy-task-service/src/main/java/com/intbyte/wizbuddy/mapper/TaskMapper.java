package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.task.domain.TaskMybatis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMybatis findTaskById(int taskId);

    List<TaskMybatis> findAllTask();

    List<TaskMybatis> findAllTaskByFixedState();

    List<TaskMybatis> findAllTasksByTaskFlag();

    List<TaskMybatis> findAllTaskByShopCode(int shopCode);

    List<TaskMybatis> findAllTaskByShopCodeByFixedState(int shopCode);

    List<TaskMybatis> findAllTaskByShopCodeByNonFixedState(int shopCode);
}
