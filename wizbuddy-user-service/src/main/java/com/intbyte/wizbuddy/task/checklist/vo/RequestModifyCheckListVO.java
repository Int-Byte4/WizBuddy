package com.intbyte.wizbuddy.task.checklist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestModifyCheckListVO {
    private final String checkListTitle;
    private final Boolean checkListFlag;
    private final LocalDateTime updatedAt;
}
