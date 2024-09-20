package com.intbyte.wizbuddy.user.query.dto;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class UserDTO {
    private String userCode;
    private UserTypeEnum userType;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPhone;
    private boolean userFlag;
    private boolean userBlackState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
