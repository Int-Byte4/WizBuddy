package com.intbyte.wizbuddy.task.command.application.controller;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;
import com.intbyte.wizbuddy.task.command.application.service.AppTaskServiceImpl;
import com.intbyte.wizbuddy.task.command.domain.aggregate.vo.RequestInsertTaskVO;
import com.intbyte.wizbuddy.task.command.domain.aggregate.vo.RequestModifyTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("TaskCommandController")
public class TaskController {

    private final AppTaskServiceImpl taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(AppTaskServiceImpl taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    // command 1. task 최초 추가
    @PostMapping("/task")
    @Operation(summary = "특정 매장에 업무 추가")
    public ResponseEntity<String> insertTask(
            @RequestBody RequestInsertTaskVO request
    ){

        TaskDTO taskDTO = modelMapper.map(request, TaskDTO.class);

        taskService.insertTask(taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("task 추가 완료");
    }

    // command 2, 3. 특정 task 자체 수정, 삭제
    @PostMapping("/task/{taskcode}")
    @Operation(summary = "특정 매장에 특정 업무 수정(삭제)")
    public ResponseEntity<String> modifyTask(
            @PathVariable("taskcode") int taskCode,
            @RequestBody RequestModifyTaskVO request
    ) {

        TaskDTO taskDTO = modelMapper.map(request, TaskDTO.class);

        if(taskDTO.isTaskFlag()){
            taskService.modifyTask(taskCode, taskDTO);
            return ResponseEntity.status(HttpStatus.OK).body(taskCode + "번 task 수정 완료");
        }else{
            taskService.deleteTask(taskCode, taskDTO); // infra에서 수행해야함.
            return ResponseEntity.status(HttpStatus.OK).body(taskCode + "번 task 삭제 완료");
        }
    }
}
