package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.command.domain.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import com.intbyte.wizbuddy.board.common.exception.SubsBoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("commandSubsBoardService")
@RequiredArgsConstructor
public class SubsBoardServiceImpl implements SubsBoardService {

    private final ModelMapper modelMapper;
    private final SubsBoardRepository subsBoardRepository;

    @Transactional
    @Override
    public ResponseInsertSubsBoardVO registSubsBoard(SubsBoardDTO subsBoardDTO) {
        SubsBoard subsBoard = SubsBoard.builder()
                .subsTitle(subsBoardDTO.getSubsTitle())
                .subsContent(subsBoardDTO.getSubsContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subsFlag(true)
                .employeeWorkingPartCode(subsBoardDTO.getEmployeeWorkingPartCode())
                .shopCode(subsBoardDTO.getShopCode())
                .build();

        subsBoardRepository.save(subsBoard);

        return modelMapper.map(subsBoard, ResponseInsertSubsBoardVO.class);
    }

    @Transactional
    @Override
    public ResponseModifySubsBoardVO modifySubsBoards(int subsCode, EditSubsBoardInfo modifySubsBoardInfo) {
        SubsBoard subsBoard = subsBoardRepository.findById(subsCode)
                .orElseThrow(IllegalArgumentException::new);
        subsBoard.toUpdate(modifySubsBoardInfo);
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseModifySubsBoardVO.class);
    }

    @Transactional
    @Override
    public ResponseDeleteSubsBoardVO deleteSubsBoard(SubsBoardDTO deleteSubsBoardDTO) {
        SubsBoard subsBoard = subsBoardRepository.findById(deleteSubsBoardDTO.getSubsCode())
                .orElseThrow(IllegalArgumentException::new);
        subsBoard.toDelete();
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseDeleteSubsBoardVO.class);
    }
}
