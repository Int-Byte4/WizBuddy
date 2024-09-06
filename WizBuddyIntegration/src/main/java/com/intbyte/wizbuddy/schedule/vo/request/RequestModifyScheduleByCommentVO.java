package com.intbyte.wizbuddy.schedule.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestModifyScheduleByCommentVO {
    private int subsCode;
    private boolean subsFlag;
    private String employeeCode;
}
