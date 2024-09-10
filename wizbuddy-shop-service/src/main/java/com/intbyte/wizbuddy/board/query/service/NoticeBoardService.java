package com.intbyte.wizbuddy.board.query.service;

import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.query.repository.NoticeBoardMapper;
import com.intbyte.wizbuddy.board.command.domain.repository.NoticeBoardRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("noticeBoardQueryService")
public class NoticeBoardService {
    private final NoticeBoardMapper noticeBoardMapper;
    private final NoticeBoardRepository noticeBoardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, NoticeBoardMapper noticeBoardMapper, ModelMapper modelMapper) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardMapper = noticeBoardMapper;
        this.modelMapper = modelMapper;
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
