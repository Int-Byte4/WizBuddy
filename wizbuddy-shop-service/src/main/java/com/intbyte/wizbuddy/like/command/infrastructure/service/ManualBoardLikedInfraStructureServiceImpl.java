package com.intbyte.wizbuddy.like.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.query.service.ManualBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("manualBoardLikedInfraStructureService")
public class ManualBoardLikedInfraStructureServiceImpl implements ManualBoardLikedInfraStructureService {

    private final ManualBoardService manualBoardService;

    @Autowired
    public ManualBoardLikedInfraStructureServiceImpl(ManualBoardService manualBoardService) {
        this.manualBoardService = manualBoardService;
    }

    @Override
    public ManualBoardDTO findManualBoardByManualCode(int manualCode) {
        return manualBoardService.findManualBoardByManualCode(manualCode);
    }
}
