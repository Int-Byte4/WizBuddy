package com.intbyte.wizbuddy.user.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RequestEditEmployerDTO {
    private String employerPassword;
    private String employerPhone;
    private LocalDateTime updatedAt;
}
