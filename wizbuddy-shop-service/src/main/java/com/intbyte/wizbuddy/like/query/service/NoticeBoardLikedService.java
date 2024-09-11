package com.intbyte.wizbuddy.like.query.service;

import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.query.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.query.repository.NoticeBoardLikedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("noticeBoardLikedQueryService")
public class NoticeBoardLikedService {

    private final NoticeBoardLikedMapper noticeBoardLikedMapper;
    private final ModelMapper mapper;

    public NoticeBoardLikedService(NoticeBoardLikedMapper noticeBoardLikedMapper, ModelMapper mapper) {
        this.noticeBoardLikedMapper = noticeBoardLikedMapper;
        this.mapper = mapper;
    }

    // 게시글 별 좋아요 조회
    public List<NoticeBoardLikedDTO> findLikesByNoticeCode(int noticeCode) {
        List<NoticeBoardLiked> NoticeBoardLikedList = noticeBoardLikedMapper.findLikesByNoticeCode(noticeCode);

        List<NoticeBoardLikedDTO> NoticeBoardLikedDTOList = new ArrayList<>();
        for (NoticeBoardLiked noticeBoardLiked : NoticeBoardLikedList) {
            NoticeBoardLikedDTO noticeBoardLikedDTO = mapper.map(noticeBoardLiked, NoticeBoardLikedDTO.class);

            NoticeBoardLikedDTOList.add(noticeBoardLikedDTO);
        }
        return NoticeBoardLikedDTOList;
    }
}
