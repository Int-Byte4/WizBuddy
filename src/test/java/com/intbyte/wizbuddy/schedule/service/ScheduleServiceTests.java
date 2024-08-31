package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
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
        // given
        // when
        List<WeeklyScheduleDTO> weeklyScheduleDTOList = scheduleService.findAllSchedules();

        // then
        Assertions.assertNotNull(weeklyScheduleDTOList);
        weeklyScheduleDTOList.forEach(System.out::println);
    }

    @Test
    @DisplayName("한주의 스케줄 조회 성공")
    public void testScheduleService_select_per_employee() {
        // given
        int scheduleCode = 1;

        // when
        List<EmployeeWorkingPartDTO> employeeWorkingPartList = scheduleService.findSchedule(scheduleCode);

        // then
        assertNotNull(employeeWorkingPartList);
        employeeWorkingPartList.forEach(System.out::println);
    }

    @Test
    @DisplayName("직원별 스케줄 조회 성공")
    public void testScheduleService_select_employee() {
        // given
        int employeeCode = 8;

        // when
        List<EmployeeWorkingPartDTO> employeeWorkPartList = scheduleService.findScheduleByEmployeeCode(employeeCode);

        // then
        assertNotNull(employeeWorkPartList);
        employeeWorkPartList.forEach(System.out::println);
    }

    @Test
    @DisplayName("스케줄 등록 성공")
    public void testScheduleService_insert() {
        // given
        WeeklyScheduleDTO weeklyScheduleDTO = new WeeklyScheduleDTO(2, true, new Date(), LocalDateTime.now(), LocalDateTime.now());

        // when
        WeeklyScheduleDTO savedSchedule = scheduleService.registWeeklySchedule(weeklyScheduleDTO);

        // then
        assertNotNull(savedSchedule);
    }

    @Test
    @DisplayName("직원 근무배치 성공")
    public void testScheduleService_insert_employee() {
        // given
        EmployeeWorkingPartDTO employeeWorkingPartDTO = new EmployeeWorkingPartDTO(19, 6, 2, LocalDateTime.now(), "1T");

        // when
        EmployeeWorkingPartDTO savedEmployeeWorkingPart = scheduleService.registSchedulePerEmployee(employeeWorkingPartDTO);

        // then
        assertNotNull(savedEmployeeWorkingPart);
    }

    @Test
    @DisplayName("스케줄 수정 성공")
    public void testScheduleService_update() {
        // given
        int employeeCode = 18;
        EditScheduleInfo editScheduleInfo = new EditScheduleInfo(8, "1T");

        // when
        scheduleService.EditSchedule(employeeCode, editScheduleInfo);

        // then
        assertEquals(editScheduleInfo.getWorkingPartTime(), "1T");
    }

    @Test
    @DisplayName("스케줄 삭제 성공")
    public void testScheduleService_delete() {
        // given
        int employeeCode = 18;

        // when
        scheduleService.deleteSchedule(employeeCode);
        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = scheduleService.findScheduleByEmployeeCode(employeeCode);

        // then
        assertTrue(employeeWorkingPartDTO.isEmpty());
    }
}
