package com.intbyte.wizbuddy.board.command.application.controller;

import com.intbyte.wizbuddy.board.command.application.dto.RequestEditManualBoardDTO;
import com.intbyte.wizbuddy.board.command.application.service.ManualBoardService;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertManualBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseUpdateManualBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("manualBoardCommandController")
@RequestMapping("/manualboard")
public class  ManualBoardController {

    private final ManualBoardService manualBoardService;

    public ManualBoardController(ManualBoardService manualBoardService) {
        this.manualBoardService = manualBoardService;
    }

    @PostMapping("/register")
    @Operation(summary = "매뉴얼 게시글 등록")
    public ResponseEntity<ResponseInsertManualBoardVO> insertManualBoard(@RequestBody RequestInsertManualBoardVO requestInsertManualBoardVO) {
        ResponseInsertManualBoardVO response = manualBoardService.registerManualBoard(requestInsertManualBoardVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/update/{manualCode}")
    @Operation(summary = "매뉴얼 게시글 수정")
    public ResponseEntity<ResponseUpdateManualBoardVO> updateManualBoard(@PathVariable("manualCode") int manualCode, @RequestBody RequestEditManualBoardDTO requestEditManualBoardDTO) {
        ResponseUpdateManualBoardVO response = manualBoardService.modifyManualBoard(manualCode, requestEditManualBoardDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/delete/{manualCode}")
    @Operation(summary = "매뉴얼 게시글 삭제")
    public ResponseEntity<ResponseUpdateManualBoardVO> deleteManualBoard(@PathVariable("manualCode") int manualCode, @RequestParam String userCode) {
        manualBoardService.deleteManualBoard(manualCode, userCode);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}