package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.exception.manualboard.ManualBoardModifyOtherUserException;
import com.intbyte.wizbuddy.exception.manualboard.ManualBoardNotFoundException;
import com.intbyte.wizbuddy.mapper.ManualBoardMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class ManualBoardService {

    private final ManualBoardMapper manualBoardMapper;
    private final ManualBoardRepository manualBoardRepository;

    public ManualBoardService(ManualBoardRepository manualBoardRepository, ManualBoardMapper manualBoardMapper) {
        this.manualBoardRepository = manualBoardRepository;
        this.manualBoardMapper = manualBoardMapper;
    }

    /* 기능. 1. 매뉴얼 게시판 게시글 등록 */
    @Transactional
    public void registerManualBoard(ManualBoardDTO manualBoardInfo) {
        int shopCode = manualBoardInfo.getShopCode();
        int userCode = manualBoardInfo.getUserCode();

        // 예외처리 추가 필요
        ManualBoard manualBoard = ManualBoard.builder()
                .manualCode(manualBoardInfo.getManualCode())
                .manualContents(manualBoardInfo.getManualContents())
                .manualFlag(true)
                .imageUrl(manualBoardInfo.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(shopCode)
                .userCode(userCode)
                .build();

        manualBoardRepository.save(manualBoard);
    }

    /* 기능. 2. 매뉴얼 게시판 게시글 수정 */
    @Transactional
    public void modifyManualBoard(int manualCode, int requestUserCode, EditManualBoardInfo modifyManualBoardInfo) {
        // 유저가 매뉴얼 게시글 작성자인지 확인
        int writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);
        if (writerCode != requestUserCode) throw new ManualBoardModifyOtherUserException();

        // 게시글 코드로 해당 게시글이 레포지토리에 존재하는지 확인
        ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);

        // 매뉴얼 게시글 제목, 내용, 이미지 url, 수정 날짜 수정
        manualBoard.modify(modifyManualBoardInfo);

        // 변경된 객체를 repository에 저장
        manualBoardRepository.save(manualBoard);
    }

    /* 기능. 3. 매뉴얼 게시판 게시글 삭제 */
    @Transactional
    public void deleteManualBoard(int manualCode, int requestUserCode, DeleteManualBoardInfo deleteManualBoardInfo) {
        // 유저가 매뉴얼 게시글 작성자인지 확인
        int writerCode = manualBoardMapper.findUserCodeByManualCode(manualCode);
        if (writerCode != requestUserCode) throw new ManualBoardModifyOtherUserException();

        // 게시글 코드로 해당 게시글이 레포지토리에 존재하는지 확인
        ManualBoard manualBoard = manualBoardRepository.findById(manualCode).orElseThrow(ManualBoardNotFoundException::new);

        // 게시글 상태, 수정 날짜 수정
        manualBoard.delete(deleteManualBoardInfo);

        // 변경된 객체를 repository에 저장
        manualBoardRepository.save(manualBoard);
    }

}
