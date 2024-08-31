package com.intbyte.wizbuddy.comment.service;

import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Test
    public void 댓글_전체_조회_테스트() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<CommentDTO> comments = commentService.findAllComment();
                    comments.forEach(System.out::println);
                });

    }

}