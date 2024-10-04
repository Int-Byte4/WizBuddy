package com.intbyte.wizbuddy.checklist.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Builder
public class ResponseFindCheckListVO {

    private final int checkListCode;
    private final String checkListTitle;
    private final Boolean checkListFlag;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
}
