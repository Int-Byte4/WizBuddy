package com.intbyte.wizbuddy.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseFindEmployerVO {
    private String employerCode;
    private String employerName;
    private String employerEmail;
    private String employerPassword;
    private String employerPhone;
    private boolean employerFlag;
    private boolean employerBlackState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
