package com.intbyte.wizbuddy.comment.service;

import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.mapper.CommentMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CommentServiceTests {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Qualifier("commentRepository")
    @Autowired
    private CommentRepository commentRepository;

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

//    @Test
//    @Transactional
//    @DisplayName("댓글_등록_테스트")
//    public void insertCommentTest() {
//
//        //given
//        int susCode = 1;
//        int empolyeeCode = 2;
//        List<CommentDTO> commentList = commentService.findAllComment();
//        CommentDTO comment = new CommentDTO(commentList.size() +1,"사장님 저 믿으시죠 저 가능합니다.",true,false, LocalDateTime.now(), LocalDateTime.now(),susCode,empolyeeCode);
//
//        //when
//        commentService.registerComment(comment);
//
//        //then
//        Comment newcomment = commentMapper.selectCommentById(comment.getCommentCode());
//        assertNotNull(newcomment);
//        assertEquals(comment.getCommentCode(),newcomment.getCommentCode());
//
//    }

    @Test
    @DisplayName("댓글_수정_테스트")
    @Transactional
    public void modifyCommentTest() {
        //given
        int commentCode = 4;
        EditCommentInfo modifyComment = new EditCommentInfo(commentCode,
                "사장님 저요 저요저요저 !!!!",
                true,false,
                LocalDateTime.now(),LocalDateTime.now());
        //when
        commentService.modifyComment(commentCode, modifyComment);
        //when
        Comment comment = commentMapper.selectCommentById(commentCode);
        assertEquals(modifyComment.getCommentContent(),comment.getCommentContent());
    }

    @Test
    @DisplayName("댓글_삭제_테스트")
    @Transactional
    public void deleteCommentTest() {

        // given
        int subsCode = 1;
        CommentDTO comment = commentService.findCommentById(subsCode);
        // when
        commentService.removeComment(comment);
        // then
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        AssertionsForClassTypes.assertThat(updatedComment).isNotNull();
        AssertionsForClassTypes.assertThat(updatedComment.isCommentFlag()).isFalse();

    }


}