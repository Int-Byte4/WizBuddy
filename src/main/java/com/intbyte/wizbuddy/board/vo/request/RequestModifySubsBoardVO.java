package com.intbyte.wizbuddy.board.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class RequestModifySubsBoardVO {
    private int subsCode;
    private String subsTitle;
    private String subsContent;
    private LocalDateTime updatedAt;
    private boolean subsFlag;
    private int employeeWorkingPartCode;
}
