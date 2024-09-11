package com.intbyte.wizbuddy.like.query.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ManualBoardLikedMapper {
    ManualBoardLiked getManualBoardLikedByUserCode(Map<String, Object> params);
}
