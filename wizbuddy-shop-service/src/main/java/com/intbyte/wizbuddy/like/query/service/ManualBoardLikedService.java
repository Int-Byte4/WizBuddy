package com.intbyte.wizbuddy.like.query.service;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.query.dto.ManualBoardLikedDTO;
import com.intbyte.wizbuddy.like.query.repository.ManualBoardLikedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("manualBoardLikedQueryService")
public class ManualBoardLikedService {

    private final ManualBoardLikedMapper manualBoardLikedMapper;
    private final ModelMapper mapper;

    public ManualBoardLikedService(ManualBoardLikedMapper manualBoardLikedMapper, ModelMapper mapper) {
        this.manualBoardLikedMapper = manualBoardLikedMapper;
        this.mapper = mapper;
    }

    // 게시글 별 좋아요 조회
    public List<ManualBoardLikedDTO> findLikesByManualCode(int manualCode) {
        List<ManualBoardLiked> ManualBoardLikedList = manualBoardLikedMapper.findLikesByManualCode(manualCode);

        List<ManualBoardLikedDTO> ManualBoardLikedDTOList = new ArrayList<>();
        for (ManualBoardLiked manualBoardLiked : ManualBoardLikedList) {
            ManualBoardLikedDTO manualBoardLikedDTO = mapper.map(manualBoardLiked, ManualBoardLikedDTO.class);

            ManualBoardLikedDTOList.add(manualBoardLikedDTO);
        }
        return ManualBoardLikedDTOList;
    }
}
