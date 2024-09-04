package com.intbyte.wizbuddy.task.controller;


import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.service.TaskService;
import com.intbyte.wizbuddy.task.vo.request.RequestInsertTaskVO;
import com.intbyte.wizbuddy.task.vo.request.RequestModifyTaskVO;
import com.intbyte.wizbuddy.task.vo.response.ResponseFindTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    // 1. 특정 task 조회
    @GetMapping("/task/{taskCode}")
    @Operation(summary = "특정 업무 조회")
    public ResponseEntity<ResponseFindTaskVO> getTask(@PathVariable("taskCode") int taskCode){

        TaskDTO taskDTO = taskService.findTaskById(taskCode);

        ResponseFindTaskVO findTask = ResponseFindTaskVO.builder()
                .taskCode(taskDTO.getTaskCode())
                .taskContents(taskDTO.getTaskContents())
                .taskFlag(taskDTO.isTaskFlag())
                .taskFixedState(taskDTO.isTaskFixedState())
                .createdAt(taskDTO.getCreatedAt())
                .updatedAt(taskDTO.getUpdatedAt())
                .shopCode(taskDTO.getShopCode())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(findTask);
    }

    // 2. 특정 매장의 fixed에 따른 task 조회
    @GetMapping("/shop/{shopCode}/task")
    @Operation(summary = "특정 매장의 고정 상태에 따른 업무 조회")
    public ResponseEntity<List<ResponseFindTaskVO>> getAllTaskByShopByFixedState(
            @PathVariable("shopCode") int shopCode,
            @RequestParam(name = "fixed", required = false) Boolean fixed) {

        List<TaskDTO> taskDTOList;

        if (fixed == null) {
            // fixed 파라미터가 없을 경우, 모든 Task를 반환
            taskDTOList = taskService.findAllTaskByShopCode(shopCode);
        } else if (fixed) {
            // fixed가 true일 경우, 고정된 Task를 반환
            taskDTOList = taskService.findAllTaskByShopCodeByFixedState(shopCode);
        } else {
            // fixed가 false일 경우, 고정되지 않은 Task를 반환
            taskDTOList = taskService.findAllTaskByShopCodeByNonFixedState(shopCode);
        }

        List<ResponseFindTaskVO> responseTasks = taskDTOList
                .stream()
                .map(taskDTO -> ResponseFindTaskVO.builder()
                        .taskCode(taskDTO.getTaskCode())
                        .taskContents(taskDTO.getTaskContents())
                        .taskFlag(taskDTO.isTaskFlag())
                        .taskFixedState(taskDTO.isTaskFixedState())
                        .createdAt(taskDTO.getCreatedAt())
                        .updatedAt(taskDTO.getUpdatedAt())
                        .shopCode(taskDTO.getShopCode())
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseTasks);
    }

    // 3. 특정 매장에 1개 task 추가
    @PostMapping("/shop/{shopCode}/task")
    @Operation(summary = "특정 매장에 업무 추가")
    public ResponseEntity<String> insertTask(
            @PathVariable("shopCode") int shopCode,
            @RequestBody RequestModifyTaskVO request){

        TaskDTO taskDTO = modelMapper.map(request, TaskDTO.class);

        taskService.insertTask(taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("추가 완료");
    }

    // 4. 특정 매장의 특정 task 수정(삭제도 같이 수행)
    @PostMapping("/shop/{shopCode}/task/{taskCode}")
    @Operation(summary = "특정 매장에 특정 업무 수정(삭제)")
    public ResponseEntity<String> modifyTask(
            @PathVariable("shopCode") int shopCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestInsertTaskVO request
    ) {
        EditTaskInfo editTaskInfo = new EditTaskInfo(request.getTaskContents(), request.isTaskFlag(), request.isTaskFixedState(), request.getUpdatedAt());
        taskService.modifyTask(taskCode, editTaskInfo);

        if(editTaskInfo.isTaskFlag()){
            return ResponseEntity.status(HttpStatus.OK).body("task 수정 완료");
        }else{
            taskService.deleteTaskPerCheckList(taskCode);
            return ResponseEntity.status(HttpStatus.OK).body("task 삭제 완료");
        }
    }
}