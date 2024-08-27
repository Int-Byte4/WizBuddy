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

@Entity(name = "task")
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Task {

    @Id
    @Column
    private int taskCode;

    @Column
    private String taskContents;

    @Column
    private boolean taskFlag;

    @Column
    private boolean taskFixedState;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
