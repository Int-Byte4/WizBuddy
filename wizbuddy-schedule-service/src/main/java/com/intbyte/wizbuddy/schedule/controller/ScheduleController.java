package com.intbyte.wizbuddy.schedule.controller;

import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.info.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.service.ScheduleService;
import com.intbyte.wizbuddy.schedule.vo.request.RequestModifyScheduleByCommentVO;
import com.intbyte.wizbuddy.schedule.vo.request.RequestModifyScheduleVO;
import com.intbyte.wizbuddy.schedule.vo.request.RequestRegistEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.schedule.vo.request.RequestRegistScheduleVO;
import com.intbyte.wizbuddy.schedule.vo.response.ResponseFindEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.schedule.vo.response.ResponseModifyScheduleByCommentVO;
import com.intbyte.wizbuddy.schedule.vo.response.ResponseRegistEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.schedule.vo.response.ResponseRegistWeeklyScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    @GetMapping("/schedules")
    @Operation(summary = "전체 스케줄 조회")
    public ResponseEntity<List<WeeklyScheduleDTO>> findAllSchedules() {

        List<WeeklyScheduleDTO> weeklyScheduleDTOList = scheduleService.findAllSchedules();

        return new ResponseEntity<>(weeklyScheduleDTOList, HttpStatus.OK);
    }

    @GetMapping("/schedules/{scheduleCode}")
    @Operation(summary = "한주의 스케줄 상세 조회")
    public ResponseEntity<List<ResponseFindEmployeeWorkingPartVO>> findSchedule(
            @PathVariable("scheduleCode") int scheduleCode) {

        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = scheduleService.findSchedule(scheduleCode);

        List<ResponseFindEmployeeWorkingPartVO> response = employeeWorkingPartDTO.stream()
                .map(dto -> ResponseFindEmployeeWorkingPartVO
                        .builder()
                        .employeeCode(dto.getEmployeeCode())
                        .scheduleCode(dto.getScheduleCode())
                        .workingDate(dto.getWorkingDate())
                        .workingPartTime(dto.getWorkingPartTime()).build()).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("schedules/users/{employeeCode}")
    @Operation(summary = "직원 별 한주의 스케줄 상세 조회")
    public ResponseEntity<List<ResponseFindEmployeeWorkingPartVO>> findScheduleByEmployeeCode(
            @PathVariable("employeeCode") String employeeCode) {

        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = scheduleService
                .findScheduleByEmployeeCode(employeeCode);

        List<ResponseFindEmployeeWorkingPartVO> response = employeeWorkingPartDTO.stream()
                .map(dto -> ResponseFindEmployeeWorkingPartVO
                        .builder()
                        .employeeCode(dto.getEmployeeCode())
                        .scheduleCode(dto.getScheduleCode())
                        .workingDate(dto.getWorkingDate())
                        .workingPartTime(dto.getWorkingPartTime()).build()).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("regist")
    @Operation(summary = "한주의 스케줄 - 스케줄 등록")
    public ResponseEntity<ResponseRegistWeeklyScheduleVO> registSchedule(
            @RequestBody RequestRegistScheduleVO request) {

        WeeklyScheduleDTO weeklyScheduleDTO = modelMapper.map(request, WeeklyScheduleDTO.class);

        scheduleService.registSchedule(weeklyScheduleDTO);

        ResponseRegistWeeklyScheduleVO registSchedule = modelMapper
                .map(weeklyScheduleDTO, ResponseRegistWeeklyScheduleVO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(registSchedule);
    }

    @PostMapping("regist/employee")
    @Operation(summary = "한주의 스케줄 - 직원 등록")
    public ResponseEntity<ResponseRegistEmployeeWorkingPartVO> registSchedulePerEmployee(
            @RequestBody RequestRegistEmployeeWorkingPartVO request) {

        EmployeeWorkingPartDTO employeeWorkingPartDTO = modelMapper.map(request, EmployeeWorkingPartDTO.class);

        scheduleService.registSchedulePerEmployee(employeeWorkingPartDTO);

        ResponseRegistEmployeeWorkingPartVO registEmployee = modelMapper
                .map(employeeWorkingPartDTO, ResponseRegistEmployeeWorkingPartVO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(registEmployee);
    }

    @PatchMapping("modify/{workingPartCode}")
    @Operation(summary = "스케줄 수정")
    public ResponseEntity<EditScheduleInfo> editSchedule(
            @PathVariable("workingPartCode") int workingPartCode,
            @RequestBody RequestModifyScheduleVO request) {

        EditScheduleInfo editSchedule = modelMapper.map(request, EditScheduleInfo.class);

        scheduleService.editSchedule(workingPartCode, editSchedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(editSchedule);
    }

    @PatchMapping("modify")
    @Operation(summary = "댓글 채택으로 스케줄 수정")
    public ResponseEntity<ResponseModifyScheduleByCommentVO> editScheduleByComment(
            @RequestBody RequestModifyScheduleByCommentVO request) {

        scheduleService.editScheduleByComment(request.getSubsCode(), request.isSubsFlag(), request.getEmployeeCode());

        ResponseModifyScheduleByCommentVO editSchedule = modelMapper
                .map(request, ResponseModifyScheduleByCommentVO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(editSchedule);
    }

    @DeleteMapping("delete/{workingPartCode}")
    @Operation(summary = "스케줄 삭제")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("workingPartCode") int workingPartCode) {

        scheduleService.deleteSchedule(workingPartCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
