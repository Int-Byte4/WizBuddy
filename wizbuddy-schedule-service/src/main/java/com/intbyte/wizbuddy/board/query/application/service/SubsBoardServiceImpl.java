package com.intbyte.wizbuddy.board.query.application.service;

import com.intbyte.wizbuddy.board.common.exception.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.query.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.query.domain.repository.SubsBoardMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("querySubsBoardService")
@RequiredArgsConstructor
public class SubsBoardServiceImpl implements SubsBoardService {

    private final SubsBoardMapper subsBoardMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<SubsBoardDTO> findAllSubsBoards() {
        List<SubsBoard> subsBoardList = subsBoardMapper.selectAllSubsBoard();
        if (subsBoardList == null || subsBoardList.isEmpty()) {
            throw new SubsBoardNotFoundException();
        }
        return subsBoardList.stream()
                .map(subsBoard -> modelMapper.map(subsBoard, SubsBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SubsBoardDTO findSubsBoardById(int subsCode) {
        SubsBoard subsBoard = subsBoardMapper.selectSubsBoardById(subsCode);
        if (subsBoard == null) {
            throw new SubsBoardNotFoundException();
        }
        return modelMapper.map(subsBoard, SubsBoardDTO.class);
    }

    @Override
    public List<SubsBoardDTO> getSubsBoardsByShopCode(int shopCode) {
        List<SubsBoard> subsBoards = subsBoardMapper.selectSubsBoardByShopCode(shopCode);
        if (subsBoards == null || subsBoards.isEmpty()) {
            throw new SubsBoardNotFoundException();
        }
        return subsBoards.stream()
                .map(subsBoard -> modelMapper.map(subsBoard, SubsBoardDTO.class))
                .collect(Collectors.toList());
    }
}
