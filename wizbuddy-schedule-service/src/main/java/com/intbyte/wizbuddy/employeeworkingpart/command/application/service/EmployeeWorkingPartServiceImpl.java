package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.application.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.employeeworkingpart.common.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.employeeworkingpart.common.WorkingDateAndTimeEqualsException;
import com.intbyte.wizbuddy.employeeworkingpart.common.ScheduleNotFoundException;
import com.intbyte.wizbuddy.employeeworkingpart.query.repository.EmployeeWorkingPartMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeWorkingPartServiceImpl implements EmployeeWorkingPartService{

    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Transactional
    public EmployeeWorkingPartDTO registSchedulePerEmployee(EmployeeWorkingPartDTO employeeWorkingPart) {

        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(employeeWorkingPart.getWorkingPartCode()
                , employeeWorkingPart.getEmployeeCode()
                , employeeWorkingPart.getScheduleCode()
                , employeeWorkingPart.getWorkingDate()
                , employeeWorkingPart.getWorkingPartTime());

        String employeeCode = employeeWorkingPart.getEmployeeCode();

        if(employeeWorkingPartMapper
                .findEmployeeByEmployeeCode(employeeCode) == null)
            throw new EmployeeCodeNotFoundException();

        if(Objects.equals(insertSchedulePerEmployee.getWorkingDate()
                ,employeeWorkingPart.getWorkingDate())
        && Objects.equals(insertSchedulePerEmployee.getWorkingPartTime()
                , employeeWorkingPart.getWorkingPartTime()))
            throw new WorkingDateAndTimeEqualsException();

        employeeWorkingPartRepository.save(insertSchedulePerEmployee);

        return employeeWorkingPart;
    }

    @Transactional
    public void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        List<EmployeeWorkingPart> actualSchedule = employeeWorkingPartRepository
                .findByEmployeeCode(responseModifyScheduleVO.getEmployeeCode());
        actualSchedule.stream()
                .filter(schedule -> Objects.equals(schedule.getWorkingDate()
                        , employeeWorkingPart.getWorkingDate())
                        && Objects.equals(schedule.getWorkingPartTime()
                        ,employeeWorkingPart.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(WorkingDateAndTimeEqualsException::new);

        employeeWorkingPart.modify(responseModifyScheduleVO);

        employeeWorkingPartRepository.save(employeeWorkingPart);
    }

    @Transactional
    public void deleteSchedule(int workingPartCode) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        employeeWorkingPartRepository.delete(employeeWorkingPart);
    }
}
