package com.intbyte.wizbuddy.user.dto;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String usedCode;
    private String userType;
    private String userEmail;
    private String userPassword;
}
