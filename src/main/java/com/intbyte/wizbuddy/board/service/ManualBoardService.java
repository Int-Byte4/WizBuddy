package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateManualBoardVO;
import com.intbyte.wizbuddy.exception.manualboard.ManualBoardModifyOtherUserException;
import com.intbyte.wizbuddy.exception.manualboard.ManualBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.ManualBoardMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ManualBoardService {

    private final ManualBoardMapper manualBoardMapper;
    private final ManualBoardRepository manualBoardRepository;
    private final ModelMapper modelMapper;

    public ManualBoardService(ManualBoardRepository manualBoardRepository, ManualBoardMapper manualBoardMapper, ModelMapper modelMapper) {
        this.manualBoardRepository = manualBoardRepository;
        this.manualBoardMapper = manualBoardMapper;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ResponseInsertManualBoardVO registerManualBoard(RequestInsertManualBoardVO manualBoardInfo) {
        ManualBoard manualBoard = ManualBoard.builder()
                .manualTitle(manualBoardInfo.getManualTitle())
                .manualContents(manualBoardInfo.getManualContents())
                .manualFlag(manualBoardInfo.isManualFlag())
                .imageUrl(manualBoardInfo.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(manualBoardInfo.getShopCode())
                .userCode(manualBoardInfo.getUserCode())
                .build();

        ManualBoard savedManualBoard = manualBoardRepository.save(manualBoard);

        ResponseInsertManualBoardVO vo = ResponseInsertManualBoardVO.builder()
                .manualTitle(savedManualBoard.getManualTitle())
                .manualContents(savedManualBoard.getManualContents())
                .manualFlag(savedManualBoard.isManualFlag())
                .imageUrl(savedManualBoard.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(savedManualBoard.getShopCode())
                .userCode(savedManualBoard.getUserCode())
                .build();

        return vo;
    }

    @Transactional
    public ResponseUpdateManualBoardVO modifyManualBoard(int manualCode, EditManualBoardInfo modifyManualBoardInfo) {
        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);

        if(writerCode != null && writerCode.equals(modifyManualBoardInfo.getUserCode())) {
            ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);
            manualBoard.modify(modifyManualBoardInfo);
            manualBoardRepository.save(manualBoard);
            ResponseUpdateManualBoardVO vo = ResponseUpdateManualBoardVO.builder()
                    .manualTitle(manualBoard.getManualTitle())
                    .manualContents(manualBoard.getManualContents())
                    .manualFlag(manualBoard.isManualFlag())
                    .imageUrl(manualBoard.getImageUrl())
                    .updatedAt(LocalDateTime.now())
                    .build();
            return vo;
        } else{
            throw new ManualBoardModifyOtherUserException();
        }
    }

    @Transactional
    public void deleteManualBoard(int manualCode, DeleteManualBoardInfo deleteManualBoardInfo) {

        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);

        if(writerCode != null && writerCode.equals(deleteManualBoardInfo.getUserCode())) {
            ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);
            manualBoard.delete(deleteManualBoardInfo);

            manualBoardRepository.save(manualBoard);
        } else {
            throw new ManualBoardModifyOtherUserException();
        }
    }

    @Transactional
    public List<ManualBoardDTO> findAllManualBoards() {
        List<ManualBoard> manualBoardList = manualBoardMapper.findAllManualBoards();

        if(manualBoardList == null || manualBoardList.isEmpty()) throw new ManualBoardNotFoundException();
        return manualBoardList.stream()
                .map(manualBoard -> modelMapper.map(manualBoard, ManualBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ManualBoardDTO> findManualBoardByShopCode(int shopCode) {
        List<ManualBoard> manualBoardList = manualBoardMapper.findManualBoardByShopCode(shopCode);

        return manualBoardList.stream()
                .map(manualBoard -> modelMapper.map(manualBoard, ManualBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ManualBoardDTO findManualBoardByManualCode(int manualCode) {
        ManualBoard manualBoard = manualBoardMapper.findManualBoardByManualCode(manualCode);

        ManualBoardDTO manualBoardDTO = modelMapper.map(manualBoard, ManualBoardDTO.class);

        return manualBoardDTO;
    }
}
