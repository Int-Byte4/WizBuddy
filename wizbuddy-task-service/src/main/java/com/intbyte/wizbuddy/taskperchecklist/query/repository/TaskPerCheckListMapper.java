package com.intbyte.wizbuddy.taskperchecklist.query.repository;

import com.intbyte.wizbuddy.taskperchecklist.query.dto.TaskPerCheckListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskPerCheckListMapper {
    List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode);

    List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByNonFinished(int checkListCode);

    List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCode(int checkListCode);
}
