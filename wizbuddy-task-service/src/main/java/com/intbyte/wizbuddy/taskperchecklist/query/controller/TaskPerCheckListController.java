package com.intbyte.wizbuddy.taskperchecklist.query.controller;

import com.intbyte.wizbuddy.taskperchecklist.query.dto.TaskPerCheckListQueryDTO;
import com.intbyte.wizbuddy.taskperchecklist.query.service.TaskPerCheckListServiceImpl;
import com.intbyte.wizbuddy.taskperchecklist.query.vo.ResponseFindTaskPerCheckListVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("TaskPerCheckListQueryController")
@RequestMapping("/taskperchecklist")
public class TaskPerCheckListController {

    private final TaskPerCheckListServiceImpl taskPerCheckListService;

    @Autowired
    public TaskPerCheckListController(TaskPerCheckListServiceImpl taskPerCheckListService) {
        this.taskPerCheckListService = taskPerCheckListService;
    }

    // query 1-1, 1-2, 1-3. finishedState에 따른 특정 체크리스트 속 업무 조회
    @GetMapping("/checklist/{checkListCode}")
    @Operation(summary = "종료 상태에 따른 체크리스트속 업무 조회")
    public ResponseEntity<List<ResponseFindTaskPerCheckListVO>> getAllTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @RequestParam(value = "finished", required = false) Boolean finished) {

        List<TaskPerCheckListQueryDTO> dtoList;

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
}