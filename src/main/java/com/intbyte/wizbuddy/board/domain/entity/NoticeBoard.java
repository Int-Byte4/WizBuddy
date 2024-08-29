package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "noticeBoard")
@Table(name = "noticeBoard")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeBoard {
    @Id
    @Column
    private int noticeCode;

    @Column
    private String noticeTitle;

    @Column
    private String noticeContents;

    @Column
    private boolean noticeFlag;

    @Column
    private String imageURL;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private int shopCode;
}
