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
    @Column(name = "user_type", nullable = false)
    private UserTypeEnum userType;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(nullable = false, name = "user_phone")
    private String userPhone;

    @Column(nullable = false, name = "user_flag")
    private boolean userFlag;

    @Column(nullable = false, name = "user_black_state")
    private boolean userBlackState;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

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
