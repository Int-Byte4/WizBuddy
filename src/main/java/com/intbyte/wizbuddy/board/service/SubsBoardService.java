package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.exception.board.SubsBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.SubsBoardMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubsBoardService {

    private final SubsBoardMapper subsBoardMapper;
    private final ModelMapper modelMapper;
    private final SubsBoardRepository subsBoardRepository;

    @Autowired
    public SubsBoardService(SubsBoardMapper subsBoardMapper, ModelMapper modelMapper, SubsBoardRepository subsBoardRepository) {
        this.subsBoardMapper = subsBoardMapper;
        this.modelMapper = modelMapper;
        this.subsBoardRepository = subsBoardRepository;
    }



    public List<SubsBoardDTO> findAllSubsBoards() {
        List<SubsBoard> subsBoardList = subsBoardMapper.selectAllSubsBoard();

        return subsBoardList.stream()
                .map(subsBoard -> modelMapper.map(subsBoard, SubsBoardDTO.class))
                .collect(Collectors.toList());
    }


    public SubsBoardDTO findSubsBoardById(int subsCode) {
        SubsBoard subsBoard = subsBoardMapper.selectSubsBoardById(subsCode);

        if (subsBoard == null) {
            throw new SubsBoardNotFoundException();
        }

        return modelMapper.map(subsBoard, SubsBoardDTO.class);
    }


    @Transactional
    public void registSubsBoard(SubsBoardDTO subsBoards) {
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

    }

    @Transactional
    public void modifySubsBoards(int subsCode, EditSubsBoardInfo modifysubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(subsCode).orElseThrow(SubsBoardNotFoundException::new);
        subsBoard.toUpdate(modifysubsBoard);
        subsBoardRepository.save(subsBoard);
    }

    @Transactional
    public void deleteSubsBoard(SubsBoardDTO deleteSubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(deleteSubsBoard.getSubsCode()).orElseThrow(SubsBoardNotFoundException::new);
        subsBoard.toDelete();
        subsBoardRepository.save(subsBoard);
    }

}
