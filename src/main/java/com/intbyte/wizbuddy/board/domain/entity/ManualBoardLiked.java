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
    @Column
    private int manualLikedCode;

    @Column
    private LocalDateTime createdAt;

    @Column
    private int manualCode;

    @Column
    private int shopCode;

    @Column
    private String employeeCode;
}
