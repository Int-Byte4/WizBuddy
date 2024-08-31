package com.intbyte.wizbuddy.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code", nullable = false, unique = true)
    private String userCode;

    @Column(nullable = false, name = "user_type")
    private String userType;
}
