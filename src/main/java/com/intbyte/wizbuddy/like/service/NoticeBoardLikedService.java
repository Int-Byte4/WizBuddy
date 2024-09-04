package com.intbyte.wizbuddy.like.service;

import com.intbyte.wizbuddy.like.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.dto.NoticeBoardLikedDTO;
import com.intbyte.wizbuddy.like.repository.NoticeBoardLikedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeBoardLikedService {

    private final NoticeBoardLikedRepository noticeBoardLikedRepository;


    public NoticeBoardLikedService(NoticeBoardLikedRepository noticeBoardLikedRepository) {
        this.noticeBoardLikedRepository = noticeBoardLikedRepository;
    }

    /* 기능. 1. 공지사항 게시글 좋아요 추가 */
    @Transactional
    public void registerNoticeBoardLike(NoticeBoardLikedDTO noticeBoardLikedInfo) {

        NoticeBoardLiked noticeBoardLiked = NoticeBoardLiked.builder()
                                           .noticeLikedCode(noticeBoardLikedInfo.getNoticeCode())
                                           .createdAt(LocalDateTime.now())
                                           .employeeCode(noticeBoardLikedInfo.getEmployeeCode())
                                           .shopCode(noticeBoardLikedInfo.getShopCode())
                                           .noticeCode(noticeBoardLikedInfo.getNoticeCode())
                                           .build();

        noticeBoardLikedRepository.save(noticeBoardLiked);
    }
}
