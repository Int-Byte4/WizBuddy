package com.intbyte.wizbuddy.user.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class RequestEditEmployerDTO {
    private String employerPassword;
    private String employerPhone;
    private LocalDateTime updatedAt;
}
