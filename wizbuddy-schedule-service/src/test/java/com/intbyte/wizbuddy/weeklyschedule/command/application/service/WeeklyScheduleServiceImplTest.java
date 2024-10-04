package com.intbyte.wizbuddy.weeklyschedule.command.application.service;

import com.intbyte.wizbuddy.weeklyschedule.command.application.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.response.ResponseRegistWeeklyScheduleVO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WeeklyScheduleServiceImplTest {

    @Autowired
    private WeeklyScheduleServiceImpl weeklyScheduleService;

    @Test
    @DisplayName("스케줄 등록 성공")
    void registSchedule() {
        // given
        ResponseRegistWeeklyScheduleVO responseRegistWeeklyScheduleVO =
                new ResponseRegistWeeklyScheduleVO(1,
                        true,
                        LocalDate.of(2024,9,26),
                        LocalDateTime.now(),
                        LocalDateTime.now());
        System.out.println(responseRegistWeeklyScheduleVO);

        // when
        ResponseRegistWeeklyScheduleVO savedSchedule = weeklyScheduleService
                .registSchedule(responseRegistWeeklyScheduleVO);

        // then
        assertNotNull(savedSchedule);
    }
}