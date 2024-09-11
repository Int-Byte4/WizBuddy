package com.intbyte.wizbuddy.comment.infrastructure.service;

import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.infrastructure.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.infrastructure.repository.EmployeeWorkingPartRepository;
import com.intbyte.wizbuddy.comment.infrastructure.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InfraAdoptServiceImplTests {

    @Autowired
    private InfraAdoptServiceImpl infraAdoptService;
    @Autowired
    private SubsBoardRepository subsBoardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EmployeeWorkingPartRepository employeeWorkingPartRepository;


    @Test
    @DisplayName("댓글 채택해서 스케줄 수정")
    @Transactional
    public void testScheduleService_update_schedule_SuccessTest() {

        int subsCode = 1;

        Comment comment = commentRepository.findById(2).orElse(null);

        // When: handleAdoptProcess 메서드 실행
        infraAdoptService.handleAdoptProcess(comment);

        // Then: 예외가 발생하지 않으면 성공, 검증 로직 추가 가능
        EmployeeWorkingPart writer = employeeWorkingPartRepository.findByWorkingPartCode(1);
        assertNotNull(writer);
        assertEquals("20240831-1859-4c43-b692-b6cb5891c24a", writer.getEmployeeCode());
    }

}
