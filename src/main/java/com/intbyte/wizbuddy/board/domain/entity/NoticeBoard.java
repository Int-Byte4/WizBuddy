package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity(name = "noticeBoard")
@Table(name = "notice_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoticeBoard {
    @Id
    @Column
    private int notice_code;

    @Column
    private String notice_title;

    @Column
    private String notice_content;

    @Column
//    @ColumnDefault("true")
    private boolean notice_flag;

    @Column
    private String image_url;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private int shop_code;
}
