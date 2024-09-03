package com.intbyte.wizbuddy.checklist.service;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.exception.checklist.CheckListNotFoundException;
import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.task.domain.TaskMybatis;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckListId;
import com.intbyte.wizbuddy.taskperchecklist.repository.TaskPerCheckListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckListMapper checkListMapper;
    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;
    private final TaskMapper taskMapper;
    private final TaskPerCheckListRepository taskPerCheckListRepository;

    @Autowired
    public CheckListService(CheckListRepository checkListRepository, CheckListMapper checkListMapper, ModelMapper modelMapper, ShopRepository shopRepository, TaskMapper taskMapper, @Qualifier("taskPerCheckListRepository") TaskPerCheckListRepository taskPerCheckListRepository) {
        this.checkListRepository = checkListRepository;
        this.checkListMapper = checkListMapper;
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
        this.taskMapper = taskMapper;
        this.taskPerCheckListRepository = taskPerCheckListRepository;
    }

    // 1. 특정 체크리스트 조회
    @Transactional
    public CheckListDTO findCheckListById(int checkListCode){

        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListCode);

        if(checkList == null)
            throw new CheckListNotFoundException();

        return modelMapper.map(checkList, CheckListDTO.class);
    }

    // 2. flag가 true 인 특정 매장의 모든 체크리스트 조회
    @Transactional
    public List<CheckListDTO> findCheckListByIdByShop(int shopCode){

        List<CheckListMybatis> checkListList = checkListMapper.findAllCheckListByShopId(shopCode);

        if(checkListList == null || checkListList.isEmpty())
            throw new CheckListNotFoundException();

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }


    // 3. 특정 매장의 체크리스트 등록, 고정된 업무를 추가
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

        int shopCode = shop.getShopCode();
        List<TaskMybatis> allTaskByShopCode = taskMapper.findAllTaskByShopCodeByFixedState(shopCode);
        List<Task> allTask = new ArrayList<>();
        for (int i = 0; i < allTaskByShopCode.size(); i++) {
            Task task = Task.builder()
                    .taskCode(allTaskByShopCode.get(i).getTaskCode())
                    .taskContents(allTaskByShopCode.get(i).getTaskContents())
                    .taskFlag(allTaskByShopCode.get(i).isTaskFlag())
                    .taskFixedState(allTaskByShopCode.get(i).isTaskFixedState())
                    .createdAt(allTaskByShopCode.get(i).getCreatedAt())
                    .updatedAt(allTaskByShopCode.get(i).getUpdatedAt())
                    .shop(shop)
                    .build();
            allTask.add(task);
        }

        for (int i = 0; i < allTask.size(); i++) {
            TaskPerCheckList taskPerCheckList = TaskPerCheckList.builder()
                    .taskPerCheckListId(new TaskPerCheckListId(checkList.getCheckListCode(), allTask.get(i).getTaskCode()))
                    .checkList(checkList)
                    .task(allTask.get(i))
                    .taskFinishedState(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            taskPerCheckListRepository.save(taskPerCheckList);
        }
    }

    // 4. 체크리스트 수정
    @Transactional
    public void modifyCheckList(int checkListCode, EditCheckListInfo modifyCheckListInfo){

        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        checkList.modify(modifyCheckListInfo);
        checkListRepository.save(checkList);
    }

    // 5. 체크리스트 삭제
    @Transactional
    public void deleteCheckList(int checkListCode){

        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        EditCheckListInfo editCheckListInfo = new EditCheckListInfo(checkList.getCheckListTitle()
                                                , false, LocalDateTime.now());

        checkList.modify(editCheckListInfo);

        checkListRepository.save(checkList);
    }

    // 0. 특정 체크리스트에서 업무 삭제
    @Transactional
    public void deleteTaskPerCheckList(int checkListCode){

        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        taskPerCheckListRepository.deleteByCheckList(checkList);
    }
}
