package com.intbyte.wizbuddy.comment.command.application.service;

import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentService;


    @Test
    @DisplayName("댓글_등록_테스트")
    @Transactional
    public void insertCommentTest() {

        List<Comment> commentList = commentRepository.findAll();
        CommentDTO comment = new CommentDTO(commentList.size() +1,
                "사장님 저 믿으시죠 저 가능합니다.",true,
                false, LocalDateTime.now(), LocalDateTime.now(),1,
                "20240831-f409-40b1-a03d-4d14d52fa13a");
        commentService.registerComment(comment);
        List<Comment> newcomments = commentRepository.findAll();
        Comment newcomment = newcomments.get(newcomments.size()-1);
        assertNotNull(newcomment);
        assertEquals(comment.getCommentContent(),newcomment.getCommentContent());

    }

    @Test
    @DisplayName("댓글_수정_테스트")
    @Transactional
    public void modifyCommentTest() {

        int commentCode = 1;
        EditCommentVO updateComment = new EditCommentVO( "사장님 ㅋㅋ 저할거랑께요?",
                false, LocalDateTime.now());
        commentService.modifyComment(commentCode, updateComment);
        Comment newcomment = commentRepository.findById(commentCode).orElse(null);
        assertEquals(updateComment.getCommentContent(),newcomment.getCommentContent());

    }

    @Test
    @DisplayName("댓글_삭제_테스트")
    @Transactional
    public void deleteCommentTest() {

        int subsCode = 2;
        CommentDTO comment = new CommentDTO(subsCode,
                "캬 퍄 ㅋㅋ 저 대타 가능합니다",true,
                false, LocalDateTime.now(), LocalDateTime.now(),2,
                "20240831-471e-4fde-9c53-4b76b34777fd");
        commentService.removeComment(comment);
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentFlag()).isFalse();

    }

    @Test
    @DisplayName("댓글_채택_테스트")
    @Transactional
    public void adoptCommentTest() {

        int subsCode = 2;
        CommentDTO comment = new CommentDTO(subsCode,
                "캬 퍄 ㅋㅋ 저 대타 가능합니다",true,
                false, LocalDateTime.now(), LocalDateTime.now(),2,
                "20240831-471e-4fde-9c53-4b76b34777fd");
        commentService.adoptComment(comment);
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentAdoptedState()).isTrue();

    }

}


