package com.intbyte.wizbuddy.checklist.controller;

import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.checklist.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.service.CheckListService;
import com.intbyte.wizbuddy.checklist.vo.request.RequestInsertCheckListVO;
import com.intbyte.wizbuddy.checklist.vo.request.RequestModifyCheckListVO;
import com.intbyte.wizbuddy.checklist.vo.response.ResponseFindCheckListVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CheckListController {

    private final CheckListService checkListService;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckListController(CheckListService checkListService, ModelMapper modelMapper) {
        this.checkListService = checkListService;
        this.modelMapper = modelMapper;
    }

    // 1. 특정 checkList 조회
    @GetMapping("/checklist/{checkListCode}")
    public ResponseEntity<ResponseFindCheckListVO> getCheckListById(
            @PathVariable("checkListCode") int checkListCode) {

        CheckListDTO checkListDTO = checkListService.findCheckListById(checkListCode);

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
    @GetMapping("/shop/{shopCode}/checklist")
    public ResponseEntity<List<ResponseFindCheckListVO>> getAllCheckListByShop(
            @PathVariable("shopCode") int shopCode){

        List<CheckListDTO> checkListDTOList = checkListService.findCheckListByIdByShop(shopCode);

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

    // 3. 특정 매장에 체크리스트 1개 등록
    @PostMapping("/shop/{shopCode}/checklist")
    public ResponseEntity<String> insertCheckList(
            @PathVariable("shopCode") int shopCode,
            @RequestBody RequestInsertCheckListVO request
    ){
        CheckListDTO checkListDTO = modelMapper.map(request, CheckListDTO.class);

        checkListService.insertCheckList(checkListDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("생성 완료");
    }

    // 4, 5. 특정 매장의 특정 체크리스트 수정, 삭제
    @PostMapping("/shop/{shopCode}/checklist/{checkListCode}")
    public ResponseEntity<String> modifyCheckList(
            @PathVariable("shopCode") int shopCode,
            @PathVariable("checkListCode") int checkListCode,
            @RequestBody RequestModifyCheckListVO request
    ){
        EditCheckListInfo info = new EditCheckListInfo(request.getCheckListTitle(), request.getCheckListFlag(), request.getUpdatedAt());

        checkListService.modifyCheckList(checkListCode, info);
        if(request.getCheckListFlag()){
            return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
        }else{
            checkListService.deleteCheckList(checkListCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제 완료");
        }
    }
}
