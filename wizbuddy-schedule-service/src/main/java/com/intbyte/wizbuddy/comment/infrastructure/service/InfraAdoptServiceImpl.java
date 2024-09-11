package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.infrastructure.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.comment.infrastructure.exception.AlreadyAdoptedSubsBoardException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.ScheduleNotFoundException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.WorkingDateAndTimeEqualsException;
import com.intbyte.wizbuddy.comment.infrastructure.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.comment.infrastructure.repository.EmployeeWorkingPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("InfraAdoptService")
@RequiredArgsConstructor
public class InfraAdoptServiceImpl implements InfraAdoptService {

    private final SubsBoardRepository subsBoardRepository;
    private final CommentRepository commentRepository;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Override
    public void handleAdoptProcess(Comment comment) {
        // 대타 게시글 검증
        SubsBoardDTO subsBoard = validateSubsBoard(comment.getSubsCode());

        // 이미 채택된 댓글 검증
        validateAlreadyAdoptedComment(comment);

        // 작성자의 부서 확인 및 처리
        EmployeeWorkingPart writer = validateWriterWorkingPart(subsBoard);

        // 댓글 작성자와 부서 및 근무 시간, 근무일 일치 확인
        EmployeeWorkingPart matchingCommentAuthor = validateCommentAuthorWorkingPart(comment, writer);

        // 작업 부서 수정 및 저장
        updateWorkingPart(writer, matchingCommentAuthor);
    }

    private SubsBoardDTO validateSubsBoard(int subsCode) {
        SubsBoardDTO subsBoard = subsBoardRepository.findBysubsCode(subsCode);
        if (subsBoard == null) {
            throw new SubsBoardNotFoundException();
        }
        return subsBoard;
    }

    private void validateAlreadyAdoptedComment(Comment comment) {
        Comment existingComment = commentRepository.findBySubsCodeAndCommentAdoptedState(comment.getSubsCode(), comment.isCommentAdoptedState());
        if (existingComment != null) {
            throw new AlreadyAdoptedSubsBoardException();
        }
    }

    private EmployeeWorkingPart validateWriterWorkingPart(SubsBoardDTO subsBoard) {
        EmployeeWorkingPart writer = employeeWorkingPartRepository.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (writer == null) {
            throw new ScheduleNotFoundException();
        }
        return writer;
    }

    private EmployeeWorkingPart validateCommentAuthorWorkingPart(Comment comment, EmployeeWorkingPart writer) {
        List<EmployeeWorkingPart> commentAuthorParts = employeeWorkingPartRepository.findByEmployeeCode(comment.getEmployeeCode());
        if (commentAuthorParts == null || commentAuthorParts.isEmpty()) {
            throw new WorkingPartCodeNotEqualsException();
        }

        // 근무 날짜와 시간도 일치하는지 확인
        return commentAuthorParts.stream()
                .filter(author -> Objects.equals(author.getWorkingPartTime(), writer.getWorkingPartTime())
                        && Objects.equals(author.getWorkingDate(), writer.getWorkingDate()))
                .findFirst()
                .orElseThrow(WorkingDateAndTimeEqualsException::new);
    }

    private void updateWorkingPart(EmployeeWorkingPart writer, EmployeeWorkingPart matchingCommentAuthor) {
        writer.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
        employeeWorkingPartRepository.save(writer);
    }
}
