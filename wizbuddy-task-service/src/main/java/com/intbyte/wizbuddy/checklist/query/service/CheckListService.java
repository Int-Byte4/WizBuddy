package com.intbyte.wizbuddy.checklist.query.service;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListDTO;

import java.util.List;

public interface CheckListService {
    CheckListDTO findCheckListById(int checkListCode);

    List<CheckListDTO> findCheckListByIdByShop(int shopCode);
}
