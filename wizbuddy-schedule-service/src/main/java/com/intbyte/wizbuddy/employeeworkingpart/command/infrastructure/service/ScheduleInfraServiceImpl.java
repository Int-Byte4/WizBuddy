package com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.application.service.EmployeeWorkingPartService;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.weeklyschedule.query.dto.WeeklyScheduleDTO;

import com.intbyte.wizbuddy.weeklyschedule.query.service.WeeklyScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleInfraServiceImpl implements ScheduleInfraService{

    private final WeeklyScheduleService weeklyScheduleService;
    private final EmployeeWorkingPartService employeeWorkingPartService;


    @Override
    public WeeklyScheduleDTO findScheduleByScheduleCode(int scheduleCode){
        return weeklyScheduleService.findScheduleByScheduleCode(scheduleCode);
    }

    @Override
    public EmployeeWorkingPart getEmployeeWorkingPartCode(int workingPartCode) {
        return employeeWorkingPartService.findEmployeeWorkingPartCode(workingPartCode);
    }
}
