package com.intbyte.wizbuddy.user.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class UserDTO {
    @JsonProperty("user_code")
    private String userCode;

    @JsonProperty("user_type")
    private UserTypeEnum userType;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("user_password")
    private String userPassword;

    @JsonProperty("user_phone")
    private String userPhone;

    @JsonProperty("user_flag")
    private boolean userFlag;

    @JsonProperty("user_black_state")
    private boolean userBlackState;

    @JsonProperty("user_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("user_updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("user_kakao_id")
    private String kakaoId;

    @JsonProperty("user_kakao_access_token")
    private String kakaoAccessToken;

    @JsonProperty("user_jwt_token")
    private String jwtToken;
}
