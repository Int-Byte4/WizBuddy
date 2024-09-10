package com.intbyte.wizbuddy.taskperchecklist.command.application.service;

import com.intbyte.wizbuddy.taskperchecklist.command.application.dto.TaskPerCheckListDTO;

import java.util.List;

public interface AppTaskPerCheckListService {
    void insertTaskPerCheckList(int checkListCode, int taskCode);
    void modifyTaskPerCheckList(TaskPerCheckListDTO dto);
    void insertTaskPerCheckListList(List<Integer> taskCodeList, int shopCode);

    void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode);
    void deleteTaskPerCheckListByCheckListCode(int checkListCode);
    void deleteTaskPerCheckListByTaskCode(int taskCode);
}
