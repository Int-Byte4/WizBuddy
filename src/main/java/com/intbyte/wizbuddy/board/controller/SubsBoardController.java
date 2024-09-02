package com.intbyte.wizbuddy.board.controller;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.service.SubsBoardService;
import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.vo.request.RequestDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestModifySubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindSubsBoardVO;
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

    // 모든 게시판 가져오기
    @GetMapping
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

    // 특정 상점의 게시판 가져오기
    @GetMapping("/shop/{shopCode}")
    public ResponseEntity<List<ResponseFindSubsBoardVO>> getSubsBoardsByShopCode(@PathVariable("shopCode") int shopCode) {
        List<SubsBoardDTO> subsBoardDTOs = subsBoardService.getSubsBoardsByShopCode(shopCode);

        // DTO 리스트를 VO 리스트로 변환
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
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> createSubsBoard(@RequestBody RequestInsertSubsBoardVO requestInsertSubsBoardVO) {
        SubsBoardDTO subsBoardEntity = modelMapper.map(requestInsertSubsBoardVO, SubsBoardDTO.class);
        subsBoardService.registSubsBoard(subsBoardEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // 게시판 수정하기 (사장만 접근 가능)
    @PutMapping("/update/{subsCode}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> updateSubsBoard(
            @PathVariable("subsCode") int subsCode,
            @RequestBody RequestModifySubsBoardVO requestModify) {
        EditSubsBoardInfo editSubsBoardInfo = new EditSubsBoardInfo(requestModify.getSubsTitle(),requestModify.getSubsContent(),requestModify.getUpdatedAt(),requestModify.getEmployeeWorkingPartCode());
        subsBoardService.modifySubsBoards(subsCode,editSubsBoardInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 게시판 삭제하기 (사장만 접근 가능)
    @PutMapping("/delete/{subsCode}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Void> deleteSubsBoard(@PathVariable("subsCode") int subsCode, @RequestBody RequestDeleteSubsBoardVO requestDeleteSubsBoardVO) {
        SubsBoardDTO subsBoardEntity = modelMapper.map(requestDeleteSubsBoardVO, SubsBoardDTO.class);
        subsBoardEntity.setSubsCode(subsCode);
        subsBoardService.deleteSubsBoard(subsBoardEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
