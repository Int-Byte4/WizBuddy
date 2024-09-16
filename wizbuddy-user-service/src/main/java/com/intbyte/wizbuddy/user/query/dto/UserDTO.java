package com.intbyte.wizbuddy.user.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class UserDTO {
    private String userCode;
    private String userType;
    private String userEmail;
    private String userPassword;
}
