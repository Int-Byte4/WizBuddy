package com.intbyte.wizbuddy.like.query.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ManualBoardLikedMapper {

    String findEmployeeCodeByManualCode(int manualCode);

    List<ManualBoardLiked> findLikesByManualCode(int manualCode);
}
