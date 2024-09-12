package com.intbyte.wizbuddy.task.query.controller;

import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import com.intbyte.wizbuddy.task.query.service.TaskServiceImpl;
import com.intbyte.wizbuddy.task.query.vo.ResponseFindTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("TaskQueryController")
public class TaskController {
    // 1. 모든 조회 담당

    private final TaskServiceImpl taskService;

    @Autowired
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    // query 1. 특정 task 조회
    @GetMapping("/task/{taskcode}")
    @Operation(summary = "특정 업무 조회")
    public ResponseEntity<ResponseFindTaskVO> getTask(@PathVariable("taskcode") int taskCode){

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


    // query 2, 3, 4. fixed에 따른 모든 task 조회
    @GetMapping("/task/{shopcode}") // url 수정 필요하면 수정하기
    @Operation(summary = "특정 매장의 고정 상태에 따른 모든 업무 조회")
    public ResponseEntity<List<ResponseFindTaskVO>> getAllTaskByShopByFixedState(
            @PathVariable("shopcode") int shopCode,
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
}
