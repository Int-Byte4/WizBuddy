package com.intbyte.wizbuddy.user.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDeleteEmployerVO {
    private String employerCode;
    private boolean employerFlag;
    private LocalDateTime updatedAt;
}
