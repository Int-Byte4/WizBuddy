package com.intbyte.wizbuddy.board.controller;

import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.service.ManualBoardService;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindManualBoardVO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manualboard")
public class ManualBoardController {

    private final ManualBoardService manualBoardService;
    private final ModelMapper modelMapper;

    public ManualBoardController(ManualBoardService manualBoardService, ModelMapper modelMapper) {
        this.manualBoardService = manualBoardService;
        this.modelMapper = modelMapper;
    }

    // 1. 매뉴얼 게시판 전체 조회
    @GetMapping
    public ResponseEntity<List<ResponseFindManualBoardVO>> getAllManualBoards() {
        List<ManualBoardDTO> manualBoardDTOS = manualBoardService.findAllManualBoards();

        List<ResponseFindManualBoardVO> manualBoardVOs = manualBoardDTOS.stream()
                .map(dto -> ResponseFindManualBoardVO.builder()
                        .manualCode(dto.getManualCode())
                        .manualTitle(dto.getManualTitle())
                        .manualContents(dto.getManualContents())
                        .manualFlag(dto.isManualFlag())
                        .imageUrl(dto.getImageUrl())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .shopCode(dto.getShopCode())
                        .userCode(dto.getUserCode())
                        .build())
                .collect(Collectors.toList());

                return ResponseEntity.ok(manualBoardVOs);
    }



}
