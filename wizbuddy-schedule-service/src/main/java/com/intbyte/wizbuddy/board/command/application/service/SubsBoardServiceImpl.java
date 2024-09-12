package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.command.domain.aggregate.vo.EditSubsBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.SubsBoardRepository;

import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
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

        if (subsBoardDTO.getSubsTitle() == null || subsBoardDTO.getSubsTitle().isEmpty()
                || subsBoardDTO.getSubsContent() == null || subsBoardDTO.getSubsContent().isEmpty()) {
            throw new CommonException(StatusEnum.INVALID_SUBS_BOARD_DATA);
        }

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
    public ResponseModifySubsBoardVO modifySubsBoards(int subsCode, EditSubsBoardVO modifySubsBoardInfo) {

        SubsBoard subsBoard = subsBoardRepository.findById(subsCode)
                .orElseThrow(() -> new CommonException(StatusEnum.BOARD_NOT_FOUND));

        if (modifySubsBoardInfo.getSubsTitle() == null || modifySubsBoardInfo.getSubsTitle().isEmpty()
                || modifySubsBoardInfo.getSubsContent() == null || modifySubsBoardInfo.getSubsContent().isEmpty()) {
            throw new CommonException(StatusEnum.INVALID_SUBS_BOARD_DATA);
        }

        subsBoard.toUpdate(modifySubsBoardInfo);
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseModifySubsBoardVO.class);
    }

    @Transactional
    @Override
    public ResponseDeleteSubsBoardVO deleteSubsBoard(SubsBoardDTO deleteSubsBoardDTO) {
        SubsBoard subsBoard = subsBoardRepository.findById(deleteSubsBoardDTO.getSubsCode())
                .orElseThrow(() -> new CommonException(StatusEnum.BOARD_NOT_FOUND));
        subsBoard.toDelete();
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseDeleteSubsBoardVO.class);
    }

    @Override
    public SubsBoard validateSubsBoard(int subsCode) {
        SubsBoard subsBoard = subsBoardRepository.findBySubsCode(subsCode);
        if (subsBoard == null) {
            throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        }
        return subsBoard;
    }
}
