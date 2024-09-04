package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.schedule.info.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class ScheduleServiceTests {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Test
    @DisplayName("전체 스케줄 조회 성공")
    public void testScheduleService_select_SuccessTest() {
        // given
        // when
        List<WeeklyScheduleDTO> weeklyScheduleDTOList = scheduleService.findAllSchedules();

        // then
        Assertions.assertNotNull(weeklyScheduleDTOList);
        weeklyScheduleDTOList.forEach(System.out::println);
    }

    @Test
    @DisplayName("한주의 스케줄 조회 성공")
    public void testScheduleService_select_per_employee_SuccessTest() {
        // given
        int scheduleCode = 1;

        // when
        List<EmployeeWorkingPartDTO> employeeWorkingPartList = scheduleService.findSchedule(scheduleCode);

        // then
        assertNotNull(employeeWorkingPartList);
    }

    @Test
    @DisplayName("직원별 스케줄 조회 성공")
    public void testScheduleService_select_employee_SuccessTest() {
        // given
        String employeeCode = "20240831-07de-4b18-95c6-564cd86a5af2";

        // when
        List<EmployeeWorkingPartDTO> employeeWorkPartList = scheduleService.findScheduleByEmployeeCode(employeeCode);

        // then
        assertNotNull(employeeWorkPartList);
    }

    @Test
    @DisplayName("스케줄 등록 성공")
    public void testScheduleService_insert_SuccessTest() {
        // given
        WeeklyScheduleDTO weeklyScheduleDTO =
                new WeeklyScheduleDTO(1,
                        true,
                        LocalDate.of(2024,9,26),
                        LocalDateTime.now(),
                        LocalDateTime.now());

        // when
        WeeklyScheduleDTO savedSchedule = scheduleService.registSchedule(weeklyScheduleDTO);

        // then
        assertNotNull(savedSchedule);
    }

    @Test
    @DisplayName("직원 근무배치 성공")
    public void testScheduleService_insert_employee_SuccessTest() {
        // given
        List<EmployeeWorkingPart> currentEmployeeWorkingPartList = employeeWorkingPartRepository.findAll();
        int currentSize = currentEmployeeWorkingPartList.size();

        EmployeeWorkingPartDTO employeeWorkingPart = new EmployeeWorkingPartDTO(19,
                "20240831-cc00-4288-b2a6-2f864ddbf6b5",
                1,
                LocalDateTime.now(),
                "1T");

        // when
        scheduleService.registSchedulePerEmployee(employeeWorkingPart);

        // then
        List<EmployeeWorkingPart> newEmployeeWorkingPartList = employeeWorkingPartRepository.findAll();
        int newCurrentSize = newEmployeeWorkingPartList.size();

        assertEquals(currentSize + 1, newCurrentSize);
    }

    @Test
    @DisplayName("스케줄 수정 성공")
    public void testScheduleService_update_SuccessTest() {
        // given
        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository.findById(1).get();
        String employeeCode = "20240831-f409-40b1-a03d-4d14d52fa13a";
        EditScheduleInfo editScheduleInfo = new EditScheduleInfo(employeeCode);

        // when
        scheduleService.editSchedule(employeeWorkingPart.getWorkingPartCode(), editScheduleInfo);

        // then
        assertEquals(employeeWorkingPart.getEmployeeCode(), editScheduleInfo.getEmployeeCode());
    }

    @Test
    @DisplayName("스케줄 삭제 성공")
    public void testScheduleService_delete_SuccessTest() {
        // given
        int workingPartCode = 18;

        // when
        scheduleService.deleteSchedule(workingPartCode);

        // then
        boolean isDeleted = !employeeWorkingPartRepository.existsById(workingPartCode);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("댓글 채택해서 스케줄 수정")
    public void testScheduleService_update_schedule_SuccessTest() {
        scheduleService.editScheduleByComment(1
                , true
                , "20240831-1859-4c43-b692-b6cb5891c24a");
    }
}
