//package com.intbyte.wizbuddy.comment.infrastructure.service;
//
//import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
//import com.intbyte.wizbuddy.board.query.application.service.SubsBoardService;
//import com.intbyte.wizbuddy.comment.infrastructure.exception.AlreadyAdoptedSubsBoardException;
//import com.intbyte.wizbuddy.comment.infrastructure.exception.ScheduleNotFoundException;
//import com.intbyte.wizbuddy.comment.infrastructure.exception.SubsBoardNotFoundException;
//import com.intbyte.wizbuddy.comment.infrastructure.exception.WorkingPartCodeNotEqualsException;
//import com.intbyte.wizbuddy.comment.infrastructure.repository.CommentRepository;
//import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
//import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
//import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
//@Service
//@RequiredArgsConstructor
//public class InfraAdoptServiceImpl implements InfraAdoptService {
//
//    private final SubsBoardService subsBoardService;
//    private final CommentRepository commentRepository;
//    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;
//
//    @Override
//    public void handleAdoptProcess(Comment comment) {
//        int subsCode = comment.getSubsCode();
//
//        // SubsBoard 로직은 SubsBoardService에 위임
//        SubsBoardDTO subsBoard = subsBoardService.findSubsBoardById(subsCode);
//        if (subsBoard == null) throw new SubsBoardNotFoundException();
//
//        // 이미 채택된 댓글 확인
//        Comment commentBySubsCode = commentRepository.findBySubsCodeAndCommentAdoptedState(subsCode, comment.isCommentAdoptedState());
//        if (commentBySubsCode != null) throw new AlreadyAdoptedSubsBoardException();
//
//        // Schedule 서비스 이용해 코멘트 작성자와 부서 확인
//        EmployeeWorkingPart writer = employeeWorkingPartRepository.findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
//        if (writer == null) throw new ScheduleNotFoundException();
//
//        List<EmployeeWorkingPart> commentAuthorParts = employeeWorkingPartRepository.findByEmployeeCode(comment.getEmployeeCode());
//        if (commentAuthorParts == null || commentAuthorParts.isEmpty()) {throw new WorkingPartCodeNotEqualsException();}
//
//        EmployeeWorkingPart matchingCommentAuthor = commentAuthorParts.stream()
//                .filter(author -> Objects.equals(author.getWorkingPartTime(), writer.getWorkingPartTime()))
//                .findFirst()
//                .orElseThrow(WorkingPartCodeNotEqualsException::new);
//
//        writer.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
//        employeeWorkingPartRepository.save(writer);
//    }
//}
