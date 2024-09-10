package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.application.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository.EmployeeWorkingPartRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeWorkingPartServiceImplTests {

    @Autowired
    private EmployeeWorkingPartService employeeWorkingPartService;
    @Autowired
    private EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Test
    @DisplayName("직원 근무배치 성공")
    void registSchedulePerEmployee() {
        // given
        List<EmployeeWorkingPart> currentEmployeeWorkingPartList = employeeWorkingPartRepository.findAll();
        int currentSize = currentEmployeeWorkingPartList.size();

        EmployeeWorkingPartDTO employeeWorkingPart = new EmployeeWorkingPartDTO(19,
                "20240831-cc00-4288-b2a6-2f864ddbf6b5",
                1,
                LocalDateTime.now(),
                "1T");

        // when
        employeeWorkingPartService.registSchedulePerEmployee(employeeWorkingPart);

        // then
        List<EmployeeWorkingPart> newEmployeeWorkingPartList = employeeWorkingPartRepository.findAll();
        int newCurrentSize = newEmployeeWorkingPartList.size();

        assertEquals(currentSize + 1, newCurrentSize);
    }

    @Test
    @DisplayName("스케줄 수정 성공")
    void editSchedule() {
        // given
        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository.findById(1).get();
        String employeeCode = "20240831-07de-4b18-95c6-564cd86a5af2";

        ResponseModifyScheduleVO responseModifyScheduleVO =
                new ResponseModifyScheduleVO(employeeCode);
        // when
        employeeWorkingPartService.editSchedule(employeeWorkingPart.getWorkingPartCode(), responseModifyScheduleVO);

        // then
        assertEquals(employeeWorkingPart.getEmployeeCode(), responseModifyScheduleVO.getEmployeeCode());
    }

    @Test
    @DisplayName("스케줄 삭제 성공")
    void deleteSchedule() {
        // given
        int workingPartCode = 18;

        // when
        employeeWorkingPartService.deleteSchedule(workingPartCode);

        // then
        boolean isDeleted = !employeeWorkingPartRepository.existsById(workingPartCode);
        assertTrue(isDeleted);
    }
}