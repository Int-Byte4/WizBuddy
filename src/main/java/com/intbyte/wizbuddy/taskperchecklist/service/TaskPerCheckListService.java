package com.intbyte.wizbuddy.taskperchecklist.service;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.checklist.repository.CheckListRepository;
import com.intbyte.wizbuddy.exception.checklist.CheckListNotFoundException;
import com.intbyte.wizbuddy.exception.task.TaskNotFoundException;
import com.intbyte.wizbuddy.exception.user.EmployeeNotFoundException;
import com.intbyte.wizbuddy.mapper.CheckListMapper;
import com.intbyte.wizbuddy.mapper.ShopMapper;
import com.intbyte.wizbuddy.mapper.TaskMapper;
import com.intbyte.wizbuddy.mapper.TaskPerCheckListMapper;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.task.repository.TaskRepository;
import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.domain.TaskPerCheckListMybatis;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckListId;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.repository.TaskPerCheckListRepository;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final TaskMapper taskMapper;
    private final CheckListMapper checkListMapper;
    private final ShopMapper shopMapper;

    @Autowired
    public TaskPerCheckListService(CheckListRepository checkListRepository, TaskPerCheckListRepository taskPerCheckListRepository,
                                   TaskPerCheckListMapper taskPerCheckListMapper, ModelMapper modelMapper, TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskMapper taskMapper, CheckListMapper checkListMapper, ShopMapper shopMapper) {

        this.checkListRepository = checkListRepository;
        this.taskPerCheckListRepository = taskPerCheckListRepository;
        this.taskPerCheckListMapper = taskPerCheckListMapper;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskMapper = taskMapper;
        this.checkListMapper = checkListMapper;
        this.shopMapper = shopMapper;
    }

    // 1번 체크리스트에 1번 업무 추가
    @Transactional
    public void insertTaskPerCheckList(TaskPerCheckListDTO taskPerCheckListDTO) {

        TaskPerCheckListId taskPerChecklistId = new TaskPerCheckListId(
                taskPerCheckListDTO.getCheckListCode(),
                taskPerCheckListDTO.getTaskCode()
        );
        Employee employee = null;
        // CheckList, Task, Employee 객체 조회
        CheckList checkList = checkListRepository.findById(taskPerCheckListDTO.getCheckListCode())
                .orElseThrow(CheckListNotFoundException::new);
        Task task = taskRepository.findById(taskPerCheckListDTO.getTaskCode())
                .orElseThrow(TaskNotFoundException::new);

        if(taskPerCheckListDTO.getEmployeeCode() != null){
            employee = employeeRepository.findById(taskPerCheckListDTO.getEmployeeCode())
                    .orElseThrow(EmployeNotFoundException::new);
        }

        TaskPerCheckList taskPerCheckList = TaskPerCheckList.builder()
                .checkList(checkList)
                .task(task)
                .taskPerCheckListId(taskPerChecklistId)
                .taskFinishedState(taskPerCheckListDTO.getTaskFinishedState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employee(employee)
                .build();

        taskPerCheckListRepository.save(taskPerCheckList);
    }

    // taskPerCheckList에서 1개 조회
    @Transactional
    public TaskPerCheckListDTO findTaskPerCheckListById(TaskPerCheckListId taskPerChecklistId) {

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

    // 특정 checkList의 완료된 task만 조회 -> ????????
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListFinished(String employeeCode) {

        List<TaskPerCheckListMybatis> findTaskPerCheckList = taskPerCheckListMapper.findAllTaskPerCheckListFinished(employeeCode);

        return findTaskPerCheckList.stream()
                .map(taskPerCheckList -> modelMapper.map(taskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    } // 이거 이상함!!!!!!!!!!!!!!!!


    // 특정 업무를 직원이 완료 누르면 수정시간 바뀌게 하는거.
    @Transactional
    public void modifyTaskPerCheckList(EditTaskPerCheckListInfo info) {

        // 9월 3일 제훈 수정 후 커밋 : 아래 주석은 까먹고 안 지운 것 같아서 지우려다가 혹시 몰라서 냅두고 커밋함
//        TaskPerCheckListId id = new TaskPerCheckListId(taskCode, checkListCode);
        TaskPerCheckListId id = new TaskPerCheckListId(info.getCheckListCode(), info.getTaskCode());

        TaskPerCheckList taskPerCheckList = taskPerCheckListRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // employee 객체를 찾아서 넣어주기 -> employeeRepository 필요
        Employee findEmployee = employeeRepository.findById(info.getEmployeeCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employeeCode"));

        // 업데이트
        taskPerCheckList.modify(info, findEmployee);

        taskPerCheckListRepository.save(taskPerCheckList);
    }

    // 1번 체크리스트에 존재하는 모든 업무 조회(완료 + 미완료)
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCode(int checkListCode) {

        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCode(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 1번 체크리스트에 존재하는 모든 완료된 업무 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode) {

        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 1번 체크리스트에 존재하는 모든 미완료된 업무 조회
    @Transactional
    public List<TaskPerCheckListDTO> findAllTaskPerCheckListByCheckListCodeByNotFinished(int checkListCode) {
        List<TaskPerCheckListMybatis> taskPerCheckListByCheckListCode = taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByNonFinished(checkListCode);

        return taskPerCheckListByCheckListCode.stream()
                .map(TaskPerCheckList -> modelMapper.map(TaskPerCheckList, TaskPerCheckListDTO.class))
                .collect(Collectors.toList());
    }

    // 1번 체크리스트에 1번 업무 추가 -> 위ㅏ에 있음.


    // 1번 체크리스트에 존재하는 1번 업무 삭제 -> 체크리스트나 업무가 삭제되는게 아니라 체크리스트 안에있는 업무가 삭제된다는 의미(더이상 체크리스트에 속하지 않는다.)
    @Transactional
    public void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode) {

        TaskPerCheckListId id = new TaskPerCheckListId(checkListCode, taskCode);

        taskPerCheckListRepository.deleteById(id);
    }
}
