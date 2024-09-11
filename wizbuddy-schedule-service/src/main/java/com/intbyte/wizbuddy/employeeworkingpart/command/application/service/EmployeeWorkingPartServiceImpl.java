package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseRegistEmployeeVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.employeeworkingpart.common.exception.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.employeeworkingpart.common.exception.WorkingDateAndTimeEqualsException;
import com.intbyte.wizbuddy.employeeworkingpart.common.exception.ScheduleNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeWorkingPartServiceImpl implements EmployeeWorkingPartService{

    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Override
    @Transactional
    public ResponseRegistEmployeeVO registSchedulePerEmployee
            (ResponseRegistEmployeeVO responseRegistEmployeeVO) {

        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(responseRegistEmployeeVO.getWorkingPartCode()
                , responseRegistEmployeeVO.getEmployeeCode()
                , responseRegistEmployeeVO.getScheduleCode()
                , responseRegistEmployeeVO.getWorkingDate()
                , responseRegistEmployeeVO.getWorkingPartTime());

        String employeeCode = responseRegistEmployeeVO.getEmployeeCode();

        if(employeeWorkingPartRepository.findByEmployeeCode(employeeCode)==null)
            throw new EmployeeCodeNotFoundException();

        if(employeeWorkingPartRepository
                .existsByWorkingDateAndWorkingPartTime(insertSchedulePerEmployee
                        .getWorkingDate(),insertSchedulePerEmployee
                        .getWorkingPartTime()))
            throw new WorkingDateAndTimeEqualsException();

        employeeWorkingPartRepository.save(insertSchedulePerEmployee);

        return responseRegistEmployeeVO;
    }

    @Override
    @Transactional
    public void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        List<EmployeeWorkingPart> actualSchedule = employeeWorkingPartRepository
                .findByEmployeeCode(responseModifyScheduleVO.getEmployeeCode());
        actualSchedule.stream()
                .filter(schedule -> employeeWorkingPartRepository
                        .existsByWorkingDateAndWorkingPartTime(schedule
                                .getWorkingDate(),schedule
                                .getWorkingPartTime()))
                .findFirst()
                .orElseThrow(WorkingDateAndTimeEqualsException::new);

        employeeWorkingPart.modify(responseModifyScheduleVO);

        employeeWorkingPartRepository.save(employeeWorkingPart);
    }

    @Override
    @Transactional
    public void deleteSchedule(int workingPartCode) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        employeeWorkingPartRepository.delete(employeeWorkingPart);
    }
}
