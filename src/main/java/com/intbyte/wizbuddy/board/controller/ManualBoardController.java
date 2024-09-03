package com.intbyte.wizbuddy.board.controller;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import com.intbyte.wizbuddy.board.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.service.ManualBoardService;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestUpdateManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseFindManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateManualBoardVO;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // 2. 매장별 매뉴얼 게시글 조회
    @GetMapping("/shop/{shopcode}")
    public ResponseEntity<List<ResponseFindManualBoardVO>> getManualBoardByShopCode(@PathVariable("shopcode") int shopCode) {
        List<ManualBoardDTO> manualBoardDTOs = manualBoardService.findManualBoardByShopCode(shopCode);

        List<ResponseFindManualBoardVO> manualBoardVOs = manualBoardDTOs.stream()
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

    // 3. 매뉴얼 게시글 단 건 조회
    @GetMapping("/{manualcode}")
    public ResponseEntity<ResponseFindManualBoardVO> findManualBoardByManualCode(@PathVariable("manualcode") int manualCode) {
        ManualBoardDTO manualBoardDTO = manualBoardService.findManualBoardByManualCode(manualCode);

        ResponseFindManualBoardVO manualBoardVO =  ResponseFindManualBoardVO.builder()
                .manualCode(manualBoardDTO.getManualCode())
                .manualTitle(manualBoardDTO.getManualTitle())
                .manualContents(manualBoardDTO.getManualContents())
                .manualFlag(manualBoardDTO.isManualFlag())
                .imageUrl(manualBoardDTO.getImageUrl())
                .createdAt(manualBoardDTO.getCreatedAt())
                .updatedAt(manualBoardDTO.getUpdatedAt())
                .shopCode(manualBoardDTO.getShopCode())
                .userCode(manualBoardDTO.getUserCode())
                .build();

        return ResponseEntity.ok(manualBoardVO);
    }

    // 4. 새로운 매뉴얼 게시글 등록
    @PostMapping
    public ResponseEntity<ResponseInsertManualBoardVO> insertManualBoard(
            @RequestBody RequestInsertManualBoardVO requestInsertManualBoardVO) {

        // 서비스 호출하여 게시글 등록
        ResponseInsertManualBoardVO response = manualBoardService.registerManualBoard(requestInsertManualBoardVO);

        // 성공 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 5. 매뉴얼 게시글 수정, 삭제
    @PatchMapping("/update/{manualcode}")
    public ResponseEntity<ResponseUpdateManualBoardVO> updateManualBoard(
            @PathVariable("manualcode") int manualCode,
            @RequestBody RequestUpdateManualBoardVO requestUpdateManualBoardVO) {

        if(requestUpdateManualBoardVO.isManualFlag()){

            EditManualBoardInfo editManualBoardInfo = new EditManualBoardInfo(
                    manualCode,
                    requestUpdateManualBoardVO.getManualTitle(),
                    requestUpdateManualBoardVO.getManualContents(),
                    requestUpdateManualBoardVO.getImageUrl(),
                    requestUpdateManualBoardVO.isManualFlag(),
                    LocalDateTime.now(),
                    requestUpdateManualBoardVO.getUserCode()
            );

            // 수정 메서드
            ResponseUpdateManualBoardVO response = manualBoardService.modifyManualBoard(manualCode, editManualBoardInfo);

            return ResponseEntity.ok(response);

        } else {
            DeleteManualBoardInfo deleteManualBoardInfo = new DeleteManualBoardInfo(
                    requestUpdateManualBoardVO.isManualFlag(),
                    LocalDateTime.now(),
                    requestUpdateManualBoardVO.getUserCode()
            );

            //삭제 메서드
            manualBoardService.deleteManualBoard(manualCode, deleteManualBoardInfo);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
