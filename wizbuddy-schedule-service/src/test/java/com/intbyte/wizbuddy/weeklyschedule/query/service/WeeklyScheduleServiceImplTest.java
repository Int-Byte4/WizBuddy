package com.intbyte.wizbuddy.weeklyschedule.query.service;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class WeeklyScheduleServiceImplTest {

    @Autowired
    private WeeklyScheduleServiceImpl weeklyScheduleService;

    @Test
    @DisplayName("전체 스케줄 조회 성공")
    void findAllSchedules() {
        // given
        // when
        List<WeeklyScheduleDTO> weeklyScheduleDTOList = weeklyScheduleService.findAllSchedules();

        // then
        Assertions.assertNotNull(weeklyScheduleDTOList);
        weeklyScheduleDTOList.forEach(System.out::println);

    }
}