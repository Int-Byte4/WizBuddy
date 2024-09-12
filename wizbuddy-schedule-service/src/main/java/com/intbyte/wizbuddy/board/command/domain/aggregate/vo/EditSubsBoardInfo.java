package com.intbyte.wizbuddy.board.command.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditSubsBoardInfo {
    private String subsTitle;
    private String subsContent;
    private LocalDateTime updatedAt;
    private int employeeWorkingPartCode;
}
