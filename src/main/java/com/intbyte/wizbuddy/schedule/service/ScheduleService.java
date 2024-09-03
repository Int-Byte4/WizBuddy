package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.exception.board.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleCodeDuplicateException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.SubsBoardMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.info.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.schedule.repository.WeeklyScheduleRepository;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final EmployeeWorkingPartMapper employeeWorkingPartMapper;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;
    private final SubsBoardRepository subsBoardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public List<WeeklyScheduleDTO> findAllSchedules() {
        return weeklyScheduleMapper.selectAllSchedules();
    }

    @Transactional
    public List<EmployeeWorkingPartDTO> findSchedule(int scheduleCode) {

        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper.selectScheduleByScheduleCode(scheduleCode);

        if (employeeWorkingPart.isEmpty()) throw new ScheduleNotFoundException();

        return employeeWorkingPart.stream()
                .map(entity -> new EmployeeWorkingPartDTO(
                        entity.getWorkingPartCode(),
                        entity.getEmployeeCode(),
                        entity.getScheduleCode(),
                        entity.getWorkingDate(),
                        entity.getWorkingPartTime()
                )).collect(Collectors.toList());
    }

    @Transactional
    public List<EmployeeWorkingPartDTO> findScheduleByEmployeeCode(String employeeCode) {

        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper.findEmployeeByEmployeeCode(employeeCode);

        if (employeeWorkingPart.isEmpty()) throw new EmployeeCodeNotFoundException();

        return employeeWorkingPart.stream()
                .map(entity -> new EmployeeWorkingPartDTO(
                        entity.getWorkingPartCode(),
                        entity.getEmployeeCode(),
                        entity.getScheduleCode(),
                        entity.getWorkingDate(),
                        entity.getWorkingPartTime()
                )).collect(Collectors.toList());
    }

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

    @Transactional
    public void editSchedule(int workingPartCode, EditScheduleInfo modifyScheduleInfo) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper.selectScheduleByWorkingPartCode(workingPartCode);

        List<EmployeeWorkingPart> actualSchedule = employeeWorkingPartMapper.findEmployeeByEmployeeCode(modifyScheduleInfo.getEmployeeCode());

        log.info("WorkingPartCode1: {}", employeeWorkingPart.getWorkingPartTime());
        log.info("WorkingPartCode2: {}", actualSchedule.get(0).getWorkingPartTime());

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        if (!Objects.equals(actualSchedule.get(0).getWorkingPartTime(), employeeWorkingPart.getWorkingPartTime())) {
            throw new WorkingPartCodeNotEqualsException();
        }

        employeeWorkingPart.modify(modifyScheduleInfo);

        employeeWorkingPartRepository.save(employeeWorkingPart);
    }

    @Transactional
    public void deleteSchedule(int workingPartCode) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper.selectScheduleByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        employeeWorkingPartRepository.delete(employeeWorkingPart);
    }

    @Transactional
    public void EditScheduleByEmployeeCode(int subsCode, boolean subsFlag, String employeeCode) {

        // 대타게시판에서 게시글 하나 선택 1번/true - 1번 게시글에 해당하는 직원의 직원 코드로 employeeworkingpart에서 workingparttime을 알아내야함
        SubsBoard subsBoard = subsBoardRepository.findBysubsCodeAndSubsFlag(subsCode, subsFlag);
        if (subsBoard == null) throw new SubsBoardNotFoundException();
        EmployeeWorkingPart writer = employeeWorkingPartRepository.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());

        // 그 게시글에 달린 댓글 중 하나 선택 - 그 댓글을 단 직원의 직원 코드로 employeeworkingpart에서 workingparttime을 알아낸 후 게시글 작성자(사장이지만 편의상 작성자로 칭함)와 댓글단 직원의 workingparttime을 비교 -> 같으면 진행, 다르면 예외처리
        Comment comment = commentRepository.findBySubsCodeAndEmployeeCode(subsCode,employeeCode);   // 근데 이렇게 하면 여러개가 나옴 이중에 하나 선택해야함 직원번호로 선택하면 됨 근데 그럼 리스트로 받아야하나?
        // 파라미터로 employeeCode도 받아야 한다 -> 근데 여러개가 조회될 가능성이 있다.(직원이 1T,2T,3T다 근무한다면? 그리고 그 주에 여러 날에 2T으로 들어갔다면? -> 그 중 하나만 있어도 그 타임에 근무하는거니까 1개로 제한)
        List<EmployeeWorkingPart> commentAuthor = employeeWorkingPartRepository.findByEmployeeCode(comment.getEmployeeCode());
//        if(!Objects.equals(writer.getWorkingPartTime(), commentAuthor.getWorkingPartTime())) throw new WorkingPartCodeNotEqualsException();
        EmployeeWorkingPart matchingCommentAuthor = commentAuthor.stream()
                .filter(author -> Objects.equals(author.getWorkingPartTime(), writer.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(WorkingPartCodeNotEqualsException::new);

        // subsBoard의 workingpartcode로 employeeWorkingPart에서 employeecode를 댓글단 직원의 것으로 업데이트 하면 완 성 !!!!!
        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();
        employeeWorkingPart.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
        employeeWorkingPartRepository.save(employeeWorkingPart);
    }


}
