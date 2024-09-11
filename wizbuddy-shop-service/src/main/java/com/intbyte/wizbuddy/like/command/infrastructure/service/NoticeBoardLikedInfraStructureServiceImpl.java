package com.intbyte.wizbuddy.like.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;
import com.intbyte.wizbuddy.board.query.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeBoardLikedInfraStructureServiceImpl implements NoticeBoardLikedInfraStructureService {

    private NoticeBoardService noticeBoardService;

    @Autowired
    public NoticeBoardLikedInfraStructureServiceImpl(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    public NoticeBoardDTO findNoticeBoardByNoticeCode(int noticeCode) {
        return noticeBoardService.findNoticeBoardByNoticeCode(noticeCode);
    }


}
