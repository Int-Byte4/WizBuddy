package com.intbyte.wizbuddy.employeeworkingpart.query.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.employeeworkingpart.query.repository.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.employeeworkingpart.query.vo.EmployeeWorkingPartVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("queryEmployeeWorkingPartService")
@RequiredArgsConstructor
@Slf4j
public class EmployeeWorkingPartServiceImpl implements EmployeeWorkingPartService {

    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public List<EmployeeWorkingPartVO> findSchedule(int scheduleCode) {

        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = employeeWorkingPartMapper
                .selectScheduleByScheduleCode(scheduleCode);

        if (employeeWorkingPartDTO.isEmpty()) throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);

        return employeeWorkingPartDTO.stream()
                .map(employeeWorkingPart -> modelMapper.map(employeeWorkingPart, EmployeeWorkingPartVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EmployeeWorkingPartVO> findScheduleByEmployeeCode(String employeeCode) {

        List<EmployeeWorkingPartDTO> employeeWorkingPartDTO = employeeWorkingPartMapper
                .findEmployeeByEmployeeCode(employeeCode);

        if (employeeWorkingPartDTO.isEmpty()) throw new CommonException(StatusEnum.EMPLOYEE_CODE_NOT_FOUND);

        return employeeWorkingPartDTO.stream()
                .map(employeeWorkingPart -> modelMapper.map(employeeWorkingPart, EmployeeWorkingPartVO.class))
                .collect(Collectors.toList());
    }
}
