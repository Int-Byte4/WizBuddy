package com.intbyte.wizbuddy.board.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class RequestDeleteSubsBoardVO {
    private int subsCode;
    private LocalDateTime updatedAt;
    private boolean subsFlag;

}
