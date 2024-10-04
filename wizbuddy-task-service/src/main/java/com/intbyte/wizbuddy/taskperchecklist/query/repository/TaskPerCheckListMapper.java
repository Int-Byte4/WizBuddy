package com.intbyte.wizbuddy.taskperchecklist.query.repository;

import com.intbyte.wizbuddy.taskperchecklist.query.dto.TaskPerCheckListQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskPerCheckListMapper {
    List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode);

    List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCodeByNonFinished(int checkListCode);

    List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCode(int checkListCode);
}
