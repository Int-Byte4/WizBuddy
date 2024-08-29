package com.intbyte.wizbuddy.task.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Task {

    @Id
    @Column(name = "task_code")
    private int taskCode;

    @Column(name = "task_contents")
    private String taskContents;

    @Column(name = "task_flag")
    private boolean taskFlag;

    @Column(name = "task_fixed_state")
    private boolean taskFixedState;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}