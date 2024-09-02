package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.board.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.board.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.mapper.ManualBoardLikedMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ManualBoardLikedService {

    private final ManualBoardLikedRepository manualBoardLikedRepository;

    public ManualBoardLikedService(ManualBoardLikedRepository manualBoardLikedRepository) {
        this.manualBoardLikedRepository = manualBoardLikedRepository;
    }

    /* 기능. 1. 매뉴얼 게시글 좋아요 추가 */
    @Transactional
    public void registerManualBoardLike(ManualBoardLikedDTO manualBoardLikedInfo) {

        ManualBoardLiked manualBoardLiked = ManualBoardLiked.builder()
                .manualLikedCode(manualBoardLikedInfo.getManualLikedCode())
                .createdAt(LocalDateTime.now())
                .manualCode(manualBoardLikedInfo.getManualCode())
                .shopCode(manualBoardLikedInfo.getShopCode())
                .employeeCode(manualBoardLikedInfo.getEmployeeCode())
                .build();

        manualBoardLikedRepository.save(manualBoardLiked);
    }


}
