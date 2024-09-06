package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.like.domain.entity.NoticeBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface NoticeBoardLikedMapper {
    NoticeBoardLiked getNoticeBoardLikedByUserCode(Map<String, Object> params);
}
