package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.command.domain.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.board.query.repository.NoticeBoardMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("noticeBoardCommandService")
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
}
