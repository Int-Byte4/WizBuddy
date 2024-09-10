package com.intbyte.wizbuddy.shop.command.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
