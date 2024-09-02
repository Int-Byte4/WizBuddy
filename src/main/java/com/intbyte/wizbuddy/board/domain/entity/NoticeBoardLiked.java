package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "noticeBoardLiked")
@Table(name = "noticeboardliked")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoticeBoardLiked {

    @Id
    @Column
    private int noticeLikedCode;

    @Column
    private LocalDateTime createdAt;

    @Column
    private int shopCode;

    @Column
    private int noticeCode;

    @Column
    private String employeeCode;
}
