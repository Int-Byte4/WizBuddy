package com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestRegisterKakaoUserVO {
//    private UserTypeEnum userType;
    private String userName;
    private String userEmail;
    private String userPassword;
//    private String userPhone;
    private boolean userFlag;
    private boolean userBlackState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String kakaoId;
    private String kakaoAccessToken;
    private String jwtToken;
}
