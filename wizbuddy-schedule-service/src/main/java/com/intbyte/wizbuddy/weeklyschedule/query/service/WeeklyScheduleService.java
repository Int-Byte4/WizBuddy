package com.intbyte.wizbuddy.weeklyschedule.query.service;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

import java.util.List;

public interface WeeklyScheduleService {
    List<WeeklyScheduleDTO> findAllSchedules();
    WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode);
}
