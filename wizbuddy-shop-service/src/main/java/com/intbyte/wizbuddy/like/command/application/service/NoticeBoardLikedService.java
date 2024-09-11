package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.command.application.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.command.domain.repository.NoticeBoardLikedRepository;
import com.intbyte.wizbuddy.like.command.domain.entity.vo.response.ResponseInsertNoticeBoardLikeVO;
import com.intbyte.wizbuddy.like.query.repository.NoticeBoardLikedMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeBoardLikedService {

    private final NoticeBoardLikedRepository noticeBoardLikedRepository;

    private final NoticeBoardLikedMapper noticeBoardLikedMapper;

    private final ModelMapper modelMapper;

    @Transactional
    public ResponseInsertNoticeBoardLikeVO registerNoticeBoardLike(NoticeBoardLikedDTO noticeBoardLikedInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put("noticeCode", noticeBoardLikedInfo.getNoticeCode());
        params.put("employeeCode", noticeBoardLikedInfo.getEmployeeCode());

        if (noticeBoardLikedMapper.getNoticeBoardLikedByUserCode(params) != null) throw new CommonException(StatusEnum.ALREADY_PUSH_LIKED);

        NoticeBoardLiked noticeBoardLiked = NoticeBoardLiked.builder()
                                           .noticeLikedCode(noticeBoardLikedInfo.getNoticeCode())
                                           .createdAt(LocalDateTime.now())
                                           .employeeCode(noticeBoardLikedInfo.getEmployeeCode())
                                           .shopCode(noticeBoardLikedInfo.getShopCode())
                                           .noticeCode(noticeBoardLikedInfo.getNoticeCode())
                                           .build();

        noticeBoardLikedRepository.save(noticeBoardLiked);
        return modelMapper.map(noticeBoardLiked, ResponseInsertNoticeBoardLikeVO.class);
    }
}