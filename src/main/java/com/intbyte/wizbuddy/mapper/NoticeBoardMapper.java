package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import com.intbyte.wizbuddy.board.dto.NoticeBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeBoardMapper {
    String findEmployerCodeByNoticeCode(int noticeCode);

    List<NoticeBoard> findAllNoticeBoards();

    List<NoticeBoard> findNoticeBoardByShopCode(int shopCode);

    NoticeBoard findNoticeBoardByNoticeCode(int noticeCode);
}
