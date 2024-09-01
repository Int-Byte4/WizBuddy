package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleCodeDuplicateException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
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
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;


    @Autowired
    public ScheduleService(ModelMapper mapper
            , WeeklyScheduleMapper weeklyScheduleMapper
            , WeeklyScheduleRepository weeklyScheduleRepository
            , EmployeeWorkingPartMapper employeeWorkingPartMapper
            ,EmployeeWorkingPartRepository employeeWorkingPartRepository) {
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
    @Transactional
    public List<EmployeeWorkingPartDTO> findSchedule(int scheduleCode) {
        WeeklySchedule weeklySchedule =  weeklyScheduleRepository.findById(scheduleCode)
                .orElseThrow(ScheduleNotFoundException::new);

        validateRequest(weeklySchedule);

        return employeeWorkingPartMapper.selectSchedule(weeklySchedule.getScheduleCode());
    }

    // 직원코드로 직원별 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPartDTO> findScheduleByEmployeeCode(int employeeCode) {
        EmployeeWorkingPartDTO foundCode = employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode);

        if (foundCode == null || foundCode.getEmployeeCode() != employeeCode)
            throw new EmployeeCodeNotFoundException();

        return employeeWorkingPartMapper.selectScheduleByEmployeeCode(employeeCode);
    }

    // 한주의 스케줄 등록
    // 1. 근무일정 등록
    @Transactional
    public WeeklyScheduleDTO registWeeklySchedule(WeeklyScheduleDTO weeklySchedule) {
        WeeklySchedule insertWeeklySchedule =
                new WeeklySchedule(weeklySchedule.getScheduleCode()
                        , weeklySchedule.isScheduleFlag()
                        , weeklySchedule.getScheduleStartDate()
                        , weeklySchedule.getCreatedAt()
                        , weeklySchedule.getUpdatedAt());
        if (weeklyScheduleMapper.findScheduleByStartDate(weeklySchedule.getScheduleStartDate()) != null)
            throw new ScheduleCodeDuplicateException();
        weeklyScheduleRepository.save(mapper.map(insertWeeklySchedule, WeeklySchedule.class));

        return weeklySchedule;
    }

    // 2. 직원 배치
    @Transactional
    public EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart) {
        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode()
                , employeeWorkingPart.getEmployeeCode()
                , employeeWorkingPart.getScheduleCode()
                , employeeWorkingPart.getWorkingDate()
                , employeeWorkingPart.getWorkingPartTime());
        int employeeCode = employeeWorkingPart.getEmployeeCode();
        if(employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode) == null) throw new EmployeeCodeNotFoundException();

        employeeWorkingPartRepository.save(mapper.map(insertSchedulePerEmployee, EmployeeWorkingPart.class));

        return employeeWorkingPart;
    }


    // 근무일 수정
    @Transactional
    public void EditSchedule(int employeeCode, EditScheduleInfo editScheduleInfo) {
        EmployeeWorkingPart foundSchedule = employeeWorkingPartRepository.findById(employeeCode)
                .orElseThrow(EmployeeCodeNotFoundException::new);

        foundSchedule.modify(editScheduleInfo);

        employeeWorkingPartRepository.save(foundSchedule);
    }

    // 근무일정 삭제
    @Transactional
    public void deleteSchedule(int employeeCode) {
        boolean exists = employeeWorkingPartRepository.existsById(employeeCode);

        if(!exists) throw new EmployeeCodeNotFoundException();

        employeeWorkingPartRepository.deleteById(employeeCode);
    }

//    // 대타게시글에 달린 댓글을 선택해서 근무일정 수정하기
//    @Transactional
//    public void selectCommentToEdit() {
//
//    }




    private void validateRequest(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.isScheduleFlag()) {
            throw new ScheduleNotFoundException();
        }
    }
}
