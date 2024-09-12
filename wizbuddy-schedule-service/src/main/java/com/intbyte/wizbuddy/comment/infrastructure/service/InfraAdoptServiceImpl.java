package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.comment.infrastructure.exception.AlreadyAdoptedSubsBoardException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.ScheduleNotFoundException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.comment.infrastructure.exception.WorkingDateAndTimeEqualsException;
import com.intbyte.wizbuddy.comment.infrastructure.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.comment.infrastructure.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.comment.infrastructure.repository.SubsBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InfraAdoptService")
@RequiredArgsConstructor
public class InfraAdoptServiceImpl implements InfraAdoptService {

    private final SubsBoardRepository subsBoardRepository;
    private final CommentRepository commentRepository;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;

    @Override
    public void handleAdoptProcess(Comment comment) {

        SubsBoard subsBoard = validateSubsBoard(comment.getSubsCode());

        validateAlreadyAdoptedComment(comment);

        EmployeeWorkingPart writer = validateWriterWorkingPart(subsBoard);

        EmployeeWorkingPart matchingCommentAuthor = validateCommentAuthorWorkingPart(comment, writer);

        updateWorkingPart(writer, matchingCommentAuthor);
    }

    private SubsBoard validateSubsBoard(int subsCode) {
        SubsBoard subsBoard = subsBoardRepository.findBySubsCode(subsCode);
        if (subsBoard == null) {
            throw new SubsBoardNotFoundException();
        }
        return subsBoard;
    }

    private void validateAlreadyAdoptedComment(Comment comment) {
        Comment existingComment = commentRepository.findBySubsCodeAndCommentAdoptedState(comment.getSubsCode(), true);
        if (existingComment != null) {
            throw new AlreadyAdoptedSubsBoardException();
        }
    }

    private EmployeeWorkingPart validateWriterWorkingPart(SubsBoard subsBoard) {
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


        return commentAuthorParts.stream()
                .filter(author -> employeeWorkingPartRepository.existsByWorkingDateAndWorkingPartTime(author.getWorkingDate(),author.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(WorkingDateAndTimeEqualsException::new);
    }

    private void updateWorkingPart(EmployeeWorkingPart writer, EmployeeWorkingPart matchingCommentAuthor) {
        writer.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
        employeeWorkingPartRepository.save(writer);
    }
}
