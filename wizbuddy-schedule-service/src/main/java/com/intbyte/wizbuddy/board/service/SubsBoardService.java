package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import com.intbyte.wizbuddy.exception.board.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.SubsBoardMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubsBoardService {

    private final SubsBoardMapper subsBoardMapper;
    private final ModelMapper modelMapper;
    private final SubsBoardRepository subsBoardRepository;


    public List<SubsBoardDTO> findAllSubsBoards() {
        List<SubsBoard> subsBoardList = subsBoardMapper.selectAllSubsBoard();
        if (subsBoardList == null || subsBoardList.isEmpty() ) {throw new SubsBoardNotFoundException();}
        return subsBoardList.stream()
                .map(subsBoard -> modelMapper.map(subsBoard, SubsBoardDTO.class))
                .collect(Collectors.toList());
    }

    public SubsBoardDTO findSubsBoardById(int subsCode) {
        SubsBoard subsBoard = subsBoardMapper.selectSubsBoardById(subsCode);
        if (subsBoard == null) {throw new SubsBoardNotFoundException();}
        return modelMapper.map(subsBoard, SubsBoardDTO.class);
    }

    public List<SubsBoardDTO> getSubsBoardsByShopCode(int shopCode) {
        List<SubsBoard> subsBoards = subsBoardMapper.selectSubsBoardByShopCode(shopCode);
        if (subsBoards == null || subsBoards.isEmpty()) {throw new SubsBoardNotFoundException();}
        return subsBoards.stream()
                .map(subsBoard -> modelMapper.map(subsBoard, SubsBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseInsertSubsBoardVO registSubsBoard(SubsBoardDTO subsBoards) {
        SubsBoard subsBoard = SubsBoard.builder()
                .subsTitle(subsBoards.getSubsTitle())
                .subsContent(subsBoards.getSubsContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subsFlag(true)
                .employeeWorkingPartCode(subsBoards.getEmployeeWorkingPartCode())
                .shopCode(subsBoards.getShopCode())
                .build();

        subsBoardRepository.save(subsBoard);

        return modelMapper.map(subsBoard, ResponseInsertSubsBoardVO.class);
    }

    @Transactional
    public ResponseModifySubsBoardVO modifySubsBoards(int subsCode, EditSubsBoardInfo modifysubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(subsCode).orElseThrow(SubsBoardNotFoundException::new);
        subsBoard.toUpdate(modifysubsBoard);
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseModifySubsBoardVO.class);
    }

    @Transactional
    public ResponseDeleteSubsBoardVO deleteSubsBoard(SubsBoardDTO deleteSubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(deleteSubsBoard.getSubsCode())
                .orElseThrow(SubsBoardNotFoundException::new);
        subsBoard.toDelete();
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseDeleteSubsBoardVO.class);
    }

}
