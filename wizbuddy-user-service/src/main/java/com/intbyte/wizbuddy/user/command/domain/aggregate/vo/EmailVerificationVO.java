package com.intbyte.wizbuddy.user.command.domain.aggregate.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailVerificationVO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String code;
}