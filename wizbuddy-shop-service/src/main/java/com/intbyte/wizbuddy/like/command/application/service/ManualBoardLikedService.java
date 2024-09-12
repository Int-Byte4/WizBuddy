package com.intbyte.wizbuddy.like.command.application.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import com.intbyte.wizbuddy.like.command.domain.repository.ManualBoardLikedRepository;
import com.intbyte.wizbuddy.like.command.infrastructure.service.ManualBoardLikedInfraStructureService;
import com.intbyte.wizbuddy.like.query.repository.ManualBoardLikedMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ManualBoardLikedService {

    private final ManualBoardLikedRepository manualBoardLikedRepository;
    private final ManualBoardLikedMapper manualBoardLikedMapper;
    private final ManualBoardLikedInfraStructureService manualBoardQueryService;


    @Autowired
    public ManualBoardLikedService(ManualBoardLikedMapper manualBoardLikedMapper,
                                   ManualBoardLikedRepository manualBoardLikedRepository,
                                   ManualBoardLikedInfraStructureService manualBoardQueryService) {
        this.manualBoardLikedMapper = manualBoardLikedMapper;
        this.manualBoardLikedRepository = manualBoardLikedRepository;
        this.manualBoardQueryService = manualBoardQueryService;
    }

    // 좋아요 등록
    @Transactional
    public void registerManualBoardLike(int manualCode, String employeeCode) {
        String likedEmployeeCode = manualBoardLikedMapper.findEmployeeCodeByManualCode(manualCode);

        if (likedEmployeeCode.equals(employeeCode)) throw new CommonException(StatusEnum.ALREADY_PUSH_LIKED);

        int manualLikedCode = manualBoardLikedRepository.findAll().size() + 1;

        ManualBoardLiked manualBoardLiked = new ManualBoardLiked(
                manualLikedCode,
                LocalDateTime.now(),
                manualCode,
                manualBoardQueryService.findManualBoardByManualCode(manualCode).getShopCode(),
                employeeCode
        );

        manualBoardLikedRepository.save(manualBoardLiked);
    }
}
