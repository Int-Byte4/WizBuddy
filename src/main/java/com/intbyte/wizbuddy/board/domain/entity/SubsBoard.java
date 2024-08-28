package com.intbyte.wizbuddy.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "subsBoard")
@Table(name = "substitutionBoard")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubsBoard {
    @Id
    @Column
    private int subsCode;

    @Column
    private String subsTitle;

    @Column
    private String subsContents;

    @Column
    private boolean subsFlag;

    @Column
    private String imageURL;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private int employeeWorkingPartCode;

    @Column
    private int shopCode;
}
