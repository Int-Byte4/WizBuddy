package com.intbyte.wizbuddy.weeklyschedule.command.application.controller;

import com.intbyte.wizbuddy.weeklyschedule.command.application.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.weeklyschedule.command.application.service.WeeklyScheduleService;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.request.RequestRegistWeeklyScheduleVO;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.response.ResponseRegistWeeklyScheduleVO;
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
public class WeeklyScheduleController {

    private final WeeklyScheduleService weeklyScheduleService;
    private final ModelMapper modelMapper;

    @PostMapping("regist")
    @Operation(summary = "한주의 스케줄 - 스케줄 등록")
    public ResponseEntity<ResponseRegistWeeklyScheduleVO> registSchedule(
            @RequestBody RequestRegistWeeklyScheduleVO request) {

        WeeklyScheduleDTO weeklyScheduleDTO = modelMapper.map(request, WeeklyScheduleDTO.class);

        weeklyScheduleService.registSchedule(weeklyScheduleDTO);

        ResponseRegistWeeklyScheduleVO registSchedule = modelMapper
                .map(weeklyScheduleDTO, ResponseRegistWeeklyScheduleVO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(registSchedule);
    }
}
