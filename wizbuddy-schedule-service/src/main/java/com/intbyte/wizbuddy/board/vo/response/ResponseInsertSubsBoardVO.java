package com.intbyte.wizbuddy.board.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertSubsBoardVO {
    private String subsTitle;
    private String subsContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean subsFlag;
    private int employeeWorkingPartCode;
    private int shopCode;
}
