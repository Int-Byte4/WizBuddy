package com.intbyte.wizbuddy.user.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RequestEditUserDTO {
    private String userPassword;
    private String userPhone;
    private LocalDateTime updatedAt;
}
