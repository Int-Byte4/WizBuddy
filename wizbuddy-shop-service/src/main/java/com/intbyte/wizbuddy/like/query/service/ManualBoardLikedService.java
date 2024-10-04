package com.intbyte.wizbuddy.like.query.service;

import com.intbyte.wizbuddy.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.query.repository.ManualBoardLikedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("manualBoardLikedQueryService")
public class ManualBoardLikedService {

    private final ManualBoardLikedMapper manualBoardLikedMapper;
    private final ModelMapper mapper;
    private final UserServiceClient userServiceClient;

    public ManualBoardLikedService(ManualBoardLikedMapper manualBoardLikedMapper, ModelMapper mapper, UserServiceClient userServiceClient) {
        this.manualBoardLikedMapper = manualBoardLikedMapper;
        this.mapper = mapper;
        this.userServiceClient = userServiceClient;
    }

    // 게시글 별 좋아요 누른 직원 정보 조회
    public List<Map<String, Object>> findLikesByManualCode(int manualCode) {
        List<ManualBoardLiked> manualBoardLikedList = manualBoardLikedMapper.findLikesByManualCode(manualCode);

        List<Map<String, Object>> userDTOList = new ArrayList<>();

        for (ManualBoardLiked manualBoardLiked : manualBoardLikedList) {
            Map<String, Object> userDetail = userServiceClient.getEmployee(manualBoardLiked.getEmployeeCode()).getBody();

            userDTOList.add(userDetail);
        }

        return userDTOList;
    }
}
