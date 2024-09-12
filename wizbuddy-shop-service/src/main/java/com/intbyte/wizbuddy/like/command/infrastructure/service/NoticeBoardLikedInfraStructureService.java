package com.intbyte.wizbuddy.like.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.dto.NoticeBoardDTO;

public interface NoticeBoardLikedInfraStructureService {
    NoticeBoardDTO findNoticeBoardByNoticeCode(int noticeCode);
}
