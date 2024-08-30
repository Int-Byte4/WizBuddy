package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardRepository;
import com.intbyte.wizbuddy.mapper.ManualBoardMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Transactional
public class ManualBoardService {

    private ManualBoardMapper manualBoardMapper;
    private final ManualBoardRepository manualBoardRepository;

    public ManualBoardService(ManualBoardRepository manualBoardRepository, ManualBoardMapper manualBoardMapper) {
        this.manualBoardRepository = manualBoardRepository;
        this.manualBoardMapper = manualBoardMapper;
    }

    // 매뉴얼 게시판 게시글 등록
    public void registerManualBoard(ManualBoardDTO manualBoardInfo) {
        int shopCode = manualBoardInfo.getShop_code();
        int userCode = manualBoardInfo.getUser_code();

        // 예외처리 추가 필요
        ManualBoard manualBoard = ManualBoard.builder()
                .manual_title(manualBoardInfo.getManual_title())
                .manual_contents(manualBoardInfo.getManual_contents())
                .manual_flag(true)
                .image_url(manualBoardInfo.getImage_url())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .shop_code(shopCode)
                .user_code(userCode)
                .build();

        manualBoardRepository.save(manualBoard);
    }

}
