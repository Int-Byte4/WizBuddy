package com.intbyte.wizbuddy.board.query.repository;

import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeBoardMapper {
    String findEmployerCodeByNoticeCode(int noticeCode);

    List<NoticeBoard> findAllNoticeBoards();

    List<NoticeBoard> findNoticeBoardByShopCode(int shopCode);

    NoticeBoard findNoticeBoardByNoticeCode(int noticeCode);
}
