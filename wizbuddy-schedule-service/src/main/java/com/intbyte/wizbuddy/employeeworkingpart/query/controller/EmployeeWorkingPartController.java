package com.intbyte.wizbuddy.employeeworkingpart.query.controller;

import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.query.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.employeeworkingpart.query.vo.EmployeeWorkingPartVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("queryEmployeeWorkingPartController")
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class EmployeeWorkingPartController {

    private final EmployeeWorkingPartService employeeWorkingPartService;

    @GetMapping("/schedules/{scheduleCode}")
    @Operation(summary = "한주의 스케줄 상세 조회")
    public ResponseEntity<List<EmployeeWorkingPartDTO>> findSchedule(
            @PathVariable("scheduleCode") int scheduleCode) {

        List<EmployeeWorkingPartVO> employeeWorkingPartDTO = employeeWorkingPartService.findSchedule(scheduleCode);

        List<EmployeeWorkingPartDTO> response = employeeWorkingPartDTO.stream()
                .map(dto -> EmployeeWorkingPartDTO
                        .builder()
                        .employeeCode(dto.getEmployeeCode())
                        .scheduleCode(dto.getScheduleCode())
                        .workingDate(dto.getWorkingDate())
                        .workingPartTime(dto.getWorkingPartTime()).build()).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("schedules/users/{employeeCode}")
    @Operation(summary = "직원 별 한주의 스케줄 상세 조회")
    public ResponseEntity<List<EmployeeWorkingPartDTO>> findScheduleByEmployeeCode(
            @PathVariable("employeeCode") String employeeCode) {

        List<EmployeeWorkingPartVO> employeeWorkingPartDTO = employeeWorkingPartService
                .findScheduleByEmployeeCode(employeeCode);

        List<EmployeeWorkingPartDTO> response = employeeWorkingPartDTO.stream()
                .map(dto -> EmployeeWorkingPartDTO
                        .builder()
                        .employeeCode(dto.getEmployeeCode())
                        .scheduleCode(dto.getScheduleCode())
                        .workingDate(dto.getWorkingDate())
                        .workingPartTime(dto.getWorkingPartTime()).build()).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
