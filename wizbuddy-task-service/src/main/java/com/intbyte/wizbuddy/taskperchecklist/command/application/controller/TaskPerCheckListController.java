package com.intbyte.wizbuddy.taskperchecklist.command.application.controller;

import com.intbyte.wizbuddy.taskperchecklist.command.application.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.command.application.service.AppTaskPerCheckListServiceImpl;
import com.intbyte.wizbuddy.taskperchecklist.command.domain.aggregate.vo.RequestModifyTaskPerCheckListVO;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("TaskPerCheckListCommandController")
public class TaskPerCheckListController {

    private final AppTaskPerCheckListServiceImpl taskPerCheckListService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskPerCheckListController(AppTaskPerCheckListServiceImpl taskPerCheckListService, ModelMapper modelMapper) {
        this.taskPerCheckListService = taskPerCheckListService;
        this.modelMapper = modelMapper;
    }

    // command 1. 특정 매장의 특정 체크리스트에 특정 업무 완료표시(체크리스트, 업무, 직원)(수정)
    @PutMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    @Operation(summary = "특정 체크리스트의 특정 업무 완료 표시")
    public ResponseEntity<String> modifyTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestModifyTaskPerCheckListVO request
    ){

        TaskPerCheckListDTO dto = modelMapper.map(request, TaskPerCheckListDTO.class);
        dto.setTaskCode(taskCode);
        dto.setCheckListCode(checkListCode);

        taskPerCheckListService.modifyTaskPerCheckList(dto);

        return ResponseEntity.status(HttpStatus.OK).body(checkListCode + "번 업무 완료표시" + request.getEmployeeCode() + "가 수정 성공");
    }
}
