package com.intbyte.wizbuddy.task.domain.entity;

import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.task.domain.EditTaskInfo;
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

    @ManyToOne
    @JoinColumn(name="shop_code")
    private Shop shop;

    public void modify(@Valid EditTaskInfo editTaskInfo){
        this.taskContents = editTaskInfo.getTaskContents();
        this.taskFlag = editTaskInfo.isTaskFlag();
        this.taskFixedState = editTaskInfo.isTaskFixedState();
        this.updatedAt = LocalDateTime.now();
    }
}