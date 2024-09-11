package com.intbyte.wizbuddy.taskperchecklist.command.domain.aggregate.entity;

import com.intbyte.wizbuddy.taskperchecklist.command.application.dto.TaskPerCheckListDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "taskperchecklist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
@IdClass(TaskPerCheckListId.class)
public class TaskPerCheckList {

    @Id
    @Column(name="checklist_code")
    private Integer checkListCode;

    @Id
    @Column(name = "task_code")
    private Integer taskCode;

    @Column(name = "task_finished_state")
    private Boolean taskFinishedState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "employee_code")
    private String employeeCode;

    public void modify(TaskPerCheckListDTO taskPerCheckListDTO, String EmployeeCode) {

        this.taskFinishedState = taskPerCheckListDTO.getTaskFinishedState();
        this.updatedAt = LocalDateTime.now();
        this.employeeCode = EmployeeCode;
    }
}