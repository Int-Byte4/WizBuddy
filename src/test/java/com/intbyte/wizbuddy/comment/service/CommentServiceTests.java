package com.intbyte.wizbuddy.comment.service;

import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CommentServiceTests {

    @Autowired
    private CommentService commentService;

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

        //given
        int commentCode = 1;
        
        //when
        CommentDTO comment = commentService.findCommentById(commentCode);
        
        //then
        assertNotNull(comment);
        assertNotNull(comment);
        System.out.println("comment = " + comment);
        

    }

}