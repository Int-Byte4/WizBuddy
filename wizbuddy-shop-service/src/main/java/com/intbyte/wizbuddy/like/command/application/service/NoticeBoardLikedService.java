package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.board.common.exception.CommonException;
import com.intbyte.wizbuddy.board.common.exception.StatusEnum;
import com.intbyte.wizbuddy.like.command.domain.entity.NoticeBoardLiked;
import com.intbyte.wizbuddy.like.command.domain.repository.NoticeBoardLikedRepository;
import com.intbyte.wizbuddy.like.command.infrastructure.service.NoticeBoardLikedInfraStructureService;
import com.intbyte.wizbuddy.like.query.repository.NoticeBoardLikedMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeBoardLikedService {

    private final NoticeBoardLikedRepository noticeBoardLikedRepository;
    private final NoticeBoardLikedMapper noticeBoardLikedMapper;
    private final NoticeBoardLikedInfraStructureService noticeBoardQueryService;

    @Autowired
    public NoticeBoardLikedService(NoticeBoardLikedRepository noticeBoardLikedRepository,
                                   NoticeBoardLikedMapper noticeBoardLikedMapper,
                                   NoticeBoardLikedInfraStructureService noticeBoardQueryService) {
        this.noticeBoardLikedRepository = noticeBoardLikedRepository;
        this.noticeBoardLikedMapper = noticeBoardLikedMapper;
        this.noticeBoardQueryService = noticeBoardQueryService;
    }

    // 좋아요 등록
    @Transactional
    public void registerNoticeBoardLike(int noticeCode, String employeeCode) {
        String likedEmployeeCode = noticeBoardLikedMapper.findEmployeeCodeByNoticeCode(noticeCode);

        if (likedEmployeeCode.equals(employeeCode)) throw new CommonException(StatusEnum.ALREADY_PUSH_LIKED);

        int noticeLikedCode = noticeBoardLikedRepository.findAll().size() + 1;

        NoticeBoardLiked noticeBoardLiked = new NoticeBoardLiked(
                noticeLikedCode,
                LocalDateTime.now(),
                noticeCode,
                noticeBoardQueryService.findNoticeBoardByNoticeCode(noticeCode).getShopCode(),
                employeeCode
        );

        noticeBoardLikedRepository.save(noticeBoardLiked);
    }
}