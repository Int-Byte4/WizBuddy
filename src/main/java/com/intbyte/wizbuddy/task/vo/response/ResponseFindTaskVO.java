package com.intbyte.wizbuddy.task.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseFindTaskVO {
    private int taskCode;
    private String taskContents;
    private boolean taskFlag;
    private boolean taskFixedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
