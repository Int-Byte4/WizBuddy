package com.intbyte.wizbuddy.board.command.application.controller;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.application.service.SubsBoardService;
import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.vo.request.RequestDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.request.RequestModifySubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("commandSubsBoardController")
@RequestMapping("/subsboards")
@RequiredArgsConstructor
public class SubsBoardController {

    private final SubsBoardService subsBoardService;
    private final ModelMapper modelMapper;

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
