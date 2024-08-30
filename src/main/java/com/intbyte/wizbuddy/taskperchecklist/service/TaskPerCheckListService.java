package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.mapper.TaskPerCheckListMapper;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
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

    private CheckListRepository checkListRepository;

    private TaskRepository taskRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public TaskPerCheckListService(CheckListRepository checkListRepository, TaskPerCheckListRepository taskPerCheckListRepository, /*TaskPerCheckListMapper taskPerCheckListMapper,*/ ModelMapper modelMapper, TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.checkListRepository = checkListRepository;
        this.taskPerCheckListRepository = taskPerCheckListRepository;
//        this.taskPerCheckListMapper = taskPerCheckListMapper;
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
// 좀따가 생성해야함. -> employee 생성 후에

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

//    @Transactional
//    public TaskPerCheckListDTO findTaskPerCheckListById(TaskPerChecklistId id) {
//
//
//    }
//
//    @Transactional
//    public List<TaskPerCheckListDTO> findAllTaskPerCheckLists() {
//        List<TaskPerCheckList> list = taskPerCheckListMapper.findAllTaskPerCheckList();
//
//        return list.stream()
//                .map(taskPerCheckList -> taskPerCheckListMapper
//
//
//    }


}
