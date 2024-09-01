package com.intbyte.wizbuddy.comment.service;

import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import com.intbyte.wizbuddy.comment.domain.Entity.Comment;
import com.intbyte.wizbuddy.comment.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.repository.CommentRepository;
import com.intbyte.wizbuddy.mapper.CommentMapper;
import org.junit.jupiter.api.Assertions;
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
class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

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

    }

    @Test
    @DisplayName("게시글별_댓글_모두_조회_테스트")
    public void findCommentBySubsBoardTest() {

        // given
        int subsCode = 2;
        // when
        List<CommentDTO> comments = commentService.getCommentsBySubsCode(subsCode);
        // then
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());

    }

    @Test
    @DisplayName("직원별_댓글_모두_조회_테스트")
    public void findCommentByEmployeeTest() {

        // given
        String employeeCode = "20240831-471e-4fde-9c53-4b76b34777fd";
        // when
        List<CommentDTO> comments = commentService.getCommentsByEmployeeCode(employeeCode);
        // then
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());

    }

    @Test
    @DisplayName("댓글_등록_테스트")
    @Transactional
    public void insertCommentTest() {

        //given
        List<Comment> commentList = commentRepository.findAll();
        CommentDTO comment = new CommentDTO(commentList.size() +1,
                "사장님 저 믿으시죠 저 가능합니다.",true,
                false, LocalDateTime.now(), LocalDateTime.now(),1,
                "20240831-f409-40b1-a03d-4d14d52fa13a");
        //when
        System.out.println("comment = " + comment);
        commentService.registerComment(comment);
        //then
        List<Comment> newcomments = commentRepository.findAll();
        Comment newcomment = newcomments.get(newcomments.size()-1);
        System.out.println("newcomment = " + newcomment);
        assertNotNull(newcomment);
        assertEquals(comment.getCommentContent(),newcomment.getCommentContent());

    }

    @Test
    @DisplayName("댓글_수정_테스트")
    @Transactional
    public void modifyCommentTest() {

        //given
        int commentCode = 1;
        Comment comment = commentMapper.selectCommentById(commentCode);
        EditCommentInfo updateComment = new EditCommentInfo( "사장님 ㅋㅋ 저할거랑께요?",
                false, LocalDateTime.now());
        //when
        commentService.modifyComment(comment.getCommentCode(), updateComment);
        //when
        Comment newcomment = commentRepository.findById(commentCode).orElse(null);
        assertEquals(updateComment.getCommentContent(),newcomment.getCommentContent());

    }

    @Test
    @DisplayName("댓글_삭제_테스트")
    @Transactional
    public void deleteCommentTest() {

        // given
        int subsCode = 2;
        CommentDTO comment = commentService.findCommentById(subsCode);
        // when
        commentService.removeComment(comment);
        // then
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentFlag()).isFalse();

    }

    @Test
    @DisplayName("댓글_채택_테스트")
    @Transactional
    public void adoptCommentTest() {

        // given
        int subsCode = 2;
        CommentDTO comment = commentService.findCommentById(subsCode);
        // when
        commentService.adoptComment(comment);
        // then
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentAdoptedState()).isTrue();

    }


}