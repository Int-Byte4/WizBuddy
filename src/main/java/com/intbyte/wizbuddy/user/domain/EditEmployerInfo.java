package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployerInfo {
    private int employerCode;
    private String employerName;
    private String employerPassword;
    private String employerPhone;
    private LocalDateTime updatedAt;
}
