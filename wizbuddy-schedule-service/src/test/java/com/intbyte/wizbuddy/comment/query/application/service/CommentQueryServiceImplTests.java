package com.intbyte.wizbuddy.comment.query.application.service;

import com.intbyte.wizbuddy.comment.query.application.dto.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentQueryServiceImplTests {


    @Autowired
    private CommentQueryServiceImpl commentService;

    @Test
    @DisplayName("댓글_전체_조회_테스트")
    public void findAllCommentTest() {

        Assertions.assertDoesNotThrow(
                () -> {
                    List<CommentDTO> comments = commentService.findAllComment();
                    comments.forEach(System.out::println);
                });

    }

    @Test
    @DisplayName("댓글_1개_조회_테스트")
    public void findCommentByIdTest(){

        int commentCode = 1;
        CommentDTO comment = commentService.findCommentById(commentCode);
        assertNotNull(comment);
        assertNotNull(comment);

    }

    @Test
    @DisplayName("게시글별_댓글_모두_조회_테스트")
    public void findCommentBySubsBoardTest() {

        int subsCode = 2;
        List<CommentDTO> comments = commentService.getCommentsBySubsCode(subsCode);
        assertNotNull(comments);
    }

    @Test
    @DisplayName("직원별_댓글_모두_조회_테스트")
    public void findCommentByEmployeeTest() {

        String employeeCode = "20240831-471e-4fde-9c53-4b76b34777fd";
        List<CommentDTO> comments = commentService.getCommentsByEmployeeCode(employeeCode);
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());

    }

}