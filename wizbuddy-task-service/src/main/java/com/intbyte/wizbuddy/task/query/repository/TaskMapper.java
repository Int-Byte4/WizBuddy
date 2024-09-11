package com.intbyte.wizbuddy.task.query.repository;

import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskDTO findTaskByTaskCode(int taskCode);

    List<TaskDTO> findAllTaskByShopCode(int shopCode);

    List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode);

    List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode);
}
