package com.intbyte.wizbuddy.weeklyschedule.query.controller;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.weeklyschedule.query.service.WeeklyScheduleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("queryWeeklyScheduleController")
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class WeeklyScheduleController {

    private final WeeklyScheduleServiceImpl weeklyScheduleService;

    @GetMapping("/schedules")
    @Operation(summary = "전체 스케줄 조회")
    public ResponseEntity<List<WeeklyScheduleDTO>> findAllSchedules() {

        List<WeeklyScheduleDTO> weeklyScheduleDTOList = weeklyScheduleService.findAllSchedules();

        return new ResponseEntity<>(weeklyScheduleDTOList, HttpStatus.OK);
    }
}
