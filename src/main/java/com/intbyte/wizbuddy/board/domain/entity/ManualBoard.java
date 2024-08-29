package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity(name = "manualBoard")
@Table(name = "manualBoard")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManualBoard {
    @Id
    @Column
    private int manualCode;

    @Column
    private String manualTitle;

    @Column
    private String manualContents;

    @Column
    private boolean manualFlag;

    @Column
    private String imageURL;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private int shopCode;

}
