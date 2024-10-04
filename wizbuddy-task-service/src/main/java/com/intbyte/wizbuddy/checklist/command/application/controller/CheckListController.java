package com.intbyte.wizbuddy.checklist.command.application.controller;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.command.application.service.AppCheckListService;
import com.intbyte.wizbuddy.checklist.command.domain.aggregate.vo.RequestInsertCheckListVO;
import com.intbyte.wizbuddy.checklist.command.domain.aggregate.vo.RequestModifyCheckListVO;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("checkListCommandController")
@RequestMapping("/checklist")
public class CheckListController {

    private final AppCheckListService appCheckListService;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckListController(AppCheckListService appCheckListService, ModelMapper modelMapper) {
        this.appCheckListService = appCheckListService;
        this.modelMapper = modelMapper;
    }

    // command 1. 특정 체크리스트 등록
    @PostMapping
//    @PostMapping("/shop/{shopCode}/checklist")
    @Operation(summary = "특정 매장에 체크리스트 등록")
    public ResponseEntity<String> insertCheckList(
//            @PathVariable("shopCode") int shopCode,
            @RequestBody RequestInsertCheckListVO request
    ){
        CheckListDTO checkListDTO = modelMapper.map(request, CheckListDTO.class);

        appCheckListService.insertCheckList(checkListDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("체크리스트 생성 완료");
    }

    // command 2. 특정 체크리스트에 특정 업무 추가
    @PostMapping("/{checkListcode}/{taskcode}")
    @Operation(summary = "특정 체크리스트에 업무 추가")
    public ResponseEntity<String> insertTaskToCheckList(
            @PathVariable("checkListcode") int checkListCode,
            @PathVariable("taskcode") int taskCode
    ){

        appCheckListService.insertTaskToCheckList(checkListCode, taskCode);

        return ResponseEntity.status(HttpStatus.CREATED).body(checkListCode + "번 체크리스트에" + taskCode + "번 업무 추가 완료");
    }

    // command 3. 특정 체크리스트에 특정 업무 삭제 // 신규
    @DeleteMapping("/{checkListcode}/{taskcode}")
    @Operation(summary = "특정 체크리스트의 업무 삭제")
    public ResponseEntity<String> deleteTaskFromCheckList(
            @PathVariable("checkListcode") int checkListCode,
            @PathVariable("taskcode") int taskCode
    ){

        appCheckListService.deleteTaskFromCheckList(checkListCode, taskCode);

        return ResponseEntity.status(HttpStatus.OK).body(checkListCode + "번 체크리스트의" + taskCode + "번 업무 삭제 완료");
    }


    // command 4, 5. 특정 체크리스트 자체 수정, 완전 삭제
    @PutMapping("/{checkListcode}")
//    @PostMapping("/shop/{shopCode}/checklist/{checkListCode}")
    @Operation(summary = "특정 체크리스트 수정, 삭제")
    public ResponseEntity<String> modifyCheckList(
//            @PathVariable("shopCode") int shopCode,
            @PathVariable("checkListcode") int checkListCode,
            @RequestBody RequestModifyCheckListVO request
    ){
        CheckListDTO checkListDTO = modelMapper.map(request, CheckListDTO.class);

        if(request.getCheckListFlag()){

            appCheckListService.modifyCheckList(checkListCode, checkListDTO);
            return ResponseEntity.status(HttpStatus.OK).body(checkListCode + "번 체크리스트 수정 완료");
        }else{

            appCheckListService.deleteCheckList(checkListCode);
            return ResponseEntity.status(HttpStatus.OK).body(checkListCode + "번 체크리스트 삭제 완료");
        }
    }
}
