package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindManualBoardVO;
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

    /* 기능. 1. 매뉴얼 게시판 게시글 등록 */
    @Transactional
    public ResponseInsertManualBoardVO registerManualBoard(RequestInsertManualBoardVO manualBoardInfo) {
        // DTO -> Entity 변환 및 추가 설정
        ManualBoard manualBoard = ManualBoard.builder()
                .manualTitle(manualBoardInfo.getManualTitle())
                .manualContents(manualBoardInfo.getManualContents())
                .manualFlag(manualBoardInfo.isManualFlag())  // 전달된 값으로 설정
                .imageUrl(manualBoardInfo.getImageUrl())
                .createdAt(LocalDateTime.now())  // 생성 시간 설정
                .updatedAt(LocalDateTime.now())  // 수정 시간 설정
                .shopCode(manualBoardInfo.getShopCode())
                .userCode(manualBoardInfo.getUserCode())
                .build();

        // 엔티티 저장
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

        // 저장된 엔티티 -> Response DTO 변환
        return vo;
    }

    /* 기능. 2. 매뉴얼 게시판 게시글 수정 */
    @Transactional
    public ResponseUpdateManualBoardVO modifyManualBoard(int manualCode, EditManualBoardInfo modifyManualBoardInfo) {
        // 유저가 매뉴얼 게시글 작성자인지 확인
        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);

        System.out.println("writerCode = " + writerCode);
        System.out.println("modifyManualBoardInfo.getUserCode() = " + modifyManualBoardInfo.getUserCode());
        
        if(writerCode != null && writerCode.equals(modifyManualBoardInfo.getUserCode())) {
            // 게시글 코드로 해당 게시글이 레포지토리에 존재하는지 확인
            ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);

            // 매뉴얼 게시글 제목, 내용, 이미지 url, 수정 날짜 수정
            manualBoard.modify(modifyManualBoardInfo);

            // 변경된 객체를 repository에 저장
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

    /* 기능. 3. 매뉴얼 게시판 게시글 삭제 */
    @Transactional
    public void deleteManualBoard(int manualCode, DeleteManualBoardInfo deleteManualBoardInfo) {

        String writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);

        if(writerCode != null && writerCode.equals(deleteManualBoardInfo.getUserCode())) {

            // 게시글 코드로 해당 게시글이 레포지토리에 존재하는지 확인
            ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);

            manualBoard.delete(deleteManualBoardInfo);

            // 변경된 객체를 repository에 저장
            manualBoardRepository.save(manualBoard);

        } else {
            throw new ManualBoardModifyOtherUserException();
        }
    }

    /* 기능 4. 매뉴얼 게시판 전체 게시글 조회 */
    @Transactional
    public List<ManualBoardDTO> findAllManualBoards() {
        List<ManualBoard> manualBoardList = manualBoardMapper.findAllManualBoards();

        if(manualBoardList == null || manualBoardList.isEmpty()) throw new ManualBoardNotFoundException();
        return manualBoardList.stream()
                .map(manualBoard -> modelMapper.map(manualBoard, ManualBoardDTO.class))
                .collect(Collectors.toList());
    }

    // 기능 5. 매장별 매뉴얼 게시판 게시글 조회
    @Transactional
    public List<ManualBoardDTO> findManualBoardByShopCode(int shopCode) {
        List<ManualBoard> manualBoardList = manualBoardMapper.findManualBoardByShopCode(shopCode);

        return manualBoardList.stream()
                .map(manualBoard -> modelMapper.map(manualBoard, ManualBoardDTO.class))
                .collect(Collectors.toList());
    }

    /* 기능 6. 매뉴얼 게시글 단 건 조회 */
    @Transactional
    public ManualBoardDTO findManualBoardByManualCode(int manualCode) {
        ManualBoard manualBoard = manualBoardMapper.findManualBoardByManualCode(manualCode);

        ManualBoardDTO manualBoardDTO = modelMapper.map(manualBoard, ManualBoardDTO.class);

        return manualBoardDTO;
    }
}
