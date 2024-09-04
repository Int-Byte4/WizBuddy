package com.intbyte.wizbuddy.like.service;

import com.intbyte.wizbuddy.exception.board.LikeDuplicateException;
import com.intbyte.wizbuddy.like.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.like.vo.response.ResponseInsertManualBoardLikeVO;
import com.intbyte.wizbuddy.mapper.ManualBoardLikedMapper;
import com.intbyte.wizbuddy.mapper.ManualBoardMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManualBoardLikedService {

    private final ManualBoardLikedRepository manualBoardLikedRepository;
    private final ManualBoardLikedMapper manualBoardLikedMapper;

    private final ModelMapper modelMapper;

    @Transactional
    public ResponseInsertManualBoardLikeVO registerManualBoardLike(ManualBoardLikedDTO manualBoardLikedInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put("manualCode", manualBoardLikedInfo.getManualCode());
        params.put("employeeCode", manualBoardLikedInfo.getEmployeeCode());

        if (manualBoardLikedMapper.getManualBoardLikedByUserCode(params) != null) throw new LikeDuplicateException();

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
