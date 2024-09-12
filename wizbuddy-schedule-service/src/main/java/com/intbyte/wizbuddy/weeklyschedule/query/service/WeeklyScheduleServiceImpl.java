package com.intbyte.wizbuddy.weeklyschedule.query.service;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.weeklyschedule.query.repository.WeeklyScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("queryWeeklyScheduleService")
@RequiredArgsConstructor
@Slf4j
public class WeeklyScheduleServiceImpl implements WeeklyScheduleService {

    private final WeeklyScheduleMapper weeklyScheduleMapper;

    @Override
    @Transactional
    public List<WeeklyScheduleDTO> findAllSchedules() {
        return weeklyScheduleMapper.selectAllSchedules();
    }

    @Override
    public WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode) {
        return weeklyScheduleMapper.selectScheduleByScheduleCode(scheduleCode);
    }
}
