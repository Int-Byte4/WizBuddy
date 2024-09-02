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
    public List<EmployeeWorkingPart> findSchedule(int scheduleCode) {
        WeeklySchedule weeklySchedule =  weeklyScheduleRepository.findById(scheduleCode)
                .orElseThrow(ScheduleNotFoundException::new);

        validateRequest(weeklySchedule);

        return employeeWorkingPartMapper.selectScheduleByScheduleCode(weeklySchedule.getScheduleCode());
    }

    // 직원코드로 직원별 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPart> findScheduleByEmployeeCode(String employeeCode) {
        EmployeeWorkingPart foundCode = employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode);

        if (foundCode == null) throw new EmployeeCodeNotFoundException();

        return employeeWorkingPartMapper.selectAllScheduleByEmployeeCode(employeeCode);
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
    public void registSchedulePerEmployee(EmployeeWorkingPart employeeWorkingPart) {
        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode()
                , employeeWorkingPart.getEmployeeCode()
                , employeeWorkingPart.getScheduleCode()
                , employeeWorkingPart.getWorkingDate()
                , employeeWorkingPart.getWorkingPartTime());
        String employeeCode = employeeWorkingPart.getEmployeeCode();
        if(employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode) == null) throw new EmployeeCodeNotFoundException();

        employeeWorkingPartRepository.save(mapper.map(insertSchedulePerEmployee, EmployeeWorkingPart.class));
    }


    // 근무일 수정
    @Transactional
    public void EditSchedule(int workingPartCode, EditScheduleInfo modifyScheduleInfo) {
        EmployeeWorkingPart foundSchedule = employeeWorkingPartMapper.selectScheduleByWorkingPartCode(workingPartCode);

        if (foundSchedule == null) throw new ScheduleNotFoundException();

        foundSchedule.modify(modifyScheduleInfo);
        employeeWorkingPartRepository.save(foundSchedule);
    }


    // 근무일정 삭제
    @Transactional
    public void deleteSchedule(int workingPartCode) {
        EmployeeWorkingPart foundCode = employeeWorkingPartMapper.selectScheduleByWorkingPartCode(workingPartCode);

        if (foundCode == null) throw new ScheduleNotFoundException();

        employeeWorkingPartRepository.delete(foundCode);
    }

//    // 대타게시글에 달린 댓글을 선택해서 근무일정 수정하기
//    @Transactional
//    public void selectCommentToEdit(int subsCode, int commentCode) {
//        int foundSchedule = employeeWorkingPartMapper.updateScheduleByComment(subsCode, commentCode);
//        if (foundSchedule == 0) {
////            // 예외처리
//        }
//
//    }




    private void validateRequest(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.isScheduleFlag()) {
            throw new ScheduleNotFoundException();
        }
    }
}
