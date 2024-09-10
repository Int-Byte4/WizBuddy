package com.intbyte.wizbuddy.board.query.service;

import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;
import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.query.repository.NoticeBoardMapper;
import com.intbyte.wizbuddy.board.command.domain.repository.NoticeBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("noticeBoardQueryService")
public class NoticeBoardService {

    private final NoticeBoardMapper noticeBoardMapper;
    private final NoticeBoardRepository noticeBoardRepository;
    private final ModelMapper mapper;

    @Autowired
    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, NoticeBoardMapper noticeBoardMapper, ModelMapper modelMapper) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardMapper = noticeBoardMapper;
        this.mapper = modelMapper;
    }

    // 매뉴얼 게시판 전체 조회
    public List<NoticeBoardDTO> findAllNoticeBoards() {
        List<NoticeBoardDTO> noticeBoardList = new ArrayList<>();

        if (noticeBoardMapper.findAllNoticeBoards() == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);

        for (NoticeBoard noticeBoard : noticeBoardMapper.findAllNoticeBoards()) {
            NoticeBoardDTO noticeBoardDTO = mapper.map(noticeBoard, NoticeBoardDTO.class);

            noticeBoardList.add(noticeBoardDTO);
        }

        return noticeBoardList;
    }

    // 특정 매장의 전체 공지사항 게시글 조회
    public List<NoticeBoardDTO> findNoticeBoardByShopCode(int shopCode) {
        List<NoticeBoard> noticeBoardList = noticeBoardMapper.findNoticeBoardByShopCode(shopCode);

        if (noticeBoardList == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);

        List<NoticeBoardDTO> noticeBoardDTOList = new ArrayList<>();

        for (NoticeBoard noticeBoard : noticeBoardMapper.findNoticeBoardByShopCode(shopCode)) {
            NoticeBoardDTO noticeBoardDTO = mapper.map(noticeBoard, NoticeBoardDTO.class);

            noticeBoardDTOList.add(noticeBoardDTO);
        }

        return noticeBoardDTOList;
    }

    // 공지사항 게시글 단 건 조회
    public NoticeBoardDTO findNoticeBoardByNoticeCode(int noticeCode) {
        NoticeBoard noticeBoard = noticeBoardMapper.findNoticeBoardByNoticeCode(noticeCode);

        if (noticeBoard == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);

        return mapper.map(noticeBoard, NoticeBoardDTO.class);
    }
}
