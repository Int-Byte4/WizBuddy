package com.intbyte.wizbuddy.checklist.command.infrastructure.service;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.command.domain.repository.CheckListRepository;
import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import com.intbyte.wizbuddy.task.query.service.TaskService;
import com.intbyte.wizbuddy.taskperchecklist.command.application.service.AppTaskPerCheckListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class InfraCheckListServiceImpl implements InfraCheckListService {

    private final CheckListRepository checkListRepository;
    private final TaskService taskService;
    private final AppTaskPerCheckListService appTaskPerCheckListService;

    public InfraCheckListServiceImpl(CheckListRepository checkListRepository, TaskService taskService, AppTaskPerCheckListService appTaskPerCheckListService) {
        this.checkListRepository = checkListRepository;
        this.taskService = taskService;
        this.appTaskPerCheckListService = appTaskPerCheckListService;
    }

    // command 1. 특정 매장의 체크리스트 등록, 고정된 업무를 추가
    @Override
    @Transactional
    public void insertFixedTaskAndTaskPerCheckList(CheckListDTO checkListDTO){

        // 1. taskMapper를 통해 task를 다 찾아와야함. (query) -> shopCode를 통해서 찾아옴.
        List<TaskDTO> allTaskDTOByShopCode = taskService.findAllTaskByShopCode(checkListDTO.getShopCode());
        List<Integer> taskCodeList = new ArrayList<>();
        for (int i = 0; i <allTaskDTOByShopCode.size(); i++) taskCodeList.add(allTaskDTOByShopCode.get(i).getTaskCode());

        // 2. TaskPerCheckList 서비스를 통해서 이걸 등록 (command)
        appTaskPerCheckListService.insertTaskPerCheckListList(taskCodeList, checkListDTO.getShopCode());
    }

    // command 2. 체크리스트에 특정 task 추가하는 상황 -> tpcs를 불러야함. -> 어떤 employee가 했는지도 알아야하네? -> 추가하는경우에는 필요없음.
    @Override
    @Transactional
    public void insertTaskPerCheckList(int checkListCode, int taskCode){
        appTaskPerCheckListService.insertTaskPerCheckList(checkListCode, taskCode);
    }

    // command 3. 체크리스트에 특정 task 삭제하는 상황 -> tpcs를 불러서 거기서 없애버려야함. -> 어떤 애가 뺐는지는 상관없음 -> employeeCode 필요없음.
    @Override
    @Transactional
    public void deleteTaskPerCheckListByCheckListCodeAndTaskCode(int checkListCode, int taskCode) {
        appTaskPerCheckListService.deleteTaskPerCheckListByCheckListCodeAndTaskCode(checkListCode, taskCode);
    }

    // command 5. 체크리스트 삭제시 해당 체크리스트와 관련된 task도 모두 삭제해주는 메서드
    @Override
    @Transactional
    public void deleteTaskPerCheckListByCheckListCode(int checkListCode) {
        appTaskPerCheckListService.deleteTaskPerCheckListByCheckListCode(checkListCode);
    }

    @Override
    @Transactional
    public TaskDTO findShopCodeByTaskCode(int taskCode){
        return taskService.findTaskById(taskCode);
    }

    @Override
    @Transactional
    public boolean isTaskAlreadyInCheckList(int checkListCode, int taskCode){
        return appTaskPerCheckListService.findById(checkListCode, taskCode);
    }

}
