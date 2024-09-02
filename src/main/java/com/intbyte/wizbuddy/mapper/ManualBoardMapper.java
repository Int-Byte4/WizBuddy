package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManualBoardMapper {
    int findUserCodeByManualCode(int manualCode);

    List<ManualBoard> findAllManualBoards(int shopCode);


    ManualBoard findManualBoard(int manualCode);
}
