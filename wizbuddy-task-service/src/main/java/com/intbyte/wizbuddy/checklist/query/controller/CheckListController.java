package com.intbyte.wizbuddy.checklist.query.controller;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListQueryDTO;
import com.intbyte.wizbuddy.checklist.query.service.CheckListService;
import com.intbyte.wizbuddy.checklist.query.vo.ResponseFindCheckListVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("checkListQueryController")
public class CheckListController {

    private final CheckListService checkListService;

    @Autowired
    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    // 1. 특정 checkList 조회
    @GetMapping("/checklist/{checkListcode}")
    @Operation(summary = "특정 체크리스트 조회")
    public ResponseEntity<ResponseFindCheckListVO> getCheckListById(
            @PathVariable("checkListcode") int checkListCode) {

        CheckListQueryDTO checkListDTO = checkListService.findCheckListById(checkListCode);

        ResponseFindCheckListVO checkListVO = ResponseFindCheckListVO.builder()
                .checkListCode(checkListDTO.getCheckListCode())
                .checkListTitle(checkListDTO.getCheckListTitle())
                .checkListFlag(checkListDTO.isCheckListFlag())
                .createdAt(checkListDTO.getCreatedAt())
                .updatedAt(checkListDTO.getUpdatedAt())
                .shopCode(checkListDTO.getShopCode())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(checkListVO);
    }

    // 2. 특정 매장의 flag가 ture인 모든 checklist 조회
    @GetMapping("/shop/{shopcode}/checklist")
    @Operation(summary = "특정 매장의 모든 체크리스트 조회")
    public ResponseEntity<List<ResponseFindCheckListVO>> getAllCheckListByShop(
            @PathVariable("shopcode") int shopCode){

        List<CheckListQueryDTO> checkListDTOList = checkListService.findCheckListByIdByShop(shopCode);

        List<ResponseFindCheckListVO> checkListVOList = checkListDTOList
                .stream()
                .map(checkListDTO -> ResponseFindCheckListVO.builder()
                        .checkListCode(checkListDTO.getCheckListCode())
                        .checkListTitle(checkListDTO.getCheckListTitle())
                        .checkListFlag(checkListDTO.isCheckListFlag())
                        .createdAt(checkListDTO.getCreatedAt())
                        .updatedAt(checkListDTO.getUpdatedAt())
                        .shopCode(checkListDTO.getShopCode())
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(checkListVOList);
    }
}
