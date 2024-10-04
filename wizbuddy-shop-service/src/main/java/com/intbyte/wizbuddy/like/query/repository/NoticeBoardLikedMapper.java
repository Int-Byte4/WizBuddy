package com.intbyte.wizbuddy.like.query.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeBoardLikedMapper {
    String findEmployeeCodeByNoticeCode(int noticeCode);

    List<NoticeBoardLiked> findLikesByNoticeCode(int noticeCode);
}
