package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditNoticeBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.request.RequestInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.NoticeBoardRepository;
import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;

import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseInsertNoticeBoardVO;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseUpdateNoticeBoardVO;
import com.intbyte.wizbuddy.board.query.repository.NoticeBoardMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    // 공지사항 게시판 게시글 등록
    @Transactional
    public ResponseInsertNoticeBoardVO registerNoticeBoard(RequestInsertNoticeBoardVO requestInsertNoticeBoardVO) {
        NoticeBoard noticeBoard = modelMapper.map(requestInsertNoticeBoardVO, NoticeBoard.class);

        noticeBoardRepository.save(noticeBoard);

        return modelMapper.map(noticeBoard, ResponseInsertNoticeBoardVO.class);
    }

    // 공지사항 게시판 게시글 수정
    @Transactional
    public ResponseUpdateNoticeBoardVO modifyNoticeBoard(int noticeCode, RequestEditNoticeBoardDTO requestEditNoticeBoardDTO) {
        String writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);
        String employerCode = requestEditNoticeBoardDTO.getEmployerCode();
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoardByNoticeCode(noticeCode);

        if(noticeBoard == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        if(!employerCode.equals(writerCode)) throw new CommonException(StatusEnum.RESTRICTED);

        noticeBoard.modify(requestEditNoticeBoardDTO);
        noticeBoardRepository.save(noticeBoard);

        return modelMapper.map(noticeBoard, ResponseUpdateNoticeBoardVO.class);
    }

    // 공지사항 게시판 게시글 삭제
    @Transactional
    public void deleteNoticeBoard(int noticeCode, String employerCode) {
        String writerCode = noticeBoardMapper.findEmployerCodeByNoticeCode(noticeCode);
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoardByNoticeCode(noticeCode);

        if(!employerCode.equals(writerCode)) throw new CommonException(StatusEnum.RESTRICTED);

        noticeBoard.delete();

        noticeBoardRepository.save(noticeBoard);
    }
}
