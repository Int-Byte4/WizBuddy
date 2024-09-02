package com.intbyte.wizbuddy.taskperchecklist.controller;

import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.taskperchecklist.dto.TaskPerCheckListDTO;
import com.intbyte.wizbuddy.taskperchecklist.service.TaskPerCheckListService;
import com.intbyte.wizbuddy.taskperchecklist.vo.Response.ResponseFindTaskPerCheckListVO;
import com.intbyte.wizbuddy.taskperchecklist.vo.request.RequestInsertTaskPerCheckListVO;
import com.intbyte.wizbuddy.taskperchecklist.vo.request.RequestModifyTaskPerCheckListVO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskPerCheckListController {

    private final TaskPerCheckListService taskPerCheckListService;
    private final ModelMapper modelMapper;

    public TaskPerCheckListController(TaskPerCheckListService taskPerCheckListService, ModelMapper modelMapper) {
        this.taskPerCheckListService = taskPerCheckListService;
        this.modelMapper = modelMapper;
    }


    // 0. 특정 매장의 특정 체크리스트에 특정 매장의 고정된 업무 추가하기
    // -> 1. task에서 매장 코드와 같은 모든 업무를 찾아옴(task_flag가 true, task_fixed_state가 true인것)
    // -> 2. 파라미터로 받아온 checkListCode에 넣어주기


//    // 1. 특정 매장의 특정 체크리스트의 모든 업무 조회(특정 매장은 필요없음. -> 이미 체크리스트라는 정보가 어떤 매장이라는 정보를 담고있음.)
//    @GetMapping("/taskperchecklist/checklist/{checkListCode}")
//    public ResponseEntity<List<ResponseFindTaskPerCheckListVO>> getAllTaskPerCheckList(
//            @PathVariable("checkListCode") int checkListCode) {
//        // 1번 체크리스트의 모든 업무 조회
//
//        List<TaskPerCheckListDTO> dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(checkListCode);
//
//        List<ResponseFindTaskPerCheckListVO> response = dtoList.stream().map(
//                dto -> ResponseFindTaskPerCheckListVO.builder().
//                        checkListCode(dto.getCheckListCode()).
//                        taskCode(dto.getTaskCode()).
//                        taskFinishedState(dto.getTaskFinishedState()).
//                        createdAt(dto.getCreatedAt()).
//                        updatedAt(dto.getUpdatedAt()).
//                        employeeCode(dto.getEmployeeCode()).
//                        build()
//                    ).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 2. 특정 매장의 특정 체크리스트의 완료된 업무 조회 (1번과 매우 비슷함) -> 이거도 합칠수 있을거갗은데? @RequestParam으로 처리 가능할듯? 1, 2번 합치려고하니까 일단 보류
//    @GetMapping("/taskperchecklist/checklist/{checkListCode}/1")
//    public ResponseEntity<List<ResponseFindTaskPerCheckListVO>> getAllTaskPerCheckListByFinished(
//            @PathVariable("checkListCode") int checkListCode
//    ) {
//        // 1번 체크리스트의 완성된 모든 업무 조회
//
//        List<TaskPerCheckListDTO> dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);
//
//        List<ResponseFindTaskPerCheckListVO> response = dtoList.stream().map(
//                dto -> ResponseFindTaskPerCheckListVO.builder().
//                        checkListCode(dto.getCheckListCode()).
//                        taskCode(dto.getTaskCode()).
//                        taskFinishedState(dto.getTaskFinishedState()).
//                        createdAt(dto.getCreatedAt()).
//                        updatedAt(dto.getUpdatedAt()).
//                        employeeCode(dto.getEmployeeCode()).
//                        build()
//        ).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    // 1-1, 1-2, 1-3. 특정 매장의 특정 체크리스트 속 업무 조회 -> ?finished=true이면 완료된 업무만, 아니면 모든 업무만 (finished 안된 업무도 보여주기)
    @GetMapping("/taskperchecklist/checklist/{checkListCode}")
    public ResponseEntity<List<ResponseFindTaskPerCheckListVO>> getAllTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @RequestParam(value = "finished", required = false) Boolean finished) {

        List<TaskPerCheckListDTO> dtoList;

        if(finished == null) {
            // 특정 체크리스트별 모든 업무 조회
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCode(checkListCode);
        } else if (finished) {
            // 완료된 업무만 조회
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);
        } else {
            // 미완료된 업무만 조회
            dtoList = taskPerCheckListService.findAllTaskPerCheckListByCheckListCodeByNotFinished(checkListCode);
        }

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

    // 2. 특정 매장의 (특정 체크리스트에 특정 업무 추가)
    @PostMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<Void> insertTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestInsertTaskPerCheckListVO request
    ){
        TaskPerCheckListDTO dto = modelMapper.map(request, TaskPerCheckListDTO.class);

        taskPerCheckListService.insertTaskPerCheckList(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 4. 특정 매장의 특정 체크리스트에 특정 업무 삭제 -> 결국 체크리스트에서 빼잖아? -> 체크리스트에서 해줘야할거같은데
    // 근데 또 생각해보니까 체크리스트에서는 이걸 모름
    // "삭제" 버튼을 누르면 여기로 매핑되게 하면 되지 않을까? 결국 여기에서 삭제를 해줘야한다는 의미
    @DeleteMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<Void> deleteTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode
    ){
        taskPerCheckListService.deleteTaskPerCheckListByCheckListCodeAndTaskCode(checkListCode, taskCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    // 5. 특정 매장의 특정 체크리스트에 특정 업무 완료표시(체크리스트, 업무, 직원) -> 수정과 삭제가 나눠진것. 이건 수정임
    @PutMapping("/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
    public ResponseEntity<Void> modifyTaskPerCheckList(
            @PathVariable("checkListCode") int checkListCode,
            @PathVariable("taskCode") int taskCode,
            @RequestBody RequestModifyTaskPerCheckListVO request
    ){

        EditTaskPerCheckListInfo info = new EditTaskPerCheckListInfo(
                checkListCode, taskCode, request.getTaskFinishedState(), request.getUpdatedAt(), request.getEmployeeCode()
        );
        taskPerCheckListService.modifyTaskPerCheckList(info);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
