package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.taskperchecklist.domain.TaskPerCheckListMybatis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskPerCheckListMapper {
    TaskPerCheckListMybatis findTaskPerCheckListById(@Param("taskCode") Integer taskCode, @Param("checkListCode") Integer checkListCode);

    List<TaskPerCheckListMybatis> findAllTaskPerCheckList();

    List<TaskPerCheckListMybatis> findAllTaskPerCheckListFinished(String employeeCode);

    List<TaskPerCheckListMybatis> findAllTaskPerCheckListByCheckListCode(int checkListCode);

    List<TaskPerCheckListMybatis> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode);
}
