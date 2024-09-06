package com.intbyte.wizbuddy.board.controller;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.service.SubsBoardService;
import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.vo.request.RequestDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestModifySubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subsboards")
@RequiredArgsConstructor
public class SubsBoardController {

    private final SubsBoardService subsBoardService;
    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "대타게시글 전체 조회")
    public ResponseEntity<List<ResponseFindSubsBoardVO>> getAllSubsBoards() {
        List<SubsBoardDTO> subsBoardDTOs = subsBoardService.findAllSubsBoards();

        List<ResponseFindSubsBoardVO> subsBoardVOs = subsBoardDTOs.stream()
                .map(dto -> ResponseFindSubsBoardVO.builder()
                        .subsCode(dto.getSubsCode())
                        .subsTitle(dto.getSubsTitle())
                        .subsContent(dto.getSubsContent())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .subsFlag(dto.isSubsFlag())
                        .employeeWorkingPartCode(dto.getEmployeeWorkingPartCode())
                        .shopCode(dto.getShopCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(subsBoardVOs);
    }

    @GetMapping("/{subsCode}")
    @Operation(summary = "대타게시글 1개 조회")
    public ResponseEntity<ResponseFindSubsBoardVO> getSubsBoardById(@PathVariable("subsCode") int subsCode) {
        SubsBoardDTO subsBoardDTO = subsBoardService.findSubsBoardById(subsCode);

        ResponseFindSubsBoardVO subsBoardVO = ResponseFindSubsBoardVO.builder()
                .subsCode(subsBoardDTO.getSubsCode())
                .subsTitle(subsBoardDTO.getSubsTitle())
                .subsContent(subsBoardDTO.getSubsContent())
                .createdAt(subsBoardDTO.getCreatedAt())
                .updatedAt(subsBoardDTO.getUpdatedAt())
                .subsFlag(subsBoardDTO.isSubsFlag())
                .employeeWorkingPartCode(subsBoardDTO.getEmployeeWorkingPartCode())
                .shopCode(subsBoardDTO.getShopCode())
                .build();

        return ResponseEntity.ok(subsBoardVO);
    }

    @GetMapping("/shop/{shopCode}")
    @Operation(summary = "매장별 대타게시글 전체 조회")
    public ResponseEntity<List<ResponseFindSubsBoardVO>> getSubsBoardsByShopCode(@PathVariable("shopCode") int shopCode) {
        List<SubsBoardDTO> subsBoardDTOs = subsBoardService.getSubsBoardsByShopCode(shopCode);

        List<ResponseFindSubsBoardVO> subsBoardVOs = subsBoardDTOs.stream()
                .map(dto -> ResponseFindSubsBoardVO.builder()
                        .subsCode(dto.getSubsCode())
                        .subsTitle(dto.getSubsTitle())
                        .subsContent(dto.getSubsContent())
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .subsFlag(dto.isSubsFlag())
                        .employeeWorkingPartCode(dto.getEmployeeWorkingPartCode())
                        .shopCode(dto.getShopCode())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(subsBoardVOs);
    }

    @PostMapping
    @Operation(summary = "대타게시글 등록")
    public ResponseEntity<ResponseInsertSubsBoardVO> createSubsBoard(@RequestBody RequestInsertSubsBoardVO requestInsertSubsBoardVO) {
        SubsBoardDTO subsBoardEntity = modelMapper.map(requestInsertSubsBoardVO, SubsBoardDTO.class);
        ResponseInsertSubsBoardVO responseBoard = subsBoardService.registSubsBoard(subsBoardEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }

    @PatchMapping("/update/{subsCode}")
    @Operation(summary = "대타게시글 수정")
    public ResponseEntity<ResponseModifySubsBoardVO> updateSubsBoard(
            @PathVariable("subsCode") int subsCode,
            @RequestBody RequestModifySubsBoardVO requestModify) {
        EditSubsBoardInfo editSubsBoardInfo = modelMapper.map(requestModify, EditSubsBoardInfo.class);
        ResponseModifySubsBoardVO responseBoard = subsBoardService.modifySubsBoards(subsCode, editSubsBoardInfo);
        return  ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }

    @PatchMapping("/delete/{subsCode}")
    @Operation(summary = "대타게시글 삭제")
    public ResponseEntity<ResponseDeleteSubsBoardVO> deleteSubsBoard(
            @PathVariable("subsCode") int subsCode,
            @RequestBody RequestDeleteSubsBoardVO requestDeleteSubsBoardVO) {
        SubsBoardDTO subsBoardEntity = modelMapper.map(requestDeleteSubsBoardVO, SubsBoardDTO.class);
        subsBoardEntity.setSubsCode(subsCode);
        ResponseDeleteSubsBoardVO responseBoard = subsBoardService.deleteSubsBoard(subsBoardEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoard);
    }

}
