package com.intbyte.wizbuddy.employeeworkingpart.query.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
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

        // 예외처리1. 존재하지 않는 스케줄일 경우
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

        // 예외처리1. 존재하지 않는 직원일 경우
        if (employeeWorkingPartDTO.isEmpty()) throw new CommonException(StatusEnum.EMPLOYEE_CODE_NOT_FOUND);

        return employeeWorkingPartDTO.stream()
                .map(employeeWorkingPart -> modelMapper.map(employeeWorkingPart, EmployeeWorkingPartVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeWorkingPartDTO findEmployeeWorkingPartCode(int workingPartCode) { // 워킹파트코드 조회
        EmployeeWorkingPartDTO employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByWorkingPartCode(workingPartCode);
        if (employeeWorkingPart == null) {
            throw new CommonException(StatusEnum.INVALID_EMPLOYEE_WORKING_PART_DATA);
        }
        return employeeWorkingPart;
    }

    @Override
    public EmployeeWorkingPart validateWriterWorkingPart(SubsBoardDTO subsBoard) {
        EmployeeWorkingPart writer = employeeWorkingPartMapper.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (writer == null) {
            throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);
        }
        return writer;
    }

    @Override
    public EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer) {
        List<EmployeeWorkingPart> commentAuthorParts = employeeWorkingPartMapper.findByEmployeeCode(comment.getEmployeeCode());
        if (commentAuthorParts == null || commentAuthorParts.isEmpty()) {
            throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);
        }


        return commentAuthorParts.stream()
                .filter(author -> employeeWorkingPartMapper.existsByWorkingDateAndWorkingPartTime(author.getWorkingDate(),author.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(() -> new CommonException(StatusEnum.WORKING_DATE_AND_TIME_EQUALS));
    }
}
