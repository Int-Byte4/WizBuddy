package com.intbyte.wizbuddy.board.infrastructure.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.application.service.SubsBoardService;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.command.domain.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class InfraSubsBoardServiceImplTests {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SubsBoardService subsBoardService;


    @Test
    @DisplayName("게시글_삭제_시_관련_댓글_삭제)")
    @Transactional
    public void deleteCommentTest() {

        int subsCode = 2;
        SubsBoardDTO modifySubsBoard = new SubsBoardDTO(subsCode,
                "9월 5일 대타 구함 ㅜㅜ","내일은 한가한날 이지만 대청소 도와줄사람",
                LocalDateTime.now(),LocalDateTime.now(),true,
                2,1);
        subsBoardService.deleteSubsBoard(modifySubsBoard);
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentFlag()).isFalse();
    }
}