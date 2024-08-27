package com.intbyte.wizbuddy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubsBoardDTO {
    private int subsCode;
    private String subsTitle;
    private String subsContents;
    private boolean subsFlag;
    private String imageURL;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}
