package com.intbyte.wizbuddy.taskperchecklist.domain.entity;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.taskperchecklist.domain.EditTaskPerCheckListInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
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
public class TaskPerCheckList {

    @EmbeddedId
    private TaskPerCheckListId taskPerCheckListId;

    @ManyToOne
    @MapsId("checkListCode")
    @JoinColumn(name = "checklist_code", insertable = false, updatable = false)
    private CheckList checkList;

    @ManyToOne
    @MapsId("taskCode")
    @JoinColumn(name = "task_code", insertable = false, updatable = false)
    private Task task;

    @Column
    private Boolean taskFinishedState;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "employee_code")
    private Employee employee;

    public void modify(EditTaskPerCheckListInfo info, Employee employee) {

        this.taskFinishedState = info.getTaskFinishedState();
        this.updatedAt = info.getUpdatedAt();
        this.employee = employee;
    }
}
