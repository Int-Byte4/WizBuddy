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
    @Column(name="notice_liked_code")
    private int noticeLikedCode;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="employee_code")
    private int shopCode;

    @Column(name="shop_code")
    private int noticeCode;

    @Column(name="notice_code")
    private String employeeCode;
}
