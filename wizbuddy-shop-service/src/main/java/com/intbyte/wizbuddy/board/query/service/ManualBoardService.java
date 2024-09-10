package com.intbyte.wizbuddy.board.query.service;

import com.intbyte.wizbuddy.board.command.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;
import com.intbyte.wizbuddy.board.query.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.query.repository.ManualBoardMapper;
import com.intbyte.wizbuddy.board.command.domain.repository.ManualBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("manualBoardQueryService")
public class ManualBoardService {

    private final ManualBoardMapper manualBoardMapper;
    private final ManualBoardRepository manualBoardRepository;
    private final ModelMapper mapper;

    @Autowired
    public ManualBoardService(ManualBoardMapper manualBoardMapper, ManualBoardRepository manualBoardRepository, ModelMapper mapper) {
        this.manualBoardMapper = manualBoardMapper;
        this.manualBoardRepository = manualBoardRepository;
        this.mapper = mapper;
    }

    // 매뉴얼 게시판 전체 조회
    public List<ManualBoardDTO> findAllManualBoards() {
        List<ManualBoardDTO> manualBoardList = new ArrayList<>();

        if (manualBoardMapper.findAllManualBoards() == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);

        for (ManualBoard manualBoard : manualBoardMapper.findAllManualBoards()) {
            ManualBoardDTO manualBoardDTO = mapper.map(manualBoard, ManualBoardDTO.class);

            manualBoardList.add(manualBoardDTO);
        }

        return manualBoardList;
    }

    // 특정 매장의 전체 매뉴얼 게시글 조회
    public List<ManualBoardDTO> findManualBoardByShopCode(int shopCode) {
        List<ManualBoard> manualBoardList = manualBoardMapper.findManualBoardByShopCode(shopCode);

        if (manualBoardList == null) {
            throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        } else {
            List<ManualBoardDTO> manualBoardDTOList = new ArrayList<>();

            for (ManualBoard manualBoard : manualBoardMapper.findManualBoardByShopCode(shopCode)) {
                ManualBoardDTO manualBoardDTO = mapper.map(manualBoard, ManualBoardDTO.class);

                manualBoardDTOList.add(manualBoardDTO);
            }

            return manualBoardDTOList;
        }
    }

    // 매뉴얼 게시글 단 건 조회
    public ManualBoardDTO findManualBoardByManualCode(int manualCode) {
        ManualBoard manualBoard = manualBoardMapper.findManualBoardByManualCode(manualCode);

        if (manualBoard == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);

        return mapper.map(manualBoard, ManualBoardDTO.class);
    }
}
