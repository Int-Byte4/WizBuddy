package com.intbyte.wizbuddy.board.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseDeleteSubsBoardVO {
    private int subsCode;
    private LocalDateTime updatedAt;
    private boolean subsFlag;

}
