package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.exception.board.noticeboard.NoticeBoardModifyOtherUserException;
import com.intbyte.wizbuddy.exception.board.noticeboard.NoticeBoardNotFoundException;
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

    @Transactional
    public ResponseUpdateNoticeBoardVO modifyNoticeBoard(int noticeCode, EditNoticeBoardInfo modifyNoticeBoardInfo) {
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

    @Transactional
    public void deleteNoticeBoard(int noticeCode, DeleteNoticeBoardInfo deleteNoticeBoardInfo) {

        String writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);

        if(writerCode != null && writerCode.equals(deleteNoticeBoardInfo.getEmployerCode())) {
            NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeCode).orElseThrow(NoticeBoardNotFoundException::new);

            noticeBoard.delete(deleteNoticeBoardInfo);

            noticeBoardRepository.save(noticeBoard);

        } else {
            throw new NoticeBoardModifyOtherUserException();
        }
    }

    @Transactional
    public List<NoticeBoardDTO> findAllNoticeBoards() {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findAllNoticeBoards();

        if(noticeBoardList == null || noticeBoardList.isEmpty()) throw new NoticeBoardNotFoundException();
        return noticeBoardList.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<NoticeBoardDTO> findNoticeBoardByShopCode(int shopCode) {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findNoticeBoardByShopCode(shopCode);

        return noticeBoardList.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public NoticeBoardDTO findNoticeBoardByNoticeCode(int noticeCode) {
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoardByNoticeCode(noticeCode);

        NoticeBoardDTO noticeBoardDTO = modelMapper.map(noticeBoard, NoticeBoardDTO.class);

        return noticeBoardDTO;
    }
}
