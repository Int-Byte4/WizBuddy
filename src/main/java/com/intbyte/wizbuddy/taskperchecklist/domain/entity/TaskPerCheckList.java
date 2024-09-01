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
    private TaskPerChecklistId taskPerChecklistId;

    @ManyToOne
    @MapsId("checkListCode")
    @JoinColumn(name = "checklist_code", insertable = false, updatable = false)
    // @MapsId: 이 어노테이션은 엔티티의 복합 키(@EmbeddedId)에 매핑된 외래 키 필드를 나타냅니다.
    // @MapsId("checklistCode")는 TaskPerChecklistId의 checklistCode 필드와 매핑됩니다.
    // insertable = false, updatable = false 속성은 해당 필드가 외래 키로서 복합 키에 의해 관리됨을 나타냅니다.
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
    @JoinColumn(name = "employee_code", nullable = false)
    private Employee employee;  // 얘는 복합키는 아닌 그냥 외래키

    public void modify(EditTaskPerCheckListInfo info, Employee employee) {

        this.taskFinishedState = info.getTaskFinishedState();
        this.updatedAt = info.getUpdatedAt();
        this.employee = employee;
    }
}
