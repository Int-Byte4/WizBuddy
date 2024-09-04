package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name="manual_liked_code")
    private int manualLikedCode;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="manual_code")
    private int manualCode;

    @Column(name="shop_code")
    private int shopCode;

    @Column(name="employee_code")
    private String employeeCode;
}
