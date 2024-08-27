package com.intbyte.wizbuddy.task.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Task {

    @Id
    @Column
    private int TaskCode;

    @Column
    private String TaskContents;

    @Column
    private boolean TaskFlag;

    @Column
    private boolean TaskFixedState;

    @Column
    private LocalDateTime CreatedAt;

    @Column
    private LocalDateTime UpdatedAt;
}
