package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

public interface ScheduleInfraService {

    WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode);
}
