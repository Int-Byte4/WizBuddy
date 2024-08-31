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
    @Autowired
    private EmployeeWorkingPartMapper employeeWorkingPartMapper;


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
    @DisplayName("직원별 스케줄 조회 성공")
    public void testScheduleService_select_employee() {
        List<EmployeeWorkingPartDTO> employeeWorkPartList = scheduleService.findScheduleByEmployeeCode(8);

        assertNotNull(employeeWorkPartList);
        employeeWorkPartList.forEach(System.out::println);
    }

    @Test
    @DisplayName("스케줄 등록 성공")
    public void testScheduleService_insert() {
        WeeklyScheduleDTO weeklyScheduleDTOList = scheduleService.registWeeklySchedule(new WeeklyScheduleDTO(4, true, new Date(), LocalDateTime.now(), LocalDateTime.now()));
        assertNotNull(weeklyScheduleDTOList);
    }

//    @Test
//    @DisplayName("직원 근무배치 성공")
//    public void testScheduleService_insert_employee() {
//        EmployeeWorkingPartDTO employeeWorkingPartDTOList = scheduleService.registSchedulePerEmployee(new EmployeeWorkingPartDTO(19, 3, 2, LocalDateTime.now(), "1T"));
//        assertNotNull(employeeWorkingPartDTOList);
//    }

    @Test
    @DisplayName("스케줄 수정 성공")
    public void testScheduleService_update() {
        int employeeCode = 18;
        EditScheduleInfo editScheduleInfo = new EditScheduleInfo(8, "1T");   // 코드, 파트타임이 들어감 employeeCode / workingDate / workingPartTime

        scheduleService.EditSchedule(employeeCode, editScheduleInfo);

        assertEquals(editScheduleInfo.getWorkingPartTime(),"1T");
    }

    @Test
    @DisplayName("스케줄 삭제 성공")
    public void testScheduleService_delete() {
        int employeeCode = 18;
        scheduleService.deleteSchedule(employeeCode);
        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = scheduleService.findScheduleByEmployeeCode(18);
        assertTrue(employeeWorkingPartDTO.isEmpty());
    }










}