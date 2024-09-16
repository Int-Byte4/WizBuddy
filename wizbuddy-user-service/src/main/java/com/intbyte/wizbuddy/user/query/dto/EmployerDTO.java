package com.intbyte.wizbuddy.user.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class EmployerDTO {
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
