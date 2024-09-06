package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubsBoardMapper {
    List<SubsBoard> selectAllSubsBoard();
    SubsBoard selectSubsBoardById(int subsCode);
    List<SubsBoard> selectSubsBoardByShopCode(int shopCode);
}
