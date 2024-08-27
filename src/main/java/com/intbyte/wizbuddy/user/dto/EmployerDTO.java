package com.intbyte.wizbuddy.user.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployerDTO {

    private int employerCode;

    private String employerName;

    private String employerEmail;

    private String employerPassword;

    private String employerPhone;

    private boolean employerFlag;

    private boolean employerBlackState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
