package com.intbyte.wizbuddy.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployerInfo {
    private String employerCode;
    private String employerName;
    private String employerPassword;
    private String employerPhone;
    private LocalDateTime updatedAt;
}
