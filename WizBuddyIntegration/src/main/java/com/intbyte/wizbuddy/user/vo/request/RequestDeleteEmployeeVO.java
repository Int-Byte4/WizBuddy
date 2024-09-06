package com.intbyte.wizbuddy.user.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteEmployeeVO {
    private String employeeCode;
    private boolean employeeFlag;
    private LocalDateTime updatedAt;
}