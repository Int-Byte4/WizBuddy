package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.repository.SubsBoardRepository;
import com.intbyte.wizbuddy.mapper.SubsBoardMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalArgumentException("해당 subsCode에 대한 게시판 항목을 찾을 수 없습니다.");
        }

        return modelMapper.map(subsBoard, SubsBoardDTO.class);
    }


    @Transactional
    public SubsBoard registSubsBoard(SubsBoardDTO subsBoard) {
        return subsBoardRepository.save(modelMapper.map(subsBoard, SubsBoard.class));
    }

    @Transactional
    public SubsBoard modifySubsBoards(SubsBoardDTO modifysubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(modifysubsBoard.getSubsCode()).orElseThrow(IllegalArgumentException::new);
        subsBoard.toUpdate(modifysubsBoard);
        subsBoardRepository.save(modelMapper.map(subsBoard, SubsBoard.class));
        return subsBoard;
    }

    @Transactional
    public SubsBoard deleteSubsBoard(SubsBoardDTO deletesubsBoard) {
        SubsBoard subsBoard = subsBoardRepository.findById(deletesubsBoard.getSubsCode()).orElseThrow(IllegalArgumentException::new);
        subsBoard.modifyFlag();
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, SubsBoard.class);
    }



}
