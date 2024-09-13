package com.intbyte.wizbuddy.task.command.application.service;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;

public interface AppTaskService {

    // command 1. task 최초 추가
    void insertTask(TaskDTO taskDTO);

    // command 2. task 자체 수정
    void modifyTask(int taskCode, TaskDTO taskDTO);

    // command 3. task 삭제 -> task를 삭제한 순간 infra를 호출하고 infra에서 tpcs에서 taskcode와 관련된 모든거 없애기
    void deleteTask(int taskCode, TaskDTO taskDTO);
}
