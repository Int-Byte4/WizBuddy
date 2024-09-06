package com.intbyte.wizbuddy.like.service;

import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.like.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.like.vo.response.ResponseInsertManualBoardLikeVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ManualBoardLikedService {

    private final ManualBoardLikedRepository manualBoardLikedRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public ResponseInsertManualBoardLikeVO registerManualBoardLike(ManualBoardLikedDTO manualBoardLikedInfo) {

        ManualBoardLiked manualBoardLiked = ManualBoardLiked.builder()
                .manualLikedCode(manualBoardLikedInfo.getManualLikedCode())
                .createdAt(LocalDateTime.now())
                .manualCode(manualBoardLikedInfo.getManualCode())
                .shopCode(manualBoardLikedInfo.getShopCode())
                .employeeCode(manualBoardLikedInfo.getEmployeeCode())
                .build();

        manualBoardLikedRepository.save(manualBoardLiked);
        return modelMapper.map(manualBoardLiked, ResponseInsertManualBoardLikeVO.class);
    }


}
