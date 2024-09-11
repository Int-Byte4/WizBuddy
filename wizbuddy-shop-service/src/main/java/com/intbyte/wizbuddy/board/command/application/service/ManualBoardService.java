package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditManualBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;
import com.intbyte.wizbuddy.board.query.repository.ManualBoardMapper;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseInsertManualBoardVO;
import com.intbyte.wizbuddy.board.command.domain.entity.vo.response.ResponseUpdateManualBoardVO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service("manualBoardCommandService")
public class ManualBoardService {

    private final ManualBoardMapper manualBoardMapper;
    private final ManualBoardRepository manualBoardRepository;
    private final ModelMapper modelMapper;

    public ManualBoardService(ManualBoardRepository manualBoardRepository, ManualBoardMapper manualBoardMapper, ModelMapper modelMapper) {
        this.manualBoardRepository = manualBoardRepository;
        this.manualBoardMapper = manualBoardMapper;
        this.modelMapper = modelMapper;
    }

    // 매뉴얼 게시판 게시글 등록
    @Transactional
    public ResponseInsertManualBoardVO registerManualBoard(RequestInsertManualBoardVO requestInsertManualBoardVO) {
        ManualBoard manualBoard = modelMapper.map(requestInsertManualBoardVO, ManualBoard.class);

        manualBoardRepository.save(manualBoard);

        return modelMapper.map(manualBoard, ResponseInsertManualBoardVO.class);
    }

    // 매뉴얼 게시판 게시글 수정
    @Transactional
    public ResponseUpdateManualBoardVO modifyManualBoard(int manualCode, RequestEditManualBoardDTO requestEditManualBoardDTO) {
        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);
        String userCode = requestEditManualBoardDTO.getUserCode();
        ManualBoard manualBoard = manualBoardMapper.findManualBoardByManualCode(manualCode);

        if(manualBoard == null) throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        if(!userCode.equals(writerCode)) throw new CommonException(StatusEnum.RESTRICTED);

        manualBoard.modify(requestEditManualBoardDTO);
        manualBoardRepository.save(manualBoard);

        return modelMapper.map(manualBoard, ResponseUpdateManualBoardVO.class);
    }

    // 매뉴얼 게시판 게시글 삭제
    @Transactional
    public void deleteManualBoard(int manualCode, String userCode) {
        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);
        ManualBoard manualBoard = manualBoardMapper.findManualBoardByManualCode(manualCode);

        if(!userCode.equals(writerCode)) throw new CommonException(StatusEnum.RESTRICTED);

        manualBoard.delete();

        manualBoardRepository.save(manualBoard);
    }
}
