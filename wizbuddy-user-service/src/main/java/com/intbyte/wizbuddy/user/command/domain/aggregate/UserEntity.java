package com.intbyte.wizbuddy.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, name = "user_type")
    private String userType;

    @Column(nullable = false, name = "user_email")
    private String userEmail;

    @Column(nullable = false, name = "user_password")
    private String userPassword;
}
