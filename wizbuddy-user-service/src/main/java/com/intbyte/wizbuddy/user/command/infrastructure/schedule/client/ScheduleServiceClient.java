package com.intbyte.wizbuddy.user.command.infrastructure.schedule.client;

//import com.intbyte.wizbuddy.board.vo.request.RequestDeleteSubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.request.RequestInsertSubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.request.RequestModifySubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.response.ResponseFindSubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
//import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
//import com.intbyte.wizbuddy.comment.vo.request.RequestAdoptCommentVO;
//import com.intbyte.wizbuddy.comment.vo.request.RequestDeleteCommentVO;
//import com.intbyte.wizbuddy.comment.vo.request.RequestInsertCommentVO;
//import com.intbyte.wizbuddy.comment.vo.request.RequestModifyCommentVO;
//import com.intbyte.wizbuddy.comment.vo.response.*;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.request.RequestModifyScheduleVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.request.RequestRegistEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.request.RequestRegistWeeklyScheduleVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.response.ResponseFindEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.response.ResponseRegistEmployeeVO;
import com.intbyte.wizbuddy.user.command.infrastructure.schedule.vo.response.ResponseRegistWeeklyScheduleVO;
import com.intbyte.wizbuddy.user.config.FeignClientConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="intbyte-schedule-service", configuration = FeignClientConfig.class)
public interface ScheduleServiceClient {

//    @GetMapping("/schedule-service/subsboards")
//    @Operation(summary = "대타게시글 전체 조회")
//    List<ResponseFindSubsBoardVO> getAllSubsBoards();
//
//    @GetMapping("/schedule-service/subsboards/{subsCode}")
//    @Operation(summary = "대타게시글 1개 조회")
//    ResponseFindSubsBoardVO getSubsBoardById(@PathVariable("subsCode") int subsCode);
//
//    @GetMapping("/schedule-service/subsboards/shop/{shopCode}")
//    @Operation(summary = "매장별 대타게시글 전체 조회")
//    List<ResponseFindSubsBoardVO> getSubsBoardsByShopCode(@PathVariable("shopCode") int shopCode);
//
//    @PostMapping("/schedule-service/subsboards")
//    @Operation(summary = "대타게시글 등록")
//    ResponseInsertSubsBoardVO createSubsBoard(@RequestBody RequestInsertSubsBoardVO requestInsertSubsBoardVO);
//
//    @PatchMapping("/schedule-service/subsboards/update/{subsCode}")
//    @Operation(summary = "대타게시글 수정")
//    ResponseModifySubsBoardVO updateSubsBoard(
//            @PathVariable("subsCode") int subsCode,
//            @RequestBody RequestModifySubsBoardVO requestModify);
//
//    @PatchMapping("/schedule-service/subsboards/delete/{subsCode}")
//    @Operation(summary = "대타게시글 삭제")
//    ResponseDeleteSubsBoardVO deleteSubsBoard(
//            @PathVariable("subsCode") int subsCode,
//            @RequestBody RequestDeleteSubsBoardVO requestDeleteSubsBoardVO);
//
//    @GetMapping("/schedule-service/comments")
//    @Operation(summary = "댓글 전체 조회")
//    List<ResponseFindCommentVO> getAllComments();
//
//    @GetMapping("/schedule-service/comments/{commentCode}")
//    @Operation(summary = "댓글 1개 조회")
//    ResponseFindCommentVO getCommentById(@PathVariable("commentCode") int commentCode);
//
//    @GetMapping("/schedule-service/comments/subs/{subsCode}")
//    @Operation(summary = "게시글별 댓글 전체 조회")
//    List<ResponseFindCommentVO> getCommentsBySubsCode(@PathVariable("subsCode") int subsCode);
//
//    @GetMapping("/schedule-service/comments/employee/{employeeCode}")
//    @Operation(summary = "직원별 댓글 전체 조회")
//    List<ResponseFindCommentVO> getCommentsByEmployeeCode(@PathVariable("employeeCode") String employeeCode);
//
//    @PostMapping("/schedule-service")
//    @Operation(summary = "댓글 등록")
//    ResponseInsertCommentVO registerComment(@RequestBody RequestInsertCommentVO request);
//
//    @PatchMapping("/schedule-service/update/{commentCode}")
//    @Operation(summary = "댓글 수정")
//    ResponseModifyCommentVO modifyComment(@PathVariable("commentCode") int commentCode,
//                                          @RequestBody RequestModifyCommentVO request);
//
//    @PatchMapping("/schedule-service/delete/{commentCode}")
//    @Operation(summary = "댓글 삭제")
//    ResponseDeleteCommentVO removeComment(@PathVariable("commentCode") int commentCode,
//                                          @RequestBody RequestDeleteCommentVO request);
//
//    @PatchMapping("/schedule-service/adopt/{commentCode}")
//    @Operation(summary = "댓글 채택")
//    ResponseAdoptCommentVO adoptComment(@PathVariable("commentCode") int commentCode,
//                                        @RequestBody RequestAdoptCommentVO request);

    @GetMapping("/schedule-service/schedule/schedules")
    @Operation(summary = "전체 스케줄 조회")
    List<WeeklyScheduleDTO> findAllSchedules();

    @GetMapping("/schedule-service/schedule/schedules/{scheduleCode}")
    @Operation(summary = "한주의 스케줄 상세 조회")
    List<ResponseFindEmployeeWorkingPartVO> findSchedule(
            @PathVariable("scheduleCode") int scheduleCode);

    @GetMapping("/schedule-service/schedule/schedules/users/{employeeCode}")
    @Operation(summary = "직원 별 한주의 스케줄 상세 조회")
    List<ResponseFindEmployeeWorkingPartVO> findScheduleByEmployeeCode(
            @PathVariable("employeeCode") String employeeCode);

    @PostMapping("/schedule-service/schedule/regist")
    @Operation(summary = "한주의 스케줄 - 스케줄 등록")
    ResponseRegistWeeklyScheduleVO registSchedule(
            @RequestBody RequestRegistWeeklyScheduleVO request);

    @PostMapping("/schedule-service/schedule/regist/employee")
    @Operation(summary = "한주의 스케줄 - 직원 등록")
    ResponseRegistEmployeeVO registSchedulePerEmployee(
            @RequestBody RequestRegistEmployeeWorkingPartVO request);

    @PatchMapping("/schedule-service/schedule/modify/{workingPartCode}")
    @Operation(summary = "스케줄 수정")
    ResponseModifyScheduleVO editSchedule(
            @PathVariable("workingPartCode") int workingPartCode,
            @RequestBody RequestModifyScheduleVO request);

//    @PatchMapping("/schedule-service/schedule/modify")
//    @Operation(summary = "댓글 채택으로 스케줄 수정")
//    ResponseModifyScheduleByCommentVO editScheduleByComment(
//            @RequestBody RequestModifyScheduleByCommentVO request);

    @DeleteMapping("/schedule-service/schedule/delete/{workingPartCode}")
    @Operation(summary = "스케줄 삭제")
    void deleteSchedule(@PathVariable("workingPartCode") int workingPartCode);

}
