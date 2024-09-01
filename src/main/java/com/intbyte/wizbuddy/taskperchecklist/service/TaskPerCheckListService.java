package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
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
//    private final TaskPerCheckListMapper taskPerCheckListMapper;
    private final ModelMapper modelMapper;

    private final TaskPerCheckListMapper taskPerCheckListMapper;

    private CheckListRepository checkListRepository;

    private TaskRepository taskRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public TaskPerCheckListService(CheckListRepository checkListRepository, TaskPerCheckListRepository taskPerCheckListRepository, /*TaskPerCheckListMapper taskPerCheckListMapper,*/ ModelMapper modelMapper, TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskPerCheckListMapper taskPerCheckListMapper) {

        this.checkListRepository = checkListRepository;
        this.taskPerCheckListRepository = taskPerCheckListRepository;
        this.taskPerCheckListMapper = taskPerCheckListMapper;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // 1번 체크리스트에 1번 업무 추가
    @Transactional
    public void insertTaskPerCheckList(TaskPerCheckListDTO taskPerCheckListDTO) {

        TaskPerChecklistId taskPerChecklistId = new TaskPerChecklistId(
                taskPerCheckListDTO.getCheckListCode(),
                taskPerCheckListDTO.getTaskCode()
        );

        // CheckList, Task, Employee 객체 조회
        CheckList checkList = checkListRepository.findById(taskPerCheckListDTO.getCheckListCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid checkListCode"));
        Task task = taskRepository.findById(taskPerCheckListDTO.getTaskCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid taskCode"));
        Employee employee = employeeRepository.findById(taskPerCheckListDTO.getEmployeeCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employeeCode"));

        System.out.println("checkList = " + checkList);
        System.out.println("task = " + task);
        System.out.println("employee = " + employee);

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

    // taskPerCheckList에서 1개 조회
    @Transactional
    public TaskPerCheckListDTO findTaskPerCheckListById(TaskPerChecklistId taskPerChecklistId){

        TaskPerCheckListMybatis findTaskPerCheckList = taskPerCheckListMapper.findTaskPerCheckListById(taskPerChecklistId.getTaskCode(), taskPerChecklistId.getCheckListCode());

        return modelMapper.map(findTaskPerCheckList, TaskPerCheckListDTO.class);
    }

    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckList() {

        List<TaskPerCheckListMybatis> findTaskPerCheckList = taskPerCheckListMapper.findAllTaskPerCheckList();

        return findTaskPerCheckList.stream()
                .map(taskPerCheckList -> modelMapper.map(taskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void modifyTaskPerCheckList(int taskCode, int checkListCode, EditTaskPerCheckListInfo info){

        TaskPerChecklistId id = new TaskPerChecklistId(taskCode, checkListCode);

        TaskPerCheckList taskPerCheckList = taskPerCheckListRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // employee 객체를 찾아서 넣어주기 -> employeeRepository 필요
        Employee findEmployee = employeeRepository.findById(info.getEmployeeCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employeeCode"));

        // 업데이트
        taskPerCheckList.modify(info, findEmployee);

        taskPerCheckListRepository.save(taskPerCheckList);
    }
}
