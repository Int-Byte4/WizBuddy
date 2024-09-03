package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.exception.checklist.CheckListNotFoundException;
import com.intbyte.wizbuddy.exception.task.TaskNotFoundException;
import com.intbyte.wizbuddy.mapper.TaskPerCheckListMapper;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.domain.TaskPerCheckListMybatis;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.repository.TaskPerCheckListRepository;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskPerCheckListService {

    private final TaskPerCheckListRepository taskPerCheckListRepository;
    private final ModelMapper modelMapper;
    private final TaskPerCheckListMapper taskPerCheckListMapper;
    private final CheckListRepository checkListRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TaskPerCheckListService(TaskPerCheckListRepository taskPerCheckListRepository, ModelMapper modelMapper, TaskPerCheckListMapper taskPerCheckListMapper, CheckListRepository checkListRepository, TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskPerCheckListRepository = taskPerCheckListRepository;
        this.modelMapper = modelMapper;
        this.taskPerCheckListMapper = taskPerCheckListMapper;
        this.checkListRepository = checkListRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // taskPerCheckList에서 1개 조회
    @Transactional
    public TaskPerCheckListDTO findTaskPerCheckListById(TaskPerChecklistId taskPerChecklistId){

        TaskPerCheckListMybatis findTaskPerCheckList =
                taskPerCheckListMapper.findTaskPerCheckListById(taskPerChecklistId.getTaskCode(), taskPerChecklistId.getCheckListCode());

        return modelMapper.map(findTaskPerCheckList, TaskPerCheckListDTO.class);
    }

    // 모든 taskPerCheckList 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckList() {

        List<TaskPerCheckListMybatis> findTaskPerCheckList = taskPerCheckListMapper.findAllTaskPerCheckList();

        return findTaskPerCheckList.stream()
                .map(taskPerCheckList -> modelMapper.map(taskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 특정 checkList의 완료된 task만 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListFinished(String employeeCode) {

        List<TaskPerCheckListMybatis> findTaskPerCheckList = taskPerCheckListMapper.findAllTaskPerCheckListFinished(employeeCode);

        return findTaskPerCheckList.stream()
                .map(taskPerCheckList -> modelMapper.map(taskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }


    // 1-1. 특정 체크리스트에 존재하는 모든 완료된 업무 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode){

        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 1-2. 특정 체크리스트에 존재하는 모든 미완료된 업무 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByNotFinished(int checkListCode) {
        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByNonFinished(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 1-3. 특정 체크리스트에 존재하는 모든 업무 조회(완료 + 미완료)
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCode(int checkListCode){

        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCode(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 2. 특정 체크리스트에 특정 업무 추가
    @Transactional
    public void insertTaskPerCheckList(TaskPerCheckListDTO taskPerCheckListDTO) {

        TaskPerChecklistId taskPerChecklistId = new TaskPerChecklistId(
                taskPerCheckListDTO.getCheckListCode(),
                taskPerCheckListDTO.getTaskCode()
        );

        Employee employee = null;

        if(taskPerCheckListDTO.getEmployeeCode() != null)
            employee = employeeRepository.findById(taskPerCheckListDTO.getEmployeeCode()).orElseThrow(EmployeeNotFoundException::new);

        CheckList checkList = checkListRepository.findById(taskPerCheckListDTO.getCheckListCode()).orElseThrow(CheckListNotFoundException::new);
        Task task = taskRepository.findById(taskPerCheckListDTO.getTaskCode()).orElseThrow(TaskNotFoundException::new);

        TaskPerCheckList taskPerCheckList = TaskPerCheckList.builder()
                .checkList(checkList)
                .task(task)
                .taskPerChecklistId(taskPerChecklistId)
                .taskFinishedState(taskPerCheckListDTO.getTaskFinishedState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employee(employee)
                .build();

        taskPerCheckListRepository.save(taskPerCheckList);
    }

    // 3. 특정 체크리스트에 존재하는 1번 업무 삭제
    @Transactional
    public void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode){

        TaskPerChecklistId id = new TaskPerChecklistId(checkListCode, taskCode);

        taskPerCheckListRepository.deleteById(id);
    }

    // 4. 특정 체크리스트에 특정 업무 완료표시
    @Transactional
    public void modifyTaskPerCheckList(EditTaskPerCheckListInfo info){

        TaskPerChecklistId id = new TaskPerChecklistId(info.getCheckListCode(), info.getTaskCode());
        TaskPerCheckList taskPerCheckList = taskPerCheckListRepository.findById(id).orElseThrow(TaskPerCheckListNotFoundException::new);

        Employee findEmployee = employeeRepository.findById(info.getEmployeeCode()).orElseThrow();

        taskPerCheckList.modify(info, findEmployee);

        taskPerCheckListRepository.save(taskPerCheckList);
    }
}
