package com.intbyte.wizbuddy.like.command.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "manualBoardLiked")
@Table(name = "manualboardliked")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManualBoardLiked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_liked_code")
    private int manualLikedCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "manual_code")
    private int manualCode;

    @Column(name = "shop_code")
    private int shopCode;

    @Column(name = "employee_code")
    private String employeeCode;
}
