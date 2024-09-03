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

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ModelMapper modelMapper;
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
    public TaskDTO findTaskById(int taskCode){

        TaskMybatis findTask = taskMapper.findTaskById(taskCode);

        if(findTask == null)
            throw new TaskNotFoundException();

        return modelMapper.map(findTask, TaskDTO.class);
    }

    @Transactional
    public List<TaskDTO> findAllTask(){

        List<TaskMybatis> findTasks = taskMapper.findAllTask();

        if(findTasks == null || findTasks.isEmpty())
            throw new TaskNotFoundException();

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


    @Transactional
    public List<TaskDTO> findAllTaskByShopCode(int shopCode){
        List<TaskMybatis> findTasks = taskMapper.findAllTaskByShopCode(shopCode);

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByFixedState(int shopCode){
        List<TaskMybatis> findTasksFixedState = taskMapper.findAllTaskByShopCodeByFixedState(shopCode);

        return findTasksFixedState.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDTO> findAllTaskByShopCodeByNonFixedState(int shopCode){
        List<TaskMybatis> findTasks = taskMapper.findAllTaskByShopCodeByNonFixedState(shopCode);

        return findTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
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
    public void modifyTask(int taskCode, EditTaskInfo modifyTaskInfo){

        Task task = taskRepository.findById(taskCode).orElseThrow(TaskNotFoundException::new);
        task.modify(modifyTaskInfo);

        taskRepository.save(task);
    }

    @Transactional
    public void deleteTaskPerCheckList(int taskCode){
        Task task = taskRepository.findById(taskCode).orElseThrow(TaskNotFoundException::new);

        taskPerCheckListRepository.deleteByTask(task);
    }
}
