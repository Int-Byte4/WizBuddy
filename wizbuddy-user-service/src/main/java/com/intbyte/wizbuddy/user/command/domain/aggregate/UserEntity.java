package com.intbyte.wizbuddy.user.command.domain.aggregate;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "users")
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserEntity {

    @Id
    @Column(name = "user_code", nullable = false, unique = true)
    private String userCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserTypeEnum userType;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_flag")
    private boolean userFlag;

    @Column(name = "user_black_state")
    private boolean userBlackState;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String kakaoId;

    public void modify(RequestEditUserDTO userDTO) {
        this.userPassword = userDTO.getUserPassword();
        this.userPhone = userDTO.getUserPhone();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRequest(UserEntity user) {
        this.userCode = user.getUserCode();
        this.userFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
