package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.exception.weeklyschedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.schedule.repository.WeeklyScheduleRepository;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ModelMapper mapper;
    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;


    @Autowired
    public ScheduleService(ModelMapper mapper, WeeklyScheduleMapper weeklyScheduleMapper, WeeklyScheduleRepository weeklyScheduleRepository, EmployeeWorkingPartMapper employeeWorkingPartMapper, @Qualifier("employeeWorkingPartRepository") EmployeeWorkingPartRepository employeeWorkingPartRepository) {
        this.mapper = mapper;
        this.weeklyScheduleMapper = weeklyScheduleMapper;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.employeeWorkingPartMapper = employeeWorkingPartMapper;
        this.employeeWorkingPartRepository = employeeWorkingPartRepository;
    }

    // 근무일정 전체 조회
    @Transactional
    public List<WeeklyScheduleDTO> findAllSchedules() {
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

    // 직원코드로 직원별 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPartDTO> findScheduleByEmployeeCode(int employeeCode) {
        return employeeWorkingPartMapper.selectScheduleByEmployeeCode(employeeCode);
    }

    // 한주의 스케줄 작성
    // 1. 근무일정 등록
    @Transactional
    public WeeklyScheduleDTO registWeeklySchedule(WeeklyScheduleDTO weeklySchedule) {
        WeeklySchedule insertWeeklySchedule = new WeeklySchedule(weeklySchedule.getScheduleCode(),weeklySchedule.isScheduleFlag(),weeklySchedule.getScheduleStartDate(),weeklySchedule.getCreatedAt(),weeklySchedule.getUpdatedAt());
        weeklyScheduleRepository.save(mapper.map(insertWeeklySchedule, WeeklySchedule.class));
        return weeklySchedule;
    }

//    // 2. 직원 배치? 근무일정이 등록되고난후에 직원이 배치되는 개념 weeklySchedule에 마지막으로 추가된 스케줄코드 가져와야됨 근데? 직원도 있어야됨 ㅇㅇ 직원 코드도 가져와야되네
//    @Transactional
//    public EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart) {
//        WeeklySchedule lastScheduleCode = weeklyScheduleMapper.selectLastScheduleCode();
//
//        if (lastScheduleCode == null) {
//            throw new IllegalStateException("마지막으로 추가된 스케줄을 찾을 수 없습니다.");
//        }
//
//        EmployeeWorkingPart insertSchedulePerEmployee = new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode(), employeeWorkingPart.getEmployeeCode(), lastScheduleCode.getScheduleCode(),employeeWorkingPart.getWorkingDate(), employeeWorkingPart.getWorkingPartTime());
//
//        weeklyScheduleRepository.save(mapper.map(insertSchedulePerEmployee, WeeklySchedule.class));
//
//        return employeeWorkingPart;
//    }

    // 근무일 수정 - 직원번호 받으면 해당 직원의 근무일을 수정할 수 있음
    @Transactional
    public void EditSchedule(int employeeCode, EditScheduleInfo editScheduleInfo) {
        EmployeeWorkingPart foundSchedule = employeeWorkingPartRepository.findById(employeeCode).orElseThrow(ScheduleNotFoundException::new);
        foundSchedule.modify(editScheduleInfo);
        employeeWorkingPartRepository.save(foundSchedule);
        // 직원코드, 근무일, 근무파트 수정 가능 -> 직원코드가 존재하는지(존재하지 않는다면 예외처리)
    }

//     근무일정 삭제 - 직원번호로 삭제하기
    @Transactional
    public void deleteSchedule(int employeeCode) {
        employeeWorkingPartRepository.deleteById(employeeCode);
    }






    private void validateRequest(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.isScheduleFlag()) {
            throw new ScheduleNotFoundException();
        }
    }
}
