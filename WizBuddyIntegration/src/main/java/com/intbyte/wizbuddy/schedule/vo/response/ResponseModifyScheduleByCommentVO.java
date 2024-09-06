package com.intbyte.wizbuddy.schedule.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseModifyScheduleByCommentVO {
    private int subsCode;
    private boolean subsFlag;
    private String employeeCode;
}
