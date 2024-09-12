package com.intbyte.wizbuddy.taskperchecklist.command.application.service;

import com.intbyte.wizbuddy.taskperchecklist.command.application.dto.TaskPerCheckListDTO;

import java.util.List;

public interface AppTaskPerCheckListService {
    // checklist infra command 1에서 호출. 특정 체크리스트에 여러개의 특정 업무 추가
    void insertTaskPerCheckListList(List<Integer> taskCodeList, int shopCode);

    // checklist infra command 2에서 호출 -> 특정 체크리스트에 특정 업무 추가 -> 이걸 수정해야겠다. -> 추가하는 경우에는 employeeCode는 필요없기때문
    void insertTaskPerCheckList(int checkListCode, int taskCode);

    // checklist infra command 3에서 호출. 특정 체크리스트에 존재하는 특정 업무 삭제
    void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode);

    // checklist infra command 5에서 호출. 특정 chekcListCode에 속하는 모든 Task 삭제하는 메서드
    void deleteTaskPerCheckListByCheckListCode(int checkListCode);

    // task infra command 3에서 호출
    void deleteTaskPerCheckListByTaskCode(int taskCode);

    // command 1. 특정 체크리스트에 특정 업무 완료표시
    void modifyTaskPerCheckList(TaskPerCheckListDTO dto);

    boolean findById(int checkListCode, int taskCode);
}
