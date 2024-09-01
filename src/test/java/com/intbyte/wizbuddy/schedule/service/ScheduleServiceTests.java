package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import jakarta.transaction.Transactional;
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
@Transactional
class ScheduleServiceTests {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeWorkingPartRepository employeeWorkingPartRepository;

    private EmployeeWorkingPartDTO employeeWorkingPartDTO;

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
        employeeWorkingPartList.forEach(System.out::println);
    }

    @Test
    @DisplayName("직원별 스케줄 조회 성공")
    public void testScheduleService_select_employee_SuccessTest() {
        // given
        int employeeCode = 6;

        // when
        List<EmployeeWorkingPartDTO> employeeWorkPartList = scheduleService.findScheduleByEmployeeCode(employeeCode);

        // then
        assertNotNull(employeeWorkPartList);
        employeeWorkPartList.forEach(System.out::println);
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
        WeeklyScheduleDTO savedSchedule = scheduleService.registWeeklySchedule(weeklyScheduleDTO);

        // then
        assertNotNull(savedSchedule);
    }

    @Test
    @DisplayName("직원 근무배치 성공")
    public void testScheduleService_insert_employee_SuccessTest() {
        // given
        EmployeeWorkingPartDTO employeeWorkingPartDTO = new EmployeeWorkingPartDTO(19,
                8,
                1,
                LocalDateTime.now(),
                "1T");

        // when
        EmployeeWorkingPartDTO savedEmployeeWorkingPart = scheduleService
                .registSchedulePerEmployee(employeeWorkingPartDTO);

        // then
        assertNotNull(savedEmployeeWorkingPart);
    }

    @Test
    @DisplayName("스케줄 수정 성공")
    public void testScheduleService_update_SuccessTest() {
        // given
        int employeeCode = 6;
        EditScheduleInfo editScheduleInfo = new EditScheduleInfo(employeeCode, "1T");

        // when
        scheduleService.EditSchedule(employeeCode, editScheduleInfo);

        // then
        assertEquals(editScheduleInfo.getWorkingPartTime(), "1T");
    }

    @Test
    @DisplayName("스케줄 삭제 성공")
    public void testScheduleService_delete_SuccessTest() {
        // given
        int employeeCode = 6;

        // when
        scheduleService.deleteSchedule(employeeCode);

        // then
        boolean isDeleted = !employeeWorkingPartRepository.existsById(employeeCode);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("대타 게시판에 달린 댓글 선택해서 근무일정 수정 성공")
    public void testScheduleService_update_By_Comment_SuccessTest() {
        // given
        int subsCode = 1;
        int commentCode = 3;

        // when
        scheduleService.selectCommentToEdit(subsCode, commentCode);

        // then
        assertDoesNotThrow(() -> {
            scheduleService.selectCommentToEdit(subsCode, commentCode);
        });

    }

//    @Test
//    @DisplayName("한주의 스케줄 조회 실패")
//    public void testScheduleService_select_per_employee_ExceptionTest() {
//        // given
//        int scheduleCode = 200;
//
//        // when
//        List<EmployeeWorkingPartDTO> employeeWorkingPartList = scheduleService.findSchedule(scheduleCode);
//
//        // then
//        assertNotNull(employeeWorkingPartList);
//        employeeWorkingPartList.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("직원별 스케줄 조회 실패")
//    public void testScheduleService_select_employee_ExceptionTest() {
//        // given
//        int employeeCode = 200;
//
//        // when
//        List<EmployeeWorkingPartDTO> employeeWorkPartList = scheduleService.findScheduleByEmployeeCode(employeeCode);
//
//        // then
//        assertNotNull(employeeWorkPartList);
//        employeeWorkPartList.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("스케줄 등록 실패")
//    public void testScheduleService_insert_ExceptionTest() {
//        // given
//        WeeklyScheduleDTO weeklyScheduleDTO = new WeeklyScheduleDTO(1, true, LocalDate.of(2024,8,26), LocalDateTime.now(), LocalDateTime.now());
//
//        // when
//        WeeklyScheduleDTO savedSchedule = scheduleService.registWeeklySchedule(weeklyScheduleDTO);
//
//        // then
//        assertNotNull(savedSchedule);
//    }
//
//    @Test
//    @DisplayName("직원 근무배치 실패")
//    public void testScheduleService_insert_employee_ExceptionTest() {
//        // given
//        EmployeeWorkingPartDTO employeeWorkingPartDTO = new EmployeeWorkingPartDTO(19, 100, 1, LocalDateTime.now(), "1T");
//
//        // when
//        EmployeeWorkingPartDTO savedEmployeeWorkingPart = scheduleService.registSchedulePerEmployee(employeeWorkingPartDTO);
//
//        // then
//        assertNotNull(savedEmployeeWorkingPart);
//    }
//
//    @Test
//    @DisplayName("스케줄 수정 실패")
//    public void testScheduleService_update_ExceptionTest() {
//        // given
//        int employeeCode = 100;
//        EditScheduleInfo editScheduleInfo = new EditScheduleInfo(100, "1T");
//
//        // when
//        scheduleService.EditSchedule(employeeCode, editScheduleInfo);
//
//        // then
//        assertEquals(editScheduleInfo.getWorkingPartTime(), "1T");
//    }
//
//    @Test
//    @DisplayName("스케줄 삭제 실패")
//    public void testScheduleService_delete_ExceptionTest() {
//        // given
//        int employeeCode = 100;
//
//        // when
//        scheduleService.deleteSchedule(employeeCode);
//
//        // then
//        boolean isDeleted = !employeeWorkingPartRepository.existsById(employeeCode);
//        assertTrue(isDeleted);
//    }
}
