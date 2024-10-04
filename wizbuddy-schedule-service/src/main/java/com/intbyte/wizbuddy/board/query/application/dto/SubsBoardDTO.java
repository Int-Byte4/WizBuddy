package com.intbyte.wizbuddy.board.query.application.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubsBoardDTO {
    private int subsCode;
    private String subsTitle;
    private String subsContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean subsFlag;
    private int employeeWorkingPartCode;
    private int shopCode;
}
