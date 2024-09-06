package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.like.domain.entity.ManualBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ManualBoardLikedMapper {
    ManualBoardLiked getManualBoardLikedByUserCode(Map<String, Object> params);
}
