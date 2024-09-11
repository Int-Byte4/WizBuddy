package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service;

import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

import com.intbyte.wizbuddy.weeklyschedule.query.service.WeeklyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleInfraServiceImpl implements ScheduleInfraService{

    private WeeklyScheduleService weeklyScheduleService;

    @Autowired
    public ScheduleInfraServiceImpl(WeeklyScheduleService weeklyScheduleService) {
        this.weeklyScheduleService = weeklyScheduleService;
    }

    @Override
    public WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode){
        return weeklyScheduleService.findScheduleByScheduleCode(scheduleCode);
    }
}
