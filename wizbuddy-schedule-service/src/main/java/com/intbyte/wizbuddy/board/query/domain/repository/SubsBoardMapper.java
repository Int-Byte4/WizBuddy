package com.intbyte.wizbuddy.board.query.domain.repository;
import com.intbyte.wizbuddy.board.query.domain.aggregate.SubsBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubsBoardMapper {
    List<SubsBoard> selectAllSubsBoard();
    SubsBoard selectSubsBoardById(int subsCode);
    List<SubsBoard> selectSubsBoardByShopCode(int shopCode);
}
