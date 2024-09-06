package com.intbyte.wizbuddy.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListMybatis {

    private Integer checkListCode;
    private String checkListTitle;
    private Boolean checkListFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer shopCode;
}
