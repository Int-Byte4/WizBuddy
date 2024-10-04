package com.intbyte.wizbuddy.board.query.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "querySubsBoard")
@Table(name = "substitution_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class SubsBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_code")
    private int subsCode;

    @Column(name = "subs_title")
    private String subsTitle;

    @Column(name = "subs_content")
    private String subsContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "subs_flag" )
    private boolean subsFlag;

    @Column(name = "working_part_code")
    private int employeeWorkingPartCode;

    @Column(name = "shop_code")
    private int shopCode;

}
