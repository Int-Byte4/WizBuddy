package com.intbyte.wizbuddy.checklist.command.domain.service;

import org.springframework.transaction.annotation.Transactional;

public interface DomainCheckListService {

    @Transactional
    void validateAndAddTaskToCheckList(int checkListCode, int taskCode);
}
