package com.intbyte.wizbuddy.task.controller;


import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.service.TaskService;
import com.intbyte.wizbuddy.task.vo.request.RequestInsertTaskVO;
import com.intbyte.wizbuddy.task.vo.response.ResponseFindTaskVO;
import com.intbyte.wizbuddy.task.vo.response.ResponseInsertTaskVO;
import com.intbyte.wizbuddy.task.vo.response.ResponseModifyTaskVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/shop/{shopCode}")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

//    // 특정 task 조회
//    @GetMapping("/task/{taskCode}") // '/'를 붙이면 requestMapping 적용 x
//    public ResponseEntity<ResponseFindTaskVO> getTask(@PathVariable("taskCode") int taskCode){
//
//        TaskDTO taskDTO = taskService.findTaskById(taskCode);
//
//        ResponseFindTaskVO findTask = ResponseFindTaskVO.builder()
//                .taskCode(taskDTO.getTaskCode())
//                .taskContents(taskDTO.getTaskContents())
//                .taskFlag(taskDTO.isTaskFlag())
//                .taskFixedState(taskDTO.isTaskFixedState())
//                .createdAt(taskDTO.getCreatedAt())
//                .updatedAt(taskDTO.getUpdatedAt())
//                .shopCode(taskDTO.getShopCode())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.OK).body(findTask);
//    }
    // 추후 기능개발이 마무리 된 후에 개발 필요

    // 특정 매장의 task 조회
    @GetMapping("/shop/{shopCode}/task")
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

    // 특정 매장에 1개 task 추가
    @PostMapping("/shop/{shopCode}/task")
    public ResponseEntity<Void>insertTask(
            @PathVariable("shopCode") int shopCode,
            @RequestBody RequestInsertTaskVO request){

        TaskDTO taskDTO = modelMapper.map(request, TaskDTO.class);

        taskService.insertTask(taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 특정 매장의 특정 task 수정(삭제도 같이 수행)
    @PostMapping("/shop/{shopCode}/task/{taskCode}")
    public ResponseEntity<Void> modifyTask(
            @PathVariable("shopCode") int shopCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestInsertTaskVO request
    ) {
        EditTaskInfo editTaskInfo = new EditTaskInfo(request.getTaskContents(), request.isTaskFlag(), request.isTaskFixedState(), request.getUpdatedAt());

        taskService.modifyTask(taskCode, editTaskInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

//1. 요청으로 보내는거하고 응답으로 받는거 차이를 생각하기

//-> user에서 보면 요청과 응답의 클래스 객체는 다른데 내부 구성요소는 동일하다.
//
//        2. vo에는 setter가 없다. -> vo가 사용자가 보내주는 데이터?
//        -> 사용자가 보내는 데이터는 변화x(불변)이기 때문에 setter를 적어주지 않는다.


//        3. 일단 task에서 어떤 요청이 들어오는거지?
//        -> 1. task 등록하는 요청 들어옴.   -> PostMapping("/task")
//        -> 2. task 조회하는 요청이 들어옴.  -> GetMapping("/task")
//
//        -> 3. task를 체크리스트에 등록하는 요청이 들어옴. == 이거는 taskperchecklist에서 진행해야할듯
//
//        -> 3. task 삭제하는 요청이 들어옴.
//        -> 4. task를 고정시키는 요청이 들어옴.
//        (3, 4번은 통째로 수정으로 처리하면 될듯) -> 수정은 how?
//
//        위 4개 요청이 들어올거같음.
//        아니다.
//        -> 5. task의 데이터를 바꾸는 요청이 들어옴. -> 이것도 수정이네? 그럼 345번을 모두 한번에 처리해줘야함.
