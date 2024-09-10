package com.intbyte.wizbuddy.checklist.command.application.service;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;

public interface AppCheckListService {

    // command 1. 특정 매장의 체크리스트 최초 생성 후, 고정된 업무를 추가 -> infra에서 진행
    void insertCheckList(CheckListDTO checkListDTO);

    // command 2. 특정 체크리스트에 특정 업무 추가 -> 이건 url을 어떻게 짜느냐에 따라 다를거같은데 그냥 이걸로 하면 될듯?
    void insertTaskToCheckList(int checkListCode, int taskCode);

    // command 3. 특정 체크리스트에 특정 업무 삭제 // 신규
    void deleteTaskFromCheckList(int checkListCode, int taskCode);

    // command 4. 체크리스트 자체 수정 -> 체크리스트 수정이 체크리스트 자체 수정 + 체크리스트 업무 수정이 있음. 이건 자체 수정
    void modifyCheckList(int checkListCode, CheckListDTO checkListDTO);

    // command 5. 체크리스트 삭제
    void deleteCheckList(int checkListCode);
}
