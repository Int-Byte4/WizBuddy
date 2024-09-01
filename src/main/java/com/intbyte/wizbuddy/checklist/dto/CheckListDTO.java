package com.intbyte.wizbuddy.checklist.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CheckListDTO {
    private int checkListCode;

    private String checkListTitle;

    private Boolean checkListFlag;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int shopCode;
}
