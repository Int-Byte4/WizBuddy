package com.intbyte.wizbuddy.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column
    private int employerCode;

    @Column
    private String employerName;

    @Column
    private String employerEmail;

    @Column
    private String employerPassword;

    @Column
    private String employerPhone;

    @Column
    private boolean employerFlag;

    @Column
    private boolean employerBlackState;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
