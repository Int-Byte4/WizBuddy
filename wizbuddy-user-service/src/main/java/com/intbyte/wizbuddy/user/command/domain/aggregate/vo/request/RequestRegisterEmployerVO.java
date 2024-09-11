package com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterEmployerVO {
    private String employerName;
    private String employerEmail;
    private String employerPassword;
    private String employerPhone;
    private boolean employerFlag;
    private boolean employerBlackState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
