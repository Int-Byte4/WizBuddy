package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEmployeeInfo {
    private int employeeCode;
    private boolean employeeFlag;
    private LocalDateTime updatedAt;
}
