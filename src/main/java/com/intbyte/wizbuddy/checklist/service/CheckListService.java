package com.intbyte.wizbuddy.checklist.service;

import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.exception.checklist.CheckListNotFoundException;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
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

    @Autowired
    public CheckListService(CheckListRepository checkListRepository, CheckListMapper checkListMapper, ModelMapper modelMapper) {
        this.checkListRepository = checkListRepository;
        this.checkListMapper = checkListMapper;
        this.modelMapper = modelMapper;
    }

    // 체크리스트 등록
    @Transactional
    public void insertCheckList(CheckListDTO checkListInfo){

        CheckList checkList = CheckList.builder()
                .checkListTitle(checkListInfo.getCheckListTitle())
                .checkListFlag(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(checkListInfo.getShopCode())
                .build();

        checkListRepository.save(checkList);
    }

    //    체크리스트 조회
    @Transactional
    public CheckListDTO findCheckListById(int checkListCode){

        CheckList checkList = checkListMapper.findCheckListById(checkListCode);
        return modelMapper.map(checkList, CheckListDTO.class);
    }

    // 존재하는 모든 체크리스트 조회
    @Transactional
    public List<CheckListDTO> findAllCheckList(){
        List<CheckList> checkListList = checkListMapper.findAllCheckList();

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }

    // flag가 true인 모든 체크리스트 조회
    @Transactional
    public List<CheckListDTO> findAllCheckListsByFlag(){
        List<CheckList> checkListList = checkListMapper.findAllCheckListsByFlag();

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }

    //    체크리스트 업무 수정
    @Transactional
    public void modifyCheckList(int checkListCode, EditCheckListInfo modifyCheckListInfo){
        CheckList checkList = null;
        try{
            checkList = checkListMapper.findCheckListById(checkListCode);
        }catch (Exception e){
            throw new CheckListNotFoundException();
        }

        checkList.modify(modifyCheckListInfo);
        checkListRepository.save(checkList);
    }

//    체크리스트  삭제
    @Transactional
    public void deleteCheckList(int checkListCode){
        CheckList checkList = checkListMapper.findCheckListById(checkListCode);

        EditCheckListInfo editCheckListInfo = new EditCheckListInfo(checkList.getCheckListTitle()
                                                , false, LocalDateTime.now());

        checkList.modify(editCheckListInfo);

        checkListRepository.save(checkList);
    }
}
