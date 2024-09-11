package com.intbyte.wizbuddy.checklist.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CheckListServiceTests {

    @Autowired
    private CheckListService checkListService;

    @Test
    @DisplayName("어떤 매장의 true인 모든 checklist 가져오기")
    @Transactional
    public void findAllCheckListTest(){
        int shopCode = 1;

        checkListService.findCheckListByIdByShop(shopCode);
    }

    @Test
    @DisplayName("체크리스트 1개 조회 테스트")
    @Transactional
    public void findCheckListByIdTest(){
        int checkListCode = 1;
        checkListService.findCheckListById(checkListCode);
    }
}
