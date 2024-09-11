package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;
import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.command.application.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.command.domain.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.response.ResponseInsertManualBoardLikeVO;
import com.intbyte.wizbuddy.like.query.repository.ManualBoardLikedMapper;
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

        if (manualBoardLikedMapper.getManualBoardLikedByUserCode(params) != null) throw new CommonException(StatusEnum.ALREADY_PUSH_LIKED);

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
