package com.intbyte.wizbuddy.checklist.service;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.exception.checklist.CheckListNotFoundException;
import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckListMapper checkListMapper;
    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;

    @Autowired
    public CheckListService(CheckListRepository checkListRepository, CheckListMapper checkListMapper, ModelMapper modelMapper, ShopRepository shopRepository) {
        this.checkListRepository = checkListRepository;
        this.checkListMapper = checkListMapper;
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
    }

    // 체크리스트 등록
    @Transactional
    public void insertCheckList(CheckListDTO checkListInfo){

        Shop shop = shopRepository.findById(checkListInfo.getShopCode()).orElseThrow(ShopNotFoundException::new);

        CheckList checkList = CheckList.builder()
                .checkListTitle(checkListInfo.getCheckListTitle())
                .checkListFlag(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(shop)
                .build();

        checkListRepository.save(checkList);
    }

    //    체크리스트 조회
    @Transactional
    public CheckListDTO findCheckListById(int checkListCode){

        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListCode);
        return modelMapper.map(checkList, CheckListDTO.class);
    }

    // 존재하는 모든 체크리스트 조회
    @Transactional
    public List<CheckListDTO> findAllCheckList(){
        List<CheckListMybatis> checkListList = checkListMapper.findAllCheckList();

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }

    // flag가 true인 모든 체크리스트 조회
    @Transactional
    public List<CheckListDTO> findAllCheckListsByFlag(){
        List<CheckListMybatis> checkListList = checkListMapper.findAllCheckListsByFlag();

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }

    //    체크리스트 업무 수정
    @Transactional
    public void modifyCheckList(int checkListCode, EditCheckListInfo modifyCheckListInfo){
        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        checkList.modify(modifyCheckListInfo);
        checkListRepository.save(checkList);
    }

//    체크리스트  삭제
    @Transactional
    public void deleteCheckList(int checkListCode){

        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        EditCheckListInfo editCheckListInfo = new EditCheckListInfo(checkList.getCheckListTitle()
                                                , false, LocalDateTime.now());

        checkList.modify(editCheckListInfo);

        checkListRepository.save(checkList);
    }
}
