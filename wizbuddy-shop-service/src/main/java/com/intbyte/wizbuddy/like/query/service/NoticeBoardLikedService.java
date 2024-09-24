package com.intbyte.wizbuddy.like.query.service;

import com.intbyte.wizbuddy.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.query.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.query.repository.NoticeBoardLikedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("noticeBoardLikedQueryService")
public class NoticeBoardLikedService {

    private final NoticeBoardLikedMapper noticeBoardLikedMapper;
    private final ModelMapper mapper;
    private final UserServiceClient userServiceClient;

    public NoticeBoardLikedService(NoticeBoardLikedMapper noticeBoardLikedMapper, ModelMapper mapper, @Qualifier("com.intbyte.wizbuddy.infrastructure.client.UserServiceClient") UserServiceClient userServiceClient) {
        this.noticeBoardLikedMapper = noticeBoardLikedMapper;
        this.mapper = mapper;
        this.userServiceClient = userServiceClient;
    }

    // 게시글 별 좋아요 누른 직원 정보 조회
    public List<Map<String, Object>> findLikesByNoticeCode(int noticeCode) {
        List<NoticeBoardLiked> noticeBoardLikedList = noticeBoardLikedMapper.findLikesByNoticeCode(noticeCode);

        List<Map<String, Object>> userDTOList = new ArrayList<>();

        for (NoticeBoardLiked noticeBoardLiked : noticeBoardLikedList) {
            Map<String, Object> userDetail = userServiceClient.getEmployee(noticeBoardLiked.getEmployeeCode()).getBody();

            userDTOList.add(userDetail);
        }

        return userDTOList;
    }
}
