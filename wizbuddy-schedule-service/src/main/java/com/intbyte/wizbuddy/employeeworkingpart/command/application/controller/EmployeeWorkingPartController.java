package com.intbyte.wizbuddy.employeeworkingpart.command.application.controller;

import com.intbyte.wizbuddy.employeeworkingpart.command.application.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.command.application.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.request.RequestModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.request.RequestRegistEmployeeWorkingPartVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseRegistEmployeeWorkingPartVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class EmployeeWorkingPartController {

    private final EmployeeWorkingPartService employeeWorkingPartService;
    private final ModelMapper modelMapper;

    @PostMapping("regist/employee")
    @Operation(summary = "한주의 스케줄 - 직원 등록")
    public ResponseEntity<ResponseRegistEmployeeWorkingPartVO> registSchedulePerEmployee(
            @RequestBody RequestRegistEmployeeWorkingPartVO request) {

        EmployeeWorkingPartDTO employeeWorkingPartDTO = modelMapper.map(request, EmployeeWorkingPartDTO.class);

        employeeWorkingPartService.registSchedulePerEmployee(employeeWorkingPartDTO);

        ResponseRegistEmployeeWorkingPartVO registEmployee = modelMapper
                .map(employeeWorkingPartDTO, ResponseRegistEmployeeWorkingPartVO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(registEmployee);
    }

    @PatchMapping("modify/{workingPartCode}")
    @Operation(summary = "스케줄 수정")
    public ResponseEntity<ResponseModifyScheduleVO> editSchedule(
            @PathVariable("workingPartCode") int workingPartCode,
            @RequestBody RequestModifyScheduleVO request) {

        ResponseModifyScheduleVO editSchedule = modelMapper.map(request, ResponseModifyScheduleVO.class);

        employeeWorkingPartService.editSchedule(workingPartCode, editSchedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(editSchedule);
    }

    @DeleteMapping("delete/{workingPartCode}")
    @Operation(summary = "스케줄 삭제")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("workingPartCode") int workingPartCode) {

        employeeWorkingPartService.deleteSchedule(workingPartCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
