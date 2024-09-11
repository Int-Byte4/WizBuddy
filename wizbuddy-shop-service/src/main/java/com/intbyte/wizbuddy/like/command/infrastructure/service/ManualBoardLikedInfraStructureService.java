package com.intbyte.wizbuddy.like.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.dto.ManualBoardDTO;

public interface ManualBoardLikedInfraStructureService {
    ManualBoardDTO findManualBoardByManualCode(int manualCode);
}
