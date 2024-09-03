package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleCodeDuplicateException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleNotFoundException;import com.intbyte.wizbuddy.exception.schedule.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.schedule.repository.WeeklyScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ModelMapper mapper;
    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;

    // 근무일정 전체 조회
    @Transactional
    public List<WeeklyScheduleDTO> findAllSchedules() {
        return weeklyScheduleMapper.selectAllSchedules();
    }

    // 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPartDTO> findSchedule(int scheduleCode) {
        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper.selectScheduleByScheduleCode(scheduleCode);
        return employeeWorkingPart.stream()
                .map(entity -> new EmployeeWorkingPartDTO(
                        entity.getWorkingPartCode(),
                        entity.getEmployeeCode(),
                        entity.getScheduleCode(),
                        entity.getWorkingDate(),
                        entity.getWorkingPartTime()
                ))
                .collect(Collectors.toList());
    }

    // 직원코드로 직원별 한주의 근무일정 조회
    @Transactional
    public List<EmployeeWorkingPartDTO> findScheduleByEmployeeCode(String employeeCode) {
        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode);
        return employeeWorkingPart.stream()
                .map(entity -> new EmployeeWorkingPartDTO(
                        entity.getWorkingPartCode(),
                        entity.getEmployeeCode(),
                        entity.getScheduleCode(),
                        entity.getWorkingDate(),
                        entity.getWorkingPartTime()
                ))
                .collect(Collectors.toList());
    }

    // 한주의 스케줄 등록
    // 1. 근무일정 등록
    @Transactional
    public WeeklyScheduleDTO registSchedule(WeeklyScheduleDTO weeklySchedule) {
        WeeklySchedule insertWeeklySchedule =
                new WeeklySchedule(
                          weeklySchedule.getScheduleCode()
                        , weeklySchedule.isScheduleFlag()
                        , weeklySchedule.getScheduleStartDate()
                        , weeklySchedule.getCreatedAt()
                        , weeklySchedule.getUpdatedAt());
        if (weeklyScheduleMapper.findScheduleByStartDate(weeklySchedule.getScheduleStartDate()) != null)
            throw new ScheduleCodeDuplicateException();

        weeklyScheduleRepository.save(insertWeeklySchedule);

        return weeklySchedule;
    }

    // 2. 직원 등록
    @Transactional
    public EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart) {
        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode()
                , employeeWorkingPart.getEmployeeCode()
                , employeeWorkingPart.getScheduleCode()
                , employeeWorkingPart.getWorkingDate()
                , employeeWorkingPart.getWorkingPartTime());
        String employeeCode = employeeWorkingPart.getEmployeeCode();
        if(employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode) == null) throw new EmployeeCodeNotFoundException();

        employeeWorkingPartRepository.save(insertSchedulePerEmployee);

        return employeeWorkingPart;
    }

    // 근무일 수정
    @Transactional
    public void editSchedule(int workingPartCode, EditScheduleInfo modifyScheduleInfo) {
        EmployeeWorkingPart foundSchedule = employeeWorkingPartMapper.selectScheduleByWorkingPartCode(workingPartCode);

        List<EmployeeWorkingPart> actualSchedule = employeeWorkingPartMapper.findEmployeeByEmployeeCode(modifyScheduleInfo.getEmployeeCode());

        log.info("WorkingPartCode1: {}", foundSchedule.getWorkingPartTime());
        log.info("WorkingPartCode2: {}", actualSchedule.get(0).getWorkingPartTime());

        if (foundSchedule == null) throw new ScheduleNotFoundException();
        if (!Objects.equals(actualSchedule.get(0).getWorkingPartTime(), foundSchedule.getWorkingPartTime())) {
            throw new WorkingPartCodeNotEqualsException();
        }

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
//        int foundSchedule = employeeWorkingPartMapper.selectScheduleByComment(subsCode, commentCode);
//        if (foundSchedule == 0) {
//            // 예외처리
//        }
//        System.out.println(foundSchedule);
//
//    }




    private void validateRequest(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.isScheduleFlag()) {
            throw new ScheduleNotFoundException();
        }
    }
}
