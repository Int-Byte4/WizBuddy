package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTests {

    @Autowired
    private ScheduleService scheduleService;


    @Test
    @DisplayName("전체 스케줄 조회 성공")
    public void testScheduleService_select() {
        List<WeeklyScheduleDTO> weeklyScheduleDTOList = scheduleService.findAllSchedules();
        Assertions.assertNotNull(weeklyScheduleDTOList);
        weeklyScheduleDTOList.forEach(System.out::println);
    }

    @Test
    @DisplayName("한주의 스케줄 조회 성공")
    public void testScheduleService_select_per_employee() {
        //given, when
        List<EmployeeWorkingPartDTO> employeeWorkingPartList = scheduleService.findSchedule(1);

        //then
        assertNotNull(employeeWorkingPartList);
        employeeWorkingPartList.forEach(System.out::println);
    }

    @Test
    @DisplayName("스케줄 등록 성공")
    public void testScheduleService_insert() {
        WeeklyScheduleDTO weeklyScheduleDTOList = scheduleService.registWeeklySchedule(new WeeklyScheduleDTO(4, true, new Date(), LocalDateTime.now(), LocalDateTime.now()));
        assertNotNull(weeklyScheduleDTOList);
    }






}