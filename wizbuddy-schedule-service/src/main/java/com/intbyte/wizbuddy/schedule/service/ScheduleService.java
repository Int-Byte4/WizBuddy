package com.intbyte.wizbuddy.schedule.service;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.exception.board.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleCodeDuplicateException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.mapper.EmployeeWorkingPartMapper;
import com.intbyte.wizbuddy.mapper.WeeklyScheduleMapper;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.domain.entity.WeeklySchedule;
import com.intbyte.wizbuddy.schedule.dto.EmployeeWorkingPartDTO;
import com.intbyte.wizbuddy.schedule.dto.WeeklyScheduleDTO;
import com.intbyte.wizbuddy.schedule.info.EditScheduleInfo;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.schedule.repository.WeeklyScheduleRepository;
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

        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByScheduleCode(scheduleCode);

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

        List<EmployeeWorkingPart> employeeWorkingPart = employeeWorkingPartMapper
                .findEmployeeByEmployeeCode(employeeCode);

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

        if(employeeWorkingPartMapper
                .findEmployeeByEmployeeCode(employeeCode) == null) throw new EmployeeCodeNotFoundException();

        employeeWorkingPartRepository.save(insertSchedulePerEmployee);

        return employeeWorkingPart;
    }

    @Transactional
    public void editSchedule(int workingPartCode, EditScheduleInfo modifyScheduleInfo) {

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByWorkingPartCode(workingPartCode);

        List<EmployeeWorkingPart> actualSchedule = employeeWorkingPartMapper
                .findEmployeeByEmployeeCode(modifyScheduleInfo.getEmployeeCode());

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

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartMapper
                .selectScheduleByWorkingPartCode(workingPartCode);

        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        employeeWorkingPartRepository.delete(employeeWorkingPart);
    }

    @Transactional
    public void editScheduleByComment(int subsCode, boolean subsFlag, String employeeCode) {

        SubsBoard subsBoard = subsBoardRepository.findBysubsCodeAndSubsFlag(subsCode, subsFlag);
        if (subsBoard == null) throw new SubsBoardNotFoundException();

        EmployeeWorkingPart writer = employeeWorkingPartRepository
                .findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());

        Comment comment = commentRepository.findBySubsCodeAndEmployeeCode(subsCode,employeeCode);

        List<EmployeeWorkingPart> commentAuthor = employeeWorkingPartRepository
                .findByEmployeeCode(comment.getEmployeeCode());

        EmployeeWorkingPart nonMatchingCommentAuthors = commentAuthor.stream()
                .filter(author -> !Objects.equals(author.getWorkingPartTime(), writer.getWorkingPartTime())
                        || !Objects.equals(author.getWorkingDate(), writer.getWorkingDate()))
                .findFirst()
                .orElseThrow(WorkingPartCodeNotEqualsException::new);

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        employeeWorkingPart.modifyWorkingPart(nonMatchingCommentAuthors.getEmployeeCode());
        employeeWorkingPartRepository.save(employeeWorkingPart);
    }


}
