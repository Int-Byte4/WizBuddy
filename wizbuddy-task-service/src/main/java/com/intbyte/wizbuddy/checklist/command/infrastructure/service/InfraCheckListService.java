package com.intbyte.wizbuddy.checklist.command.infrastructure.service;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;

public interface InfraCheckListService {

    // command 1. 특정 매장의 체크리스트 등록, 고정된 업무를 추가
    void insertFixedTaskAndTaskPerCheckList(CheckListDTO checkListDTO);

    // command 2. 체크리스트에 특정 task 추가하는 상황 -> tpcs를 불러야함. -> 어떤 employee가 했는지도 알아야하네? -> 추가하는경우에는 필요없음.
    void insertTaskPerCheckList(int checkListCode, int taskCode);

    // command 3. 체크리스트에 특정 task 삭제하는 상황 -> tpcs를 불러서 거기서 없애버려야함. -> 어떤 애가 뺐는지는 상관없음 -> employeeCode 필요없음.
    void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode);

    // command 5. 체크리스트 삭제시 해당 체크리스트와 관련된 task도 모두 삭제해주는 메서드
    void deleteTaskPerCheckListByCheckListCode(int checkListCode);
}
