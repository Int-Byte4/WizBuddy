package com.intbyte.wizbuddy.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CheckListDTO {
    private int checkListCode;

    private String checkListTitle;

    private Boolean checkListFlag;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
