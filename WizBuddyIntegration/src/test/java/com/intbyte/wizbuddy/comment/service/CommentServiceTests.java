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
        assertTrue(!comments.isEmpty());

    }

    @Test
    @DisplayName("직원별_댓글_모두_조회_테스트")
    public void findCommentByEmployeeTest() {

        String employeeCode = "20240831-471e-4fde-9c53-4b76b34777fd";
        List<CommentDTO> comments = commentService.getCommentsByEmployeeCode(employeeCode);
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());

    }

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
        Comment comment = commentMapper.selectCommentById(commentCode);
        EditCommentInfo updateComment = new EditCommentInfo( "사장님 ㅋㅋ 저할거랑께요?",
                false, LocalDateTime.now());
        commentService.modifyComment(comment.getCommentCode(), updateComment);
        Comment newcomment = commentRepository.findById(commentCode).orElse(null);
        assertEquals(updateComment.getCommentContent(),newcomment.getCommentContent());

    }

    @Test
    @DisplayName("댓글_삭제_테스트")
    @Transactional
    public void deleteCommentTest() {

        int subsCode = 2;
        CommentDTO comment = commentService.findCommentById(subsCode);
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
        CommentDTO comment = commentService.findCommentById(subsCode);
        commentService.adoptComment(comment);
        Comment updatedComment = commentRepository.findById(subsCode).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.isCommentAdoptedState()).isTrue();

    }


}