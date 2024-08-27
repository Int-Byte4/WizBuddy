package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity(name = "manualboard")
@Table(name = "manualboard")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManualBoard {
    @Id
    @Column
    private int ManualCode;

    @Column
    private String ManualTitle;

    @Column
    private String ManualContents;

    @Column
    private boolean ManualFlag;

    @Column
    private String ImageURL;

    @Column
    private LocalDateTime CreatedAt;

    @Column
    private LocalDateTime UpdatedAt;

    // 외래키 어케하죠?
    @Column
    private int ShopCode;

}
