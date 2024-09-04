package com.intbyte.wizbuddy.task.checklist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestInsertCheckListVO {

    private final String checkListTitle;
    private final Boolean checkListFlag;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Integer shopCode;
}
