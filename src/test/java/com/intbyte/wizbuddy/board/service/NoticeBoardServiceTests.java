package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.exception.noticeboard.NoticeBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.NoticeBoardMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

//    @Test
//    @DisplayName("공지사항 게시글 등록 성공")
//    @Transactional
//    public void testRegisterNoticeBoard() {
//        // given
//        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
//        int lastNum = noticeBoards.size();
//
//        NoticeBoardDTO noticeBoardInfo = new NoticeBoardDTO(lastNum, "test", "test", true, null, LocalDateTime.now(), LocalDateTime.now(), 1,"20240831-3750-4218-9aed-7eabc7c634c2");
//
//        // when
//        noticeBoardService.registerNoticeBoard(noticeBoardInfo);
//
//        // then
//        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
//        int newLastNum = newNoticeBoards.size();
//
//        assertEquals(newLastNum, noticeBoardRepository.findAll().size());
//
//        newNoticeBoards.forEach(System.out::println);
//    }

    @Test
    @DisplayName("공지사항 게시물 수정 성공")
    @Transactional
    public void testModifyNoticeBoardSuccess() {
        // given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        EditNoticeBoardInfo editNoticeBoardInfo = new EditNoticeBoardInfo(1, "제목 수정", "내용 수정", null, true, LocalDateTime.now(), "20240831-3750-4218-9aed-7eabc7c634c2");

        // when
        noticeBoardService.modifyNoticeBoard(1, editNoticeBoardInfo);

        // then
        List<NoticeBoard> newNoticeBoards = noticeBoardRepository.findAll();
        assertEquals(newNoticeBoards.get(0).getNoticeTitle(), editNoticeBoardInfo.getNoticeTitle());

        newNoticeBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("공지사항 게시물 삭제 성공")
    @Transactional
    public void testDeleteNoticeBoardSuccess() {
        // given
        String employerCode =  "20240831-3750-4218-9aed-7eabc7c634c2";
        DeleteNoticeBoardInfo deleteNoticeBoardInfo = new DeleteNoticeBoardInfo(false, LocalDateTime.now(), employerCode);

        // when
        noticeBoardService.deleteNoticeBoard(1, deleteNoticeBoardInfo);

        // then
        NoticeBoard noticeBoard = noticeBoardRepository.findById(1).orElseThrow(NoticeBoardNotFoundException::new);

        assertFalse(noticeBoard.isNoticeFlag());
    }

    @Test
    @DisplayName("공지사항 게시글 전체 조회")
    @Transactional
    public void testFindAllNoticeBoards() {
        List<NoticeBoardDTO> noticeBoards = noticeBoardService.findAllNoticeBoards();

        noticeBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("매장별 공지사항 게시글 조회")
    @Transactional
    public void testFindNoticeBoardByShopCode() {
        int shopCode = 1;

        List<NoticeBoardDTO> noticeBoards = noticeBoardService.findNoticeBoardByShopCode(shopCode);

        noticeBoards.forEach(System.out::println);
    }

    @Test
    @DisplayName("공지사항 게시글 단 건 조회")
    @Transactional
    public void testFindNoticeBoardByNoticeCode() {
        //given, when
        int noticeCode = 3;

        //then
        NoticeBoardDTO noticeBoard = noticeBoardService.findNoticeBoardByNoticeCode(noticeCode);

        System.out.println(noticeBoard);
    }
}