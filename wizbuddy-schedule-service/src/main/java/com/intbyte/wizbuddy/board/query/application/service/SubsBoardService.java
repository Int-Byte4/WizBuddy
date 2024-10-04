package com.intbyte.wizbuddy.board.query.application.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;

import java.util.List;

public interface SubsBoardService {
    List<SubsBoardDTO> findAllSubsBoards();

    SubsBoardDTO findSubsBoardById(int subsCode);

    List<SubsBoardDTO> getSubsBoardsByShopCode(int shopCode);
}
