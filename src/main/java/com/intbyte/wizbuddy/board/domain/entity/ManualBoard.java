package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "manualBoard")
@Table(name = "manual_board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManualBoard {
    @Id
    @Column
    private int manual_code;

    @Column
    private String manual_title;

    @Column
    private String manual_contents;

    @Column
    private boolean manual_flag;

    @Column
    private String image_url;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private int shop_code;

    @Column
    private int user_code;

}
