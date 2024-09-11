//package com.intbyte.wizbuddy.user.command.infrastructure.client;
//
//import com.intbyte.wizbuddy.user.config.FeignClientConfig;
//import com.intbyte.wizbuddy.task.checklist.vo.RequestInsertCheckListVO;
//import com.intbyte.wizbuddy.task.checklist.vo.RequestModifyCheckListVO;
//import com.intbyte.wizbuddy.task.checklist.vo.ResponseFindCheckListVO;
//import com.intbyte.wizbuddy.task.task.vo.RequestInsertTaskVO;
//import com.intbyte.wizbuddy.task.task.vo.RequestModifyTaskVO;
//import com.intbyte.wizbuddy.task.task.vo.ResponseFindTaskVO;
//import com.intbyte.wizbuddy.task.taskperchecklist.vo.RequestInsertTaskPerCheckListVO;
//import com.intbyte.wizbuddy.task.taskperchecklist.vo.RequestModifyTaskPerCheckListVO;
//import com.intbyte.wizbuddy.task.taskperchecklist.vo.ResponseFindTaskPerCheckListVO;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name="intbyte-checklist-service", url="localhost:8000", configuration = FeignClientConfig.class)
//public interface CheckListServiceClient {
//
//    // CheckList
//    // 1. 특정 checkList 조회
//    @GetMapping("/checklist-service/checklist/{checkListCode}")
//    @Operation(summary = "특정 체크리스트 조회")
//    List<ResponseFindCheckListVO> getCheckListById(@PathVariable("checkListCode") int checkListCode);
//
//    // 2. 특정 매장의 flag가 ture인 모든 checklist 조회
//    @GetMapping("/checklist-service/shop/{shopCode}/checklist")
//    @Operation(summary = "특정 매장의 모든 체크리스트 조회")
//    List<ResponseFindCheckListVO> getAllCheckListByShop(@PathVariable("shopCode") int shopCode);
//
//    // 3. 특정 매장에 체크리스트 1개 등록
//    @PostMapping("/checklist-service/shop/{shopCode}/checklist")
//    @Operation(summary = "특정 매장에 체크리스트 등록")
//    String insertCheckList(
//            @PathVariable("shopCode") int shopCode,
//            @RequestBody RequestInsertCheckListVO request
//    );
//
//    // 4, 5. 특정 매장의 특정 체크리스트 수정, 삭제
//    @PostMapping("/checklist-service/shop/{shopCode}/checklist/{checkListCode}")
//    @Operation(summary = "특정 체크리스트 수정, 삭제")
//    String modifyCheckList(
//            @PathVariable("shopCode") int shopCode,
//            @PathVariable("checkListCode") int checkListCode,
//            @RequestBody RequestModifyCheckListVO request
//    );
//
//
//    // Task
//    // 1. 특정 task 조회
//    @GetMapping("/checklist-service/task/{taskCode}")
//    @Operation(summary = "특정 업무 조회")
//    ResponseFindTaskVO getTask(@PathVariable("taskCode") int taskCode
//    );
//
//    // 2. 특정 매장의 fixed에 따른 task 조회
//    @GetMapping("/checklist-service/shop/{shopCode}/task")
//    @Operation(summary = "특정 매장의 고정 상태에 따른 업무 조회")
//    List<ResponseFindTaskVO> getAllTaskByShopByFixedState(
//            @PathVariable("shopCode") int shopCode,
//            @RequestParam(name = "fixed", required = false) Boolean fixed
//    );
//
//    // 3. 특정 매장에 1개 task 추가
//    @PostMapping("/checklist-service/shop/{shopCode}/task")
//    @Operation(summary = "특정 매장에 업무 추가")
//    String insertTask( @PathVariable("shopCode") int shopCode,
//                       @RequestBody RequestInsertTaskVO request
//    );
//
//    // 4. 특정 매장의 특정 task 수정(삭제도 같이 수행)
//    @PostMapping("/checklist-service/shop/{shopCode}/task/{taskCode}")
//    @Operation(summary = "특정 매장에 특정 업무 수정(삭제)")
//    String modifyTask(
//            @PathVariable("shopCode") int shopCode,
//            @PathVariable("taskCode") int taskCode,
//            @RequestBody RequestModifyTaskVO request
//    );
//
//
//    // TaskPerCheckList
//    // 1-1, 1-2, 1-3. finishedState에 따른 특정 매장의 특정 체크리스트 속 업무 조회
//    @GetMapping("/checklist-service/taskperchecklist/checklist/{checkListCode}")
//    @Operation(summary = "종료 상태에 따른 체크리스트속 업무 조회")
//    List<ResponseFindTaskPerCheckListVO> getAllTaskPerCheckList(
//            @PathVariable("checkListCode") int checkListCode,
//            @RequestParam(value = "finished", required = false) Boolean finished
//    );
//
//    // 2. 특정 체크리스트에 특정 업무 추가
//    @PostMapping("/checklist-service/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
//    @Operation(summary = "특정 체크리스트에 특정 업무 추가")
//    String insertTaskPerCheckList(
//            @PathVariable("checkListCode") int checkListCode,
//            @PathVariable("taskCode") int taskCode,
//            @RequestBody RequestInsertTaskPerCheckListVO request
//    );
//
//    // 3. 특정 매장의 특정 체크리스트에 특정 업무 삭제
//    @DeleteMapping("/checklist-service/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
//    @Operation(summary = "특정 체크리스트의 특정 업무 삭제")
//    String deleteTaskPerCheckList(
//            @PathVariable("checkListCode") int checkListCode,
//            @PathVariable("taskCode") int taskCode
//    );
//
//    // 4. 특정 매장의 특정 체크리스트에 특정 업무 완료표시(체크리스트, 업무, 직원) -> 수정과 삭제가 나눠진것. 이건 수정임
//    @PutMapping("/checklist-service/taskperchecklist/checklist/{checkListCode}/task/{taskCode}")
//    @Operation(summary = "특정 체크리스트의 특정 업무 완료 표시")
//    String modifyTaskPerCheckList(
//            @PathVariable("checkListCode") int checkListCode,
//            @PathVariable("taskCode") int taskCode,
//            @RequestBody RequestModifyTaskPerCheckListVO request
//    );
//}
