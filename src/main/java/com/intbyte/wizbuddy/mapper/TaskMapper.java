package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.task.domain.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    Task findTaskById(int taskId);

    List<Task> findAllTask();

    List<Task> findAllTaskByFixedState();

    List<Task> findAllTasksByTaskFlag();
}
