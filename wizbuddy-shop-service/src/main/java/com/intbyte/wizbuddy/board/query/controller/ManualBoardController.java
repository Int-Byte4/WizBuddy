package com.intbyte.wizbuddy.board.query.controller;

import com.intbyte.wizbuddy.board.query.dto.ManualBoardDTO;
import com.intbyte.wizbuddy.board.query.service.ManualBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("manualBoardQueryController")
@RequestMapping("/manualboard")
public class ManualBoardController {

    private final ManualBoardService manualBoardService;

    @Autowired
    public ManualBoardController(ManualBoardService manualBoardService) {
        this.manualBoardService = manualBoardService;
    }

    @Operation(summary = "매뉴얼 게시판 전체 조회")
    @GetMapping
    public ResponseEntity<List<ManualBoardDTO>> getAllManualBoards() {

        List<ManualBoardDTO> response = manualBoardService.findAllManualBoards();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "매장별 매뉴얼 게시판 조회")
    @GetMapping("/shop/{shopCode}")
    public ResponseEntity<List<ManualBoardDTO>> getAllManualBoardsByShop(@PathVariable("shopCode") int shopCode) {

        List<ManualBoardDTO> response = manualBoardService.findManualBoardByShopCode(shopCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "매뉴얼 게시글 단 건 조회")
    @GetMapping("/{manualCode}")
    public ResponseEntity<ManualBoardDTO> findManualBoardByManualCode(@PathVariable("manualCode") int manualCode) {
        ManualBoardDTO manualBoardDTO = manualBoardService.findManualBoardByManualCode(manualCode);

        return ResponseEntity.status(HttpStatus.OK).body(manualBoardDTO);
    }
}
