package com.intbyte.wizbuddy.employeeworkingpart.command.application.service;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseRegistEmployeeVO;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.dto.EmployeeDTO;
import com.intbyte.wizbuddy.employeeworkingpart.command.infrastructure.service.ScheduleInfraService;
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
    private final UserServiceClient userServiceClient;
    private final ScheduleInfraService scheduleInfraService;

    @Override
    @Transactional
    public ResponseRegistEmployeeVO registSchedulePerEmployee
            (ResponseRegistEmployeeVO responseRegistEmployeeVO) {

        EmployeeDTO employeeDTO = userServiceClient.getEmployee(responseRegistEmployeeVO.getEmployeeCode()).getBody();
        int scheduleCode = scheduleInfraService.findScheduleByScheduleCode(responseRegistEmployeeVO
                .getScheduleCode()).getScheduleCode();

        EmployeeWorkingPart insertSchedulePerEmployee =
                new EmployeeWorkingPart(responseRegistEmployeeVO.getWorkingPartCode()
                , employeeDTO.getEmployeeCode()
                , scheduleCode
                , responseRegistEmployeeVO.getWorkingDate()
                , responseRegistEmployeeVO.getWorkingPartTime());

        // 예외처리1. 존재하지 않는 직원일 경우
        if(employeeWorkingPartRepository
                .findByEmployeeCode(responseRegistEmployeeVO.getEmployeeCode())==null)
            throw new CommonException(StatusEnum.EMPLOYEE_CODE_NOT_FOUND);

        // 예외처리2. 같은날, 같은 시간(예 - 월요일 2타임에 2명(A,B)의 알바생이 근무하는 경우) A의 대타로 B를 지정하려는 경우
        if(employeeWorkingPartRepository
                .existsByWorkingDateAndWorkingPartTime(insertSchedulePerEmployee
                        .getWorkingDate(),insertSchedulePerEmployee
                        .getWorkingPartTime()))
            throw new CommonException(StatusEnum.WORKING_DATE_AND_TIME_EQUALS);

        employeeWorkingPartRepository.save(insertSchedulePerEmployee);

        return responseRegistEmployeeVO;
    }

    @Override
    @Transactional
    public void editSchedule(int workingPartCode, ResponseModifyScheduleVO responseModifyScheduleVO) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(workingPartCode);
        // 예외처리1. 존재하지 않는 스케줄일 경우
        if(employeeWorkingPart==null) throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);

        // 예외처리2. 존재하지 않는 직원일 경우
        if (employeeWorkingPart.getEmployeeCode() == null) throw new CommonException(StatusEnum.EMPLOYEE_CODE_NOT_FOUND);

        // 예외처리3. 같은날, 같은 시간(예 - 월요일 2타임에 2명(A,B)의 알바생이 근무하는 경우)에 근무하는 직원으로 수정하려는 경우
        List<EmployeeWorkingPart> subsSchedule = employeeWorkingPartRepository
                .findByEmployeeCode(responseModifyScheduleVO.getEmployeeCode());
        subsSchedule.stream()
                .filter(schedule -> employeeWorkingPartRepository
                        .existsByWorkingDateAndWorkingPartTime(
                                schedule.getWorkingDate(),
                                schedule.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(() -> new CommonException(StatusEnum.WORKING_DATE_AND_TIME_EQUALS));

        employeeWorkingPart.modify(responseModifyScheduleVO);

        employeeWorkingPartRepository.save(employeeWorkingPart);
    }

    @Override
    @Transactional
    public void deleteSchedule(int workingPartCode) {
        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(workingPartCode);

        // 예외처리1. 존재하지 않는 스케줄일 경우
        if (employeeWorkingPart == null) throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);

        employeeWorkingPartRepository.delete(employeeWorkingPart);
    }

    @Override
    public EmployeeWorkingPart validateWriterWorkingPart(SubsBoard subsBoard) {
        EmployeeWorkingPart writer = employeeWorkingPartRepository.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (writer == null) {
            throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);
        }
        return writer;
    }

    @Override
    public EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer) {
        List<EmployeeWorkingPart> commentAuthorParts = employeeWorkingPartRepository.findByEmployeeCode(comment.getEmployeeCode());
        if (commentAuthorParts == null || commentAuthorParts.isEmpty()) {
            throw new CommonException(StatusEnum.SCHEDULE_NOT_FOUND);
        }


        return commentAuthorParts.stream()
                .filter(author -> employeeWorkingPartRepository.existsByWorkingDateAndWorkingPartTime(author.getWorkingDate(),author.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(() -> new CommonException(StatusEnum.WORKING_DATE_AND_TIME_EQUALS));
    }

    @Override
    public void updateWorkingPart(EmployeeWorkingPart writer, EmployeeWorkingPart matchingCommentAuthor) {
        writer.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
        employeeWorkingPartRepository.save(writer);
    }
}
