package com.intbyte.wizbuddy.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "employer")
@Table(name = "employer")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_code")
    private int employerCode;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "employer_email")
    private String employerEmail;

    @Column(name = "employer_password")
    private String employerPassword;

    @Column(name = "employer_phone")
    private String employerPhone;

    @Column(name = "employer_flag")
    private boolean employerFlag;

    @Column(name = "employer_black_state")
    private boolean employerBlackState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
