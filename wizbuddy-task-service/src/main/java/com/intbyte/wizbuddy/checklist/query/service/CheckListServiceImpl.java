package com.intbyte.wizbuddy.checklist.query.service;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListQueryDTO;
import com.intbyte.wizbuddy.checklist.query.repository.CheckListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckListServiceImpl implements CheckListService {

    private final CheckListMapper checkListMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckListServiceImpl(CheckListMapper checkListMapper, ModelMapper modelMapper) {
        this.checkListMapper = checkListMapper;
        this.modelMapper = modelMapper;
    }


//    쿼리 ㅇ{ㅖ외처리!!!!!!!!!!!!
    // 1. 특정 체크리스트 조회
    @Override
    @Transactional
    public CheckListQueryDTO findCheckListById(int checkListCode){

        CheckListQueryDTO checkList = checkListMapper.findCheckListById(checkListCode);

        if(checkList == null) return null;
//            throw new CheckListNotFoundException();

        return checkList;
    }

    // 2. flag가 true 인 특정 매장의 모든 체크리스트 조회
    @Override
    @Transactional
    public List<CheckListQueryDTO> findCheckListByIdByShop(int shopCode){

        return checkListMapper.findAllCheckListByShopId(shopCode);

//        if(checkListList == null || checkListList.isEmpty())
//            throw new CheckListNotFoundException();
    }
}
