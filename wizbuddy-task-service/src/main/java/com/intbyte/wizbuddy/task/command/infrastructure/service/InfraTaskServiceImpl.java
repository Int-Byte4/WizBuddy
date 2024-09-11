package com.intbyte.wizbuddy.task.command.infrastructure.service;

import com.intbyte.wizbuddy.taskperchecklist.command.application.service.AppTaskPerCheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InfraTaskServiceImpl implements InfraTaskService {

    private final AppTaskPerCheckListService appTaskPerCheckListService;

    @Autowired
    public InfraTaskServiceImpl(AppTaskPerCheckListService appTaskPerCheckListService) {
        this.appTaskPerCheckListService = appTaskPerCheckListService;
    }

    // command 3. task 삭제
    @Transactional
    public void deleteTaskPerCheckListByTaskCode(int taskCode){
        appTaskPerCheckListService.deleteTaskPerCheckListByTaskCode(taskCode);
    }
}
