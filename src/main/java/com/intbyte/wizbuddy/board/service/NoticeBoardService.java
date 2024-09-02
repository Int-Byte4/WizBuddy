package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.exception.noticeboard.NoticeBoardModifyOtherUserException;
import com.intbyte.wizbuddy.exception.noticeboard.NoticeBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployerMapper;
import com.intbyte.wizbuddy.mapper.ManualBoardMapper;
import com.intbyte.wizbuddy.mapper.NoticeBoardMapper;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class NoticeBoardService {

    private final NoticeBoardMapper noticeBoardMapper;
    private final NoticeBoardRepository noticeBoardRepository;
    private final ModelMapper modelMapper;


    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, NoticeBoardMapper noticeBoardMapper, ModelMapper modelMapper) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardMapper = noticeBoardMapper;
        this.modelMapper = modelMapper;
    }

    /* 기능. 1. 공지사항 게시판 게시글 등록 */
    @Transactional
    public void registerNoticeBoard(NoticeBoardDTO noticeBoardInfo) {
        int shopCode = noticeBoardInfo.getShopCode();

        // 예외처리 추가 필요
        NoticeBoard noticeBoard = NoticeBoard.builder()
                .noticeTitle(noticeBoardInfo.getNoticeTitle())
                .noticeContent(noticeBoardInfo.getNoticeContent())
                .noticeFlag(true)
                .imageUrl(noticeBoardInfo.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(shopCode)
                .build();

        noticeBoardRepository.save(noticeBoard);
    }

    /* 기능. 2. 공지사항 게시판 게시글 수정 */
    @Transactional
    public void modifyNoticeBoard(int noticeCode, int requestEmployerCode, EditNoticeBoardInfo modifyNoticeBoardInfo) {
        // 유저가 해당 매장 사장인지 확인
        int writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);
        if (writerCode != requestEmployerCode) throw new NoticeBoardModifyOtherUserException();

        // 게시글 코드로 해당 게시글이 레포지토리에 존재하는지 확인
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeCode).orElseThrow(NoticeBoardNotFoundException::new);

        // 매뉴얼 게시글 제목, 내용, 이미지 url, 수정 날짜 수정
        noticeBoard.modify(modifyNoticeBoardInfo);

        // 변경된 객체를 repository에 저장
        noticeBoardRepository.save(noticeBoard);
    }

    /* 기능. 3. 공지사항 게시판 게시글 삭제 */
    @Transactional
    public void deleteNoticeBoard(int noticeCode, int requestEmployerCode, DeleteNoticeBoardInfo deleteNoticeBoardInfo) {
        // 유저가 공지사항 게시글 작성자인지 확인
        int writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);
        if (writerCode != requestEmployerCode) throw new NoticeBoardModifyOtherUserException();

        // 게시물 코드로 해당 게시글이 레포지토리에 존재하는지 확인
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeCode).orElseThrow(NoticeBoardNotFoundException::new);

        // 게시글 상태, 수정 날짜 수정
        noticeBoard.delete(deleteNoticeBoardInfo);

        // 변경된 객체를 repository에 저장
        noticeBoardRepository.save(noticeBoard);
    }

    /* 기능. 4. 전체 공지사항 게시글 조회 */
    @Transactional
    public List<NoticeBoardDTO> findAllNoticeBoards(int shopCode) {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findAllNoticeBoards(shopCode);

        return noticeBoardList.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    /* 기능 5. 공지사항 게시글 단 건 조회 */
    @Transactional
    public NoticeBoardDTO findNoticeBoard(int noticeCode) {
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoard(noticeCode);

        NoticeBoardDTO noticeBoardDTO = modelMapper.map(noticeBoard, NoticeBoardDTO.class);

        return noticeBoardDTO;
    }
}
