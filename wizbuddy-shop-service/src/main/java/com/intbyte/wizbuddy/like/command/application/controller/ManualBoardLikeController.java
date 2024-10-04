package com.intbyte.wizbuddy.like.command.application.controller;

import com.intbyte.wizbuddy.like.command.application.service.ManualBoardLikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("manualBoardLikedCommandController")
@RequestMapping("/manualboardlike")
@RequiredArgsConstructor
public class ManualBoardLikeController {

    private final ManualBoardLikedService manualBoardLikedService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerManualBoardLike(@RequestParam int manualCode, @RequestParam String employeeCode) {
        manualBoardLikedService.registerManualBoardLike(manualCode, employeeCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
