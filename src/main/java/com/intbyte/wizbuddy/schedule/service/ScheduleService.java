package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.exception.weeklyschedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.WeeklyScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ModelMapper mapper;
    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;


    @Autowired
    public ScheduleService(ModelMapper mapper, WeeklyScheduleMapper weeklyScheduleMapper, WeeklyScheduleRepository weeklyScheduleRepository, EmployeeWorkingPartMapper employeeWorkingPartMapper) {
        this.mapper = mapper;
        this.weeklyScheduleMapper = weeklyScheduleMapper;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.employeeWorkingPartMapper = employeeWorkingPartMapper;
    }

    // 일주일 근무일정 전체 조회  -> 사장은 전체 조회만?
    @Transactional
    public List<WeeklyScheduleDTO> findAllSchedules() {
//        List<WeeklyScheduleDTO> schedules = weeklyScheduleMapper.selectAllSchedules();
//        return schedules;
        return weeklyScheduleMapper.selectAllSchedules();
    }

    // 한주의 근무일정 조회
    // -> 스케줄코드로 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPartDTO> findSchedule(int scheduleCode) {
        WeeklySchedule weeklySchedule =  weeklyScheduleRepository.findById(scheduleCode).orElseThrow(ScheduleNotFoundException::new);

        validateRequest(weeklySchedule);

        return employeeWorkingPartMapper.selectSchedule(weeklySchedule.getScheduleCode());
    }

    // 한주의 스케줄 작성
    // 1. 근무일정 등록
    @Transactional
    public WeeklyScheduleDTO registWeeklySchedule(WeeklyScheduleDTO weeklySchedule) {
        WeeklySchedule insertWeeklySchedule = new WeeklySchedule(weeklySchedule.getScheduleCode(),weeklySchedule.isScheduleFlag(),weeklySchedule.getScheduleStartDate(),weeklySchedule.getCreatedAt(),weeklySchedule.getUpdatedAt());
        weeklyScheduleRepository.save(mapper.map(insertWeeklySchedule, WeeklySchedule.class));
        return weeklySchedule;
    }

    // 2. 직원 배치? 근무일정이 등록되고난후에 직원이 배치되는 개념
//    @Transactional
//    public EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart) {
//        EmployeeWorkingPart insertSchedulePerEmployee = new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode(),employeeWorkingPart.getWorkingDate(),employeeWorkingPart.getWorkingPartTime(),employeeWorkingPart.getEmployeeCode(),);
//    }

    private void validateRequest(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.isScheduleFlag()) {
            throw new ScheduleNotFoundException();
        }
    }
}
