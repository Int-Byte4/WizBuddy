package com.intbyte.wizbuddy.checklist.query.service;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListQueryDTO;

import java.util.List;

public interface CheckListService {

    // 1. 특정 체크리스트 조회
    CheckListQueryDTO findCheckListById(int checkListCode);

    // 2. flag가 true 인 특정 매장의 모든 체크리스트 조회
    List<CheckListQueryDTO> findCheckListByIdByShop(int shopCode);
}
