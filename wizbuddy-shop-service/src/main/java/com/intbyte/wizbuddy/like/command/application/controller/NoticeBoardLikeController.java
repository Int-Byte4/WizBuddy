package com.intbyte.wizbuddy.like.command.application.controller;

import com.intbyte.wizbuddy.like.command.application.service.NoticeBoardLikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("noticeBoardLikedCommandController")
@RequestMapping("/noticeboardlike")
@RequiredArgsConstructor
public class NoticeBoardLikeController {

    private final NoticeBoardLikedService noticeBoardLikedService;


    @PostMapping("/register")
    public ResponseEntity<Void> registerNoticeboardLike(@RequestParam int noticeCode, @RequestParam String employeeCode) {
        noticeBoardLikedService.registerNoticeBoardLike(noticeCode, employeeCode);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
