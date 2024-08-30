package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEmployerInfo {
    private int employerCode;
    private boolean employerFlag;
    private LocalDateTime updatedAt;
}
