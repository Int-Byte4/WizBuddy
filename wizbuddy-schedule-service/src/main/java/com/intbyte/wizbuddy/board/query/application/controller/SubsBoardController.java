package com.intbyte.wizbuddy.board.query.application.controller;
import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.query.application.service.SubsBoardService;
import com.intbyte.wizbuddy.board.query.domain.aggregate.vo.response.ResponseFindSubsBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("querySubsBoardController")
@RequestMapping("/subsboards")
@RequiredArgsConstructor
public class SubsBoardController {

    private final SubsBoardService subsBoardService;

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


}
