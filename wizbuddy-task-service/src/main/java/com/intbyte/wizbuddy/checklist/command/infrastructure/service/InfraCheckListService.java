package com.intbyte.wizbuddy.checklist.command.infrastructure.service;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;

public interface InfraCheckListService {

    public void insertFixedTaskAndTaskPerCheckList(CheckListDTO checkListDTO);
}
