package com.intbyte.wizbuddy.employeeworkingpart.query.service;

import com.intbyte.wizbuddy.employeeworkingpart.query.vo.EmployeeWorkingPartVO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeWorkingPartServiceImplTests {

    @Autowired
    @Qualifier("queryEmployeeWorkingPartService")
    private EmployeeWorkingPartService employeeWorkingPartService;

    @Test
    @DisplayName("한주의 스케줄 조회 성공")
    void findSchedule() {
        // given
        int scheduleCode = 1;

        // when
        List<EmployeeWorkingPartVO> employeeWorkingPartList = employeeWorkingPartService
                .findSchedule(scheduleCode);

        // then
        assertNotNull(employeeWorkingPartList);
    }

    @Test
    @DisplayName("직원별 스케줄 조회 성공")
    void findScheduleByEmployeeCode() {
        // given
        String employeeCode = "20240831-07de-4b18-95c6-564cd86a5af2";

        // when
        List<EmployeeWorkingPartVO> employeeWorkPartList = employeeWorkingPartService
                .findScheduleByEmployeeCode(employeeCode);

        // then
        assertNotNull(employeeWorkPartList);
    }
}