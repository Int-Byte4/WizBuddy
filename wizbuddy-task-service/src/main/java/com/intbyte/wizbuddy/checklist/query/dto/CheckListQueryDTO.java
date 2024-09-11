package com.intbyte.wizbuddy.checklist.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CheckListQueryDTO {

    private int checkListCode;
    private String checkListTitle;
    private boolean checkListFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}

