package com.intbyte.wizbuddy.taskperchecklist.controller;


import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.service.TaskPerCheckListService;
import com.intbyte.wizbuddy.taskperchecklist.vo.Response.ResponseFindTaskPerCheckListVO;
import com.intbyte.wizbuddy.taskperchecklist.vo.request.RequestInsertTaskPerCheckListVO;
import com.intbyte.wizbuddy.taskperchecklist.vo.request.RequestModifyTaskPerCheckListVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskPerCheckListController {

    private final TaskPerCheckListService taskPerCheckListService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskPerCheckListController(TaskPerCheckListService taskPerCheckListService, ModelMapper modelMapper) {
        this.taskPerCheckListService = taskPerCheckListService;
        this.modelMapper = modelMapper;
    }

    // 1-1, 1-2, 1-3. finishedState에 따른 특정 매장의 특정 체크리스트 속 업무 조회
    @GetMapping("/taskperchecklist/checklist/{checkListCode}")
    public ResponseEntity<List<ResponseFindTaskPerCheckListVO>> getAllTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @RequestParam(value = "finished", required = false) Boolean finished) {

        List<TaskPerCheckListDTO> dtoList;

        if(finished == null)
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(checkListCode);
        else if (finished)
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);
        else
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByNotFinished(checkListCode);

        List<ResponseFindTaskPerCheckListVO> response = dtoList.stream().map(
                dto -> ResponseFindTaskPerCheckListVO.builder()
                        .checkListCode(dto.getCheckListCode())
                        .taskCode(dto.getTaskCode())
                        .taskFinishedState(dto.getTaskFinishedState())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .employeeCode(dto.getEmployeeCode())
                        .build()
        ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 2. 특정 체크리스트에 특정 업무 추가
    @PostMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<Void> insertTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestInsertTaskPerCheckListVO request
    ){
        TaskPerCheckListDTO dto = modelMapper.map(request, TaskPerCheckListDTO.class);
        dto.setTaskCode(taskCode);
        dto.setCheckListCode(checkListCode);

        taskPerCheckListService.insertTaskPerCheckList(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 3. 특정 매장의 특정 체크리스트에 특정 업무 삭제
    @DeleteMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<String> deleteTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode
    ){
        taskPerCheckListService.deleteTaskPerCheckListByCheckListCodeAndTaskCode(checkListCode, taskCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("체크리스트에서 업무 삭제 정공");
    }


    // 4. 특정 매장의 특정 체크리스트에 특정 업무 완료표시(체크리스트, 업무, 직원) -> 수정과 삭제가 나눠진것. 이건 수정임
    @PutMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<String> modifyTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestModifyTaskPerCheckListVO request
    ){

        EditTaskPerCheckListInfo info = new EditTaskPerCheckListInfo(
                checkListCode, taskCode, request.getTaskFinishedState(), request.getUpdatedAt(), request.getEmployeeCode()
        );
        taskPerCheckListService.modifyTaskPerCheckList(info);

        return ResponseEntity.status(HttpStatus.OK).body("업무 완료표시 수정 성공");
    }

}
