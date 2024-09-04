package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.exception.noticeboard.NoticeBoardModifyOtherUserException;
import com.intbyte.wizbuddy.exception.noticeboard.NoticeBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.NoticeBoardMapper;
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
    public ResponseInsertNoticeBoardVO registerNoticeBoard(RequestInsertNoticeBoardVO noticeBoardInfo) {
        NoticeBoard noticeBoard = NoticeBoard.builder()
                .noticeTitle(noticeBoardInfo.getNoticeTitle())
                .noticeContent(noticeBoardInfo.getNoticeContent())
                .noticeFlag(noticeBoardInfo.isNoticeFlag())
                .imageUrl(noticeBoardInfo.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(noticeBoardInfo.getShopCode())
                .employerCode(noticeBoardInfo.getEmployerCode())
                .build();

        NoticeBoard savedNoticeBoard = noticeBoardRepository.save(noticeBoard);

        ResponseInsertNoticeBoardVO vo = ResponseInsertNoticeBoardVO.builder()
                .noticeTitle(savedNoticeBoard.getNoticeTitle())
                .noticeContent(savedNoticeBoard.getNoticeContent())
                .noticeFlag(savedNoticeBoard.isNoticeFlag())
                .imageUrl(savedNoticeBoard.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(savedNoticeBoard.getShopCode())
                .employerCode(savedNoticeBoard.getEmployerCode())
                .build();

        return vo;
    }

    /* 기능. 2. 공지사항 게시판 게시글 수정 */
    @Transactional
    public ResponseUpdateNoticeBoardVO modifyNoticeBoard(int noticeCode, EditNoticeBoardInfo modifyNoticeBoardInfo) {
        // 유저가 해당 매장 사장인지 확인
        String writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);

        if (writerCode != null && writerCode.equals(modifyNoticeBoardInfo.getEmployerCode())) {
            NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeCode).orElseThrow(NoticeBoardNotFoundException::new);

            noticeBoard.modify(modifyNoticeBoardInfo);

            noticeBoardRepository.save(noticeBoard);
            ResponseUpdateNoticeBoardVO vo = ResponseUpdateNoticeBoardVO.builder()
                    .noticeTitle(noticeBoard.getNoticeTitle())
                    .noticeContent(noticeBoard.getNoticeContent())
                    .noticeFlag(noticeBoard.isNoticeFlag())
                    .imageUrl(noticeBoard.getImageUrl())
                    .updatedAt(LocalDateTime.now())
                    .build();

            return vo;
        } else {
            throw new NoticeBoardModifyOtherUserException();
        }
    }

    /* 기능. 3. 공지사항 게시판 게시글 삭제 */
    @Transactional
    public void deleteNoticeBoard(int noticeCode, DeleteNoticeBoardInfo deleteNoticeBoardInfo) {
        // 유저가 공지사항 게시글 작성자인지 확인

        String writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);

        if(writerCode != null && writerCode.equals(deleteNoticeBoardInfo.getEmployerCode())) {
            NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeCode).orElseThrow(NoticeBoardNotFoundException::new);

            noticeBoard.delete(deleteNoticeBoardInfo);

            noticeBoardRepository.save(noticeBoard);

        } else {
            throw new NoticeBoardModifyOtherUserException();
        }
    }

    /* 기능. 4. 전체 공지사항 게시글 조회 */
    @Transactional
    public List<NoticeBoardDTO> findAllNoticeBoards() {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findAllNoticeBoards();

        if(noticeBoardList == null || noticeBoardList.isEmpty()) throw new NoticeBoardNotFoundException();
        return noticeBoardList.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    /* 기능. 5. 매장별 공지사항 게시글 조회 */
    @Transactional
    public List<NoticeBoardDTO> findNoticeBoardByShopCode(int shopCode) {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findNoticeBoardByShopCode(shopCode);

        return noticeBoardList.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    /* 기능 6. 공지사항 게시글 단 건 조회 */
    @Transactional
    public NoticeBoardDTO findNoticeBoardByNoticeCode(int noticeCode) {
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoardByNoticeCode(noticeCode);

        NoticeBoardDTO noticeBoardDTO = modelMapper.map(noticeBoard, NoticeBoardDTO.class);

        return noticeBoardDTO;
    }
}
