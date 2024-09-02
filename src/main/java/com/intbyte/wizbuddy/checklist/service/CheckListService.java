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
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import com.intbyte.wizbuddy.taskperchecklist.repository.TaskPerCheckListRepository;
import com.intbyte.wizbuddy.taskperchecklist.service.TaskPerCheckListService;
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

    // 체크리스트 등록 -> 체크리스트가 등록될 때 체크리스트별 업무에도 등록해줘야함.
    // 4번 체크리스트 등록 -> 4번 체크리스트는 2번 매장에서 등록하기
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
        
        // 0. 체크리스트가 등록됨.
        checkListRepository.save(checkList);

        // 1. 체크리스트 테이블의 shop_code가 있는데 task 테이블에서 shop_code가 동일 + flag true + fixed_state true인 모든걸 가져옴.
        // -> List<Task> // shop도 있음.
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
        // 위의 Task를 만들었다!!!!

        // 2. 이 모든 task의 정보(taskCode), checkList의 정보(checkListCode)를 가지고 taskPerCheckList에 집어넣기. -> 일단 employeeCode는 NULL로 안넣어주기
        // -> taskCode가 아닌 Task, CheckList가 필요하다!! -> 이 데이터로 TaskPerCheckList로 만들고 save 하나하나 해주기
        for (int i = 0; i < allTask.size(); i++) {
            TaskPerCheckList taskPerCheckList = TaskPerCheckList.builder()
                    .taskPerChecklistId(new TaskPerChecklistId(checkList.getCheckListCode(), allTask.get(i).getTaskCode()))
                    .checkList(checkList)
                    .task(allTask.get(i))
                    .taskFinishedState(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            taskPerCheckListRepository.save(taskPerCheckList);
        }
    }

    // -------- 특정 매장의 모든 체크리스트를 조회 ------------

    //    체크리스트 조회
    @Transactional
    public CheckListDTO findCheckListById(int checkListCode){

        CheckListMybatis checkList = checkListMapper.findCheckListById(checkListCode);
        return modelMapper.map(checkList, CheckListDTO.class);
    }

    //    flag가 true 인 특정 매장의 모든 체크리스트 조회 (추가)
    @Transactional
    public List<CheckListDTO> findCheckListByIdByShop(int shopCode){

        List<CheckListMybatis> checkListList = checkListMapper.findAllCheckListByShopId(shopCode);

        return checkListList.stream()
                .map(checkList -> modelMapper.map(checkList, CheckListDTO.class))
                .collect(Collectors.toList());
    }

    //    체크리스트 수정
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

    @Transactional
    public void deleteTaskPerCheckList(int checkListCode){

        CheckList checkList = checkListRepository.findById(checkListCode).orElseThrow(CheckListNotFoundException::new);

        taskPerCheckListRepository.deleteByCheckList(checkList);
    }
}
