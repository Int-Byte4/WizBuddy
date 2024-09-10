package com.intbyte.wizbuddy.like.query.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface NoticeBoardLikedMapper {
    NoticeBoardLiked getNoticeBoardLikedByUserCode(Map<String, Object> params);
}
