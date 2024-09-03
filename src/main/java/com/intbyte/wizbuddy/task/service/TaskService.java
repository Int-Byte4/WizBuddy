package com.intbyte.wizbuddy.task.service;

import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.exception.task.TaskNotFoundException;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
import com.intbyte.wizbuddy.task.domain.TaskMybatis;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import com.intbyte.wizbuddy.taskperchecklist.repository.TaskPerCheckListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository; // jpa
    private final TaskMapper taskMapper;        // mybatis
    private final ModelMapper modelMapper; // dto <-> entity 변환
    private final ShopRepository shopRepository;
    private final TaskPerCheckListRepository taskPerCheckListRepository;
    private final CheckListMapper checkListMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper mapper, ModelMapper modelMapper, @Qualifier("shopRepository") ShopRepository shopRepository, @Qualifier("taskPerCheckListRepository") TaskPerCheckListRepository taskPerCheckListRepository, CheckListMapper checkListMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = mapper;
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
        this.taskPerCheckListRepository = taskPerCheckListRepository;
        this.checkListMapper = checkListMapper;
    }

    @Transactional
    public void insertTask(TaskDTO taskInfo) {

        Shop shop = shopRepository.findById(taskInfo.getShopCode()).orElseThrow(ShopNotFoundException::new);

        Task task = Task.builder()
                .taskContents(taskInfo.getTaskContents())
                .taskFlag(true)
                .taskFixedState(taskInfo.isTaskFixedState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shop(shop)
                .build();

        taskRepository.save(task);
    }

    @Transactional
    public TaskDTO findTaskById(int taskCode){

        TaskMybatis findTask = taskMapper.findTaskById(taskCode);
        return modelMapper.map(findTask, TaskDTO.class);
    }

    @Transactional
    public List<TaskDTO> findAllTask(){

        List<TaskMybatis> findTasks = taskMapper.findAllTask();

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDTO> findAllTaskByFixedState(){

        List<TaskMybatis> findTasks = taskMapper.findAllTaskByFixedState();

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDTO> findAllTasksByTaskFlag(){

        List<TaskMybatis> findTasks = taskMapper.findAllTasksByTaskFlag();

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }
    // ---------- 관리자 입장에서 사용하는 기능(매장 상관없이 모든 업무 조회) -------------


    // 특정 매장의 모든 task 조회
    @Transactional
    public List<TaskDTO> findAllTaskByShopCode(int shopCode){
        List<TaskMybatis> findTasks = taskMapper.findAllTaskByShopCode(shopCode);

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    // 특정 매장의 고정 task 보기
    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode){
        List<TaskMybatis> findTasksFixedState = taskMapper.findAllTaskByShopCodeByFixedState(shopCode);

        return findTasksFixedState.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    // 특정 매장의 고정 아닌 task 보기
    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode){
        List<TaskMybatis> findTasks = taskMapper.findAllTaskByShopCodeByNonFixedState(shopCode);

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }
    // 이 2개의 기능은 동적 쿼리를 통해 하나로 줄일 수 있을듯?
    // ---------- 매장 관리자(사장) 입장에서 사용하는 조회 기능 -------------



    // 특정 매장의 수정, 삭제 기능 필요 -> 필요 없을거 같음. 이미 사장이 보는 task는 본인 매장의 task이기 때문에
    // task 번호만 알면 그걸 삭제하거나 수정하면됨 !!
    @Transactional
    public void modifyTask(int taskCode, EditTaskInfo modifyTaskInfo){

        Task task = taskRepository.findById(taskCode).orElseThrow(TaskNotFoundException::new);

        task.modify(modifyTaskInfo);
        taskRepository.save(task);
    }

    // Task의 task_flag가 false가 된다 -> 해당 task는 삭제된것 -> taskPerCheckList에서도 삭제해줘야함.
    // 원래라면 db 제약조건에 의해서 바로 처리 되겠지만 우리는 flag로 soft delete를 했기 때문에 따로 처리해줘야함.
    @Transactional
    public void deleteTaskPerCheckList(int taskCode){

        Task task = taskRepository.findById(taskCode).orElseThrow(TaskNotFoundException::new);

        // 이미 이 함수에 들어온 순간 flag가 false인것. -> taskCode와 shopCode를 안다.
        // -> shopCode를 통해 checkList에서 shopCode를 가진 모든 checkList를 뽑아야함.
//        checkListMapper.findAllCheckListByShopId() // shopCode가 동일한(flag true) 모든 checkList 가져오는 메서드 만들기

        taskPerCheckListRepository.deleteByTask(task);
    }
}
