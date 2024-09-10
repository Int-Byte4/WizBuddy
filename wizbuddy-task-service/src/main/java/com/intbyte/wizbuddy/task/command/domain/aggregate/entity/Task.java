package com.intbyte.wizbuddy.task.command.domain.aggregate.entity;

import com.intbyte.wizbuddy.task.command.application.dto.TaskDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name="shop_code")
    private Integer shopCode;

    public void modify(@Valid TaskDTO taskDTO){
        this.taskContents = taskDTO.getTaskContents();
        this.taskFlag = taskDTO.isTaskFlag();
        this.taskFixedState = taskDTO.isTaskFixedState();
        this.updatedAt = LocalDateTime.now();
    }
}
