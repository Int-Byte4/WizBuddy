package com.intbyte.wizbuddy.like.domain.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_liked_code")
    private int noticeLikedCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "shop_code")
    private int shopCode;

    @Column(name = "notice_code")
    private int noticeCode;

    @Column(name = "employee_code")
    private String employeeCode;
}
