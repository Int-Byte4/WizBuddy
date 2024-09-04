package com.intbyte.wizbuddy.comment.service;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.vo.response.ResponseAdoptCommentVO;
import com.intbyte.wizbuddy.comment.vo.response.ResponseDeleteCommentVO;
import com.intbyte.wizbuddy.comment.vo.response.ResponseInsertCommentVO;
import com.intbyte.wizbuddy.comment.vo.response.ResponseModifyCommentVO;
import com.intbyte.wizbuddy.exception.board.AlreadyAdoptedSubsBoardException;
import com.intbyte.wizbuddy.exception.board.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.exception.comment.CommentNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.ScheduleNotFoundException;
import com.intbyte.wizbuddy.exception.schedule.WorkingPartCodeNotEqualsException;
import com.intbyte.wizbuddy.mapper.CommentMapper;
import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.schedule.repository.EmployeeWorkingPartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final EmployeeWorkingPartRepository employeeWorkingPartRepository;
    private final SubsBoardRepository subsBoardRepository;


    public List<CommentDTO> findAllComment() {
        List<Comment> commentList = commentMapper.selectAllComment();
        if(commentList == null || commentList.isEmpty()) {throw new CommentNotFoundException();}
        return  commentList.stream()
                .map(comment-> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO findCommentById(int code) {
        Comment comment = commentMapper.selectCommentById(code);
        if(comment == null) {throw new CommentNotFoundException();}
        return modelMapper.map(comment, CommentDTO.class);
    }

    public List<CommentDTO> getCommentsBySubsCode(int subsCode) {
        List<Comment> comments = commentMapper.selectCommentBySubsCode(subsCode);
        if(comments == null || comments.isEmpty()) {throw new CommentNotFoundException();}
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByEmployeeCode(String employeeCode) {
        List<Comment> comments = commentMapper.selectCommentByEmployeeCode(employeeCode);
        if(comments == null || comments.isEmpty()) {throw new CommentNotFoundException();}
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseInsertCommentVO registerComment(CommentDTO comments) {

        Comment comment = Comment.builder()
                .commentContent(comments.getCommentContent())
                .commentFlag(true)
                .commentAdoptedState(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subsCode(comments.getSubsCode())
                .employeeCode(comments.getEmployeeCode())
                .build();

        commentRepository.save(comment);
        return modelMapper.map(comments, ResponseInsertCommentVO.class);
    }

    @Transactional
    public ResponseModifyCommentVO modifyComment(int commentCode, EditCommentInfo modifyCommentInfo) {
        Comment modifycomment = commentRepository.findById(commentCode).orElseThrow(CommentNotFoundException::new);
        modifycomment.toUpdate(modifyCommentInfo);
        commentRepository.save(modifycomment);
        return modelMapper.map(modifycomment, ResponseModifyCommentVO.class);
    }

    @Transactional
    public ResponseDeleteCommentVO removeComment(CommentDTO deleteComment) {
        Comment comment = commentRepository.findById(deleteComment.getCommentCode()).orElseThrow(CommentNotFoundException::new);
        comment.toDelete();
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseDeleteCommentVO.class);
    }

    @Transactional
    public ResponseAdoptCommentVO adoptComment(CommentDTO adoptComment) {
        SubsBoard subsBoard = subsBoardRepository.findBysubsCode(adoptComment.getSubsCode());
        if (subsBoard == null) throw new SubsBoardNotFoundException();

        Comment comment = commentRepository.findBySubsCodeAndCommentAdoptedState(adoptComment.getSubsCode(), adoptComment.isCommentAdoptedState());
        if(comment != null) throw new AlreadyAdoptedSubsBoardException();

        EmployeeWorkingPart writer = employeeWorkingPartRepository
                .findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());

        List<EmployeeWorkingPart> commentAuthor = employeeWorkingPartRepository
                .findByEmployeeCode(comment.getEmployeeCode());

        EmployeeWorkingPart matchingCommentAuthor = commentAuthor.stream()
                .filter(author -> Objects.equals(author.getWorkingPartTime(), writer.getWorkingPartTime()))
                .findFirst()
                .orElseThrow(WorkingPartCodeNotEqualsException::new);

        EmployeeWorkingPart employeeWorkingPart = employeeWorkingPartRepository
                .findByWorkingPartCode(subsBoard.getEmployeeWorkingPartCode());
        if (employeeWorkingPart == null) throw new ScheduleNotFoundException();

        comment.toAdopt();
        employeeWorkingPart.modifyWorkingPart(matchingCommentAuthor.getEmployeeCode());
        commentRepository.save(comment);
        employeeWorkingPartRepository.save(employeeWorkingPart);

        return modelMapper.map(comment, ResponseAdoptCommentVO.class);
    }

}
