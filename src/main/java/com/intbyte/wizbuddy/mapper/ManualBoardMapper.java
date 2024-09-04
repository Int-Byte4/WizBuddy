package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManualBoardMapper {
    String findUserCodeByManualCode(int manualCode);

    List<ManualBoard> findAllManualBoards();

    List<ManualBoard> findManualBoardByShopCode(int shopCode);

    ManualBoard findManualBoardByManualCode(int manualCode);
}
