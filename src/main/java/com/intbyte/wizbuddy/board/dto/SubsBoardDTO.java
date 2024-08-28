package com.intbyte.wizbuddy.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubsBoardDTO {
    private int subsCode;
    private String subsTitle;
    private String subsContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean subsFlag;
    private int employeeWorkingPartCode;
    private int shopCode;
}
