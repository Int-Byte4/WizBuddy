package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.command.domain.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;

import com.intbyte.wizbuddy.comment.command.domain.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.command.infrastructure.service.InfraCommentService;
import com.intbyte.wizbuddy.comment.command.infrastructure.service.InfraCommentServiceImpl;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository.EmployeeWorkingPartRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InfraCommentServiceImplTests {

    @Autowired
    private InfraCommentService infraAdoptService;

    @Autowired
    private SubsBoardRepository subsBoardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EmployeeWorkingPartRepository employeeWorkingPartRepository;


    @Test
    @DisplayName("댓글_채택_후_스케줄_수정")
    @Transactional
    public void testScheduleService_update_schedule_SuccessTest() {

        int subsCode = 3;
        SubsBoard subsBoard = subsBoardRepository.findById(subsCode).orElse(null);
        CommentDTO modifycomment = new CommentDTO(5,
                "저 대타 가능합니다",true,
                false, LocalDateTime.now(), LocalDateTime.now(),subsBoard.getSubsCode(),
                "20240831-cc00-4288-b2a6-2f864ddbf6b5",1);
        Comment updatedComment = commentRepository.findById(modifycomment.getCommentCode()).orElse(null);
        infraAdoptService.handleAdoptProcess(updatedComment);

        EmployeeWorkingPart updatedWorkingPart = employeeWorkingPartRepository.findByWorkingPartCode(2);
        assertNotNull(updatedWorkingPart);
        assertEquals("20240831-cc00-4288-b2a6-2f864ddbf6b5", updatedWorkingPart.getEmployeeCode());
    }

}
