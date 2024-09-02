package com.intbyte.wizbuddy.user.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseEditEmployerVO {
    private String employerCode;
    private String employerName;
    private String employerPassword;
    private String employerPhone;
    private LocalDateTime updatedAt;
}
